/*
 * Copyright © 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.secrets.api.core;

import com.google.common.base.Splitter;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A URL-like representation of a secret location
 *
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public record SecretURL(String provider, String path, String key, Multimap<String, String> query, boolean isURI) {
    public static final char URL_SEPARATOR = '/';
    public static final String URI_KEY_SEPARATOR = ":";
    private static final Splitter urlPathSplitter = Splitter.on(URL_SEPARATOR);
    private static final Splitter queryParamSplitter = Splitter.on('&');
    private static final Splitter queryParamKeyValueSplitter = Splitter.on('=');
    private static final Splitter keyMapParamValueSplitter = Splitter.on(URI_KEY_SEPARATOR.charAt(0));
    public static final String SCHEME = "secret://";

    public static SecretURL from(String url) {
        return from(url, false);
    }

    /**
     * Parse the string into a {@link SecretURL}
     * <p>
     * the format is : <code>secret://&lt;provider&gt;/&lt;path or name&gt;[:&lt;key&gt;][?option=value1&option=value2]</code>
     * </p>
     * <li><code>secret://</code> is mandatory is includesScheme is true</li>
     * <li>provider is mandatory and should match a secret provider id</li>
     * <li>"path or name" is mandatory, a free string that can contain forward slashes ('/').
     * If an empty string or spaces are found between two forward slashes (eg. <code>//</code> or <code>/ /</code>) parsing will fail.</li>
     * <li>key is optional and cannot replace "name or path"</li>
     * <li>query string is optional and is simply split into key/value pairs.
     * Pair are always list as can be specified more than once. If no value is parsed, then <code>true</code> is set</li>
     *
     * @param url the string to parse
     * @param isURI to indicate if it is a URI (does not start with 'secret://')
     * @return SecretURL object
     * @throws IllegalArgumentException when failing to parse
     */
    public static SecretURL from(String url, boolean isURI) {
        url = Objects.requireNonNull(url).trim();
        if (!isURI && !url.startsWith(SCHEME)) {
            throwFormatError(url);
        }
        String schemeLess = isURI ? url.substring(1) : url.substring(SCHEME.length());
        int firstSlash = schemeLess.indexOf('/');
        if (firstSlash < 0 || firstSlash == schemeLess.length() - 1) {
            throwFormatError(url);
        }

        String provider = schemeLess.substring(0, firstSlash).trim();
        int questionMarkPos = schemeLess.indexOf('?');
        if (questionMarkPos == firstSlash + 1) {
            throwFormatError(url);
        }

        String path;
        final String key;
        final Multimap<String, String> query;

        if (questionMarkPos > 0) {
            path = schemeLess.substring(provider.length() + 1, questionMarkPos).trim();
            query = parseQuery(schemeLess.substring(questionMarkPos + 1));
        } else {
            path = schemeLess.substring(provider.length() + 1).trim();
            query = MultimapBuilder.hashKeys().arrayListValues().build();
        }

        int columnIndex = path.lastIndexOf(':');
        if (columnIndex > path.lastIndexOf(URL_SEPARATOR)) {
            key = path.substring(columnIndex + 1);
            path = path.substring(0, columnIndex);
        } else {
            key = null;
        }

        // remove trailing slashes
        while (!path.isEmpty() && path.charAt(path.length() - 1) == URL_SEPARATOR) {
            path = path.substring(0, path.length() - 1);
        }

        if (path.isBlank()) {
            throwFormatError(url);
        }

        if (urlPathSplitter.splitToList(path).stream().anyMatch(String::isBlank)) {
            throwFormatError(url);
        }

        return new SecretURL(provider, path, key, query, isURI);
    }

    public boolean isKeyEmpty() {
        return key == null || key.isBlank();
    }

    private static void throwFormatError(String url) {
        throw new IllegalArgumentException(
            "Secret URL '%s' should have the following format %s<provider>/<path or name>[:<key>][?option=value1&option=value2]".formatted(
                    url,
                    SCHEME
                )
        );
    }

    private static Multimap<String, String> parseQuery(String substring) {
        Multimap<String, String> query = MultimapBuilder.hashKeys().arrayListValues().build();
        queryParamSplitter
            .split(substring)
            .forEach(pair -> {
                Iterable<String> parts = queryParamKeyValueSplitter.split(pair);
                Iterator<String> iterator = parts.iterator();
                if (iterator.hasNext()) {
                    String key = iterator.next();
                    if (iterator.hasNext()) {
                        query.put(key, iterator.next());
                    } else {
                        query.put(key, "true");
                    }
                }
            });
        return query;
    }

    /**
     * Search query string for 'watch' with value 'true'
     *
     * @return true if <code>watch=true</code> was found.
     */
    public boolean isWatchable() {
        return queryParamEqualsIgnoreCase(WellKnownQueryParam.WATCH, "true");
    }

    /**
     * Test existence of a query param
     * @param name query param name
     * @return true if it exists
     */
    public boolean queryParamExists(String name) {
        return query.containsKey(name);
    }

    /**
     * Search query string param of given <code>name</code>  with case-insensitive <code>value</code>
     * @param name query param name
     * @param value case-insensitive value to find
     * @return true if name and value is found
     */
    public boolean queryParamEqualsIgnoreCase(@Nonnull String name, String value) {
        return query().entries().stream().anyMatch(e -> Objects.equals(e.getKey(), name) && e.getValue().equalsIgnoreCase(value));
    }

    /**
     * <p>Extract the well-known keys from the <code>keymap</code> query string.</p>
     * <p>format is &lt;well known key&gt;:&lt;key in secret&gt;</p>
     * If the well known key is unknown then it is ignored.
     *
     * @return a map to help extracting well known keys out of the secret.
     * @see SecretMap#handleWellKnownSecretKeys(Map)
     * @see SecretMap.WellKnownSecretKey
     */
    public Map<String, SecretMap.WellKnownSecretKey> wellKnowKeyMap() {
        record Mapping(String secretKey, SecretMap.WellKnownSecretKey wellKnow) {}
        return query()
            .get(WellKnownQueryParam.KEYMAP)
            .stream()
            .map(keyMap -> {
                List<String> mapping = keyMapParamValueSplitter.splitToList(keyMap);
                if (mapping.size() == 2) {
                    // eg. certificate:tls.crt
                    String wellKnown = mapping.get(0).trim().toUpperCase();
                    String secretKey = mapping.get(1).trim();
                    if (wellKnown.isEmpty() || secretKey.isEmpty()) {
                        throw new IllegalArgumentException("keymap '%s' is not valid".formatted(keyMap));
                    }
                    try {
                        return new Mapping(secretKey, SecretMap.WellKnownSecretKey.valueOf(wellKnown));
                    } catch (IllegalArgumentException e) {
                        // no op, will return "empty"
                    }
                }
                return new Mapping(null, null);
            })
            .filter(mapping -> mapping.wellKnow() != null)
            .collect(Collectors.toMap(Mapping::secretKey, Mapping::wellKnow));
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class WellKnownQueryParam {

        public static final String WATCH = "watch";
        public static final String KEYMAP = "keymap";
        public static final String NAMESPACE = "namespace";
        public static final String RESOLVE_BEFORE_WATCH = "resolveBeforeWatch";
    }
}
