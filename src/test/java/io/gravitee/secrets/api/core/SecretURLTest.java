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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SecretURLTest {

    static Stream<Arguments> workingURLs() {
        return Stream.of(
            arguments("secret://foo/bar", "foo", "bar", null, Map.of(), false),
            arguments("secret://foo/bar/", "foo", "bar", null, Map.of(), false),
            arguments("secret://foo/bar:key", "foo", "bar", "key", Map.of(), false),
            arguments("secret://foo/bar:key ", "foo", "bar", "key", Map.of(), false),
            arguments("secret://foo/bar/fiz:key ", "foo", "bar/fiz", "key", Map.of(), false),
            arguments("secret://foo/bar//", "foo", "bar", null, Map.of(), false),
            arguments("secret://foo/bar/puk", "foo", "bar/puk", null, Map.of(), false),
            arguments("secret://foo/bar/puk?", "foo", "bar/puk", null, Map.of(), false),
            arguments("secret://foo/bar/puk?watch", "foo", "bar/puk", null, Map.of("watch", List.of("true")), true),
            arguments("secret://foo/bar/puk?watch=true", "foo", "bar/puk", null, Map.of("watch", List.of("true")), true),
            arguments("secret://foo/bar/puk:key?watch=True", "foo", "bar/puk", "key", Map.of("watch", List.of("True")), true),
            arguments("secret://foo/bar/puk?watch=false", "foo", "bar/puk", null, Map.of("watch", List.of("false")), false),
            arguments(
                "secret://foo/bar/puk?watch=true&beer",
                "foo",
                "bar/puk",
                null,
                Map.of("watch", List.of("true"), "beer", List.of("true")),
                true
            ),
            arguments(
                "secret://foo/bar/puk?watch=false&exclude=7&exclude=9",
                "foo",
                "bar/puk",
                null,
                Map.of("watch", List.of("false"), "exclude", List.of("7", "9")),
                false
            )
        );
    }

    @ParameterizedTest
    @MethodSource("workingURLs")
    void should_parse_url(String url, String provider, String path, String key, Map<String, Collection<String>> query, boolean watch) {
        SecretURL cut = SecretURL.from(url);
        assertThat(cut.provider()).isEqualTo(provider);
        assertThat(cut.path()).isEqualTo(path);
        assertThat(cut.key()).isEqualTo(key);
        assertThat(cut.query().asMap()).containsAllEntriesOf(query);
        assertThat(cut.query().asMap().keySet()).allMatch(cut::queryParamExists);
        assertThat(cut.isWatchable()).isEqualTo(watch);
    }

    static Stream<Arguments> nonWorkingURLs() {
        return Stream.of(
            arguments("/foo/bar"),
            arguments("hey://foo/bar"),
            arguments("secret:/foo"),
            arguments("secret:/foo"),
            arguments("secret://foo"),
            arguments("secret://foo:key"),
            arguments("secret://foo/"),
            arguments("secret://foo/:key"),
            arguments("secret://foo?"),
            arguments("secret://foo/?"),
            arguments("secret://foo/ /?"),
            arguments("secret://foo/ /bar?"),
            arguments("secret://foo//bar?")
        );
    }

    @ParameterizedTest
    @MethodSource("nonWorkingURLs")
    void should_not_parse_url(String url) {
        assertThatCode(() -> SecretURL.from(url))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("should have the following format");
    }

    public static Stream<Arguments> wellKnowKeysURLs() {
        return Stream.of(
            arguments(
                "secret://foo/bar?keymap=certificate:tls.crt&keymap=private_key:tls.key",
                Map.of("tls.crt", SecretMap.WellKnownSecretKey.CERTIFICATE, "tls.key", SecretMap.WellKnownSecretKey.PRIVATE_KEY)
            ),
            arguments(
                "secret://foo/bar:key?keymap=certificate:tls.crt&keymap=private_key:tls.key",
                Map.of("tls.crt", SecretMap.WellKnownSecretKey.CERTIFICATE, "tls.key", SecretMap.WellKnownSecretKey.PRIVATE_KEY)
            ),
            arguments(
                "secret://foo/bar?keymap=username:user&keymap=password:passwd",
                Map.of("user", SecretMap.WellKnownSecretKey.USERNAME, "passwd", SecretMap.WellKnownSecretKey.PASSWORD)
            ),
            arguments(
                "secret://foo/bar?keymap=certificate:tls.crt&keymap=key:tls.key",
                Map.of("tls.crt", SecretMap.WellKnownSecretKey.CERTIFICATE)
            ),
            arguments(
                "secret://foo/bar?keymap=cert:tls.crt&keymap=private_key:tls.key",
                Map.of("tls.key", SecretMap.WellKnownSecretKey.PRIVATE_KEY)
            ),
            arguments("secret://foo/bar?keymap=private_key:tls.key", Map.of("tls.key", SecretMap.WellKnownSecretKey.PRIVATE_KEY)),
            arguments("secret://foo/bar?keymap=foo:tls.crt&keymap=bar:tls.key", Map.of()),
            arguments("secret://foo/bar?keymap=foo&keymap=bar", Map.of())
        );
    }

    @ParameterizedTest
    @MethodSource("wellKnowKeysURLs")
    void should_have_well_known_mapping(String url, Map<String, SecretMap.WellKnownSecretKey> expected) {
        SecretURL cut = SecretURL.from(url);
        assertThat(cut.wellKnowKeyMap()).containsAllEntriesOf(expected);
    }

    public static Stream<Arguments> failingWellKnownKeysURLs() {
        return Stream.of(
            arguments("secret://foo/bar?keymap=certificate:&keymap=private_key:foo"),
            arguments("secret://foo/bar:key?keymap=certificate:&keymap=private_key:foo"),
            arguments("secret://foo/bar?keymap=certificate: &keymap=private_key:foo"),
            arguments("secret://foo/bar?keymap=:tls.key&keymap=private_key:foo"),
            arguments("secret://foo/bar?keymap= :tls.key&keymap=private_key:foo"),
            arguments("secret://foo/bar?keymap=:&keymap=private_key:foo"),
            arguments("secret://foo/bar?keymap=: &keymap=private_key:foo"),
            arguments("secret://foo/bar?keymap= : &keymap=private_key:foo")
        );
    }

    @ParameterizedTest
    @MethodSource("failingWellKnownKeysURLs")
    void should_fail_parsing_well_known_mapping_error(String url) {
        SecretURL cut = SecretURL.from(url);
        assertThatThrownBy(cut::wellKnowKeyMap).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_parse_uri() {
        SecretURL cut = SecretURL.from("/foo/bar:baz?buz=pUUUk", true);
        assertThat(cut.provider()).isEqualTo("foo");
        assertThat(cut.path()).isEqualTo("bar");
        assertThat(cut.key()).isEqualTo("baz");
        assertThat(cut.queryParamEqualsIgnoreCase("buz", "puuuk")).isTrue();
    }

    @Test
    void should_fail_parse_uri() {
        assertThatCode(() -> SecretURL.from("/foo", false))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("should have the following format");
    }
}
