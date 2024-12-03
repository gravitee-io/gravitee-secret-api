package io.gravitee.secrets.api.spec;

import io.gravitee.common.utils.IdGenerator;
import io.gravitee.secrets.api.core.SecretURL;
import io.gravitee.secrets.api.el.FieldKind;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import org.springframework.util.StringUtils;

/**
 * Represent what can be configured to resolve secrets and manage their lifecycle.
 *
 * @param id              generated UUID. Optional when <code>isGenerated</code> is true
 * @param name            unique per environment name when set.
 *                        Constraints are:
 *                        length is min 3,
 *                        cannot start and end with non-alphanumeric chars,
 *                        can contain '_', ' ', '.', '-' and  alphanumeric chars
 * @param uri             mandatory string that designate the secret to fetch as specified by the secret manager.
 *                        It follows the {@link SecretURL} syntax: /&lt;provider id or plugin id>/&lt;secret location>
 * @param key             the key in the secret map (if known)
 * @param isELKey  when the key is computed at runtime hence true when <code>key</code> param is null
 * @param isGenerated     if the spec was generated from a reference, as opposed create by a user
 * @param resolution      how the secret is resolved
 * @param onErrorStrategy
 * @param acls            access control list: where in definitions secrets are allowed to be resolved
 * @param envId           mandatory environment ID in which this definition applies
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 * @see Resolution
 * @see ACLs
 */
public record SecretSpec(
    String id,
    String name,
    String uri,
    String key,
    boolean isELKey,
    boolean isGenerated,
    Resolution resolution,
    OnErrorStrategy onErrorStrategy,
    ACLs acls,
    String envId
) {
    public SecretSpec {
        boolean ok = isGenerated ^ StringUtils.hasText(id);
        if (!ok) {
            throw new IllegalArgumentException("spec is either generated or contains an id");
        }
        ok = isELKey ^ StringUtils.hasText(key);
        if (!ok) {
            throw new IllegalArgumentException("spec either uses EL key or specifies one");
        }
        ok = StringUtils.hasText(envId);
        if (!ok) {
            throw new IllegalArgumentException("spec must contain an envId");
        }
        ok = StringUtils.hasText(uri);
        if (!ok) {
            throw new IllegalArgumentException("spec must contain a uri");
        }
        ok = uri.charAt(0) == SecretURL.URL_SEPARATOR;
        if (!ok) {
            throw new IllegalArgumentException("uri must start with '%s'".formatted(SecretURL.URL_SEPARATOR));
        }
        if (name != null) {
            assertName(name);
        }
    }

    /**
     * Return uri and key concatenated as specified in {@link SecretSpec#formatUriAndKey(String, String)}
     * @return a string concat of uri and key
     */
    public String uriAndKey() {
        return formatUriAndKey(uri, key);
    }

    /**
     * The spec converted to a SecretURL, it is then used to resolve the secret
     * @return the spec as a SecretURL
     */
    public SecretURL toSecretURL() {
        return SecretURL.from(uriAndKey(), false);
    }

    /**
     * Simple string representation of unique data of the secret
     * @return name if set or the uri
     */
    public String asSimpleString() {
        return name != null && !name.isEmpty() ? name : uri;
    }

    /**
     * Shortcut to {@link ACLs#fieldKind()}
     * @return fieldKind or null
     */
    public FieldKind allowedFieldKind() {
        return acls != null ? acls.fieldKind() : null;
    }
    /**
     * Shortcut to extract all allowed fields from all {@link ACLs#plugins()}
     * @return a non-null set of allowed fields from ACLs object.
     */
    public Set<String> allowedFields() {
        if (acls != null && acls.plugins() != null) {
            return acls()
                .plugins()
                .stream()
                .flatMap(pl -> {
                    if (pl.fields() == null) {
                        return Stream.empty();
                    }
                    return pl.fields().stream();
                })
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        }
        return Set.of();
    }

    /**
     * Checks if the Spec has the given resolution type.
     * If {@link Resolution.Type#ONCE} is passed then it matched with a <code>null</code> resolution
     * @param type type of resolution to check
     * @return true of the type passed matches the one in the spec
     */
    public boolean hasResolutionType(@Nonnull Resolution.Type type) {
        if (type == Resolution.Type.ONCE) {
            return resolution == null || resolution.type() == Resolution.Type.ONCE;
        }
        return resolution != null && type.equals(resolution.type());
    }

    /**
     * @return main uri and optionally key concat with {@link SecretURL#URI_KEY_SEPARATOR}
     */
    public static String formatUriAndKey(String uri, String key) {
        if (key == null) {
            return uri;
        }
        return uri.concat(SecretURL.URI_KEY_SEPARATOR).concat(key);
    }

    /*
     * char by analysis to avoid using regexp on a user input
     */
    private void assertName(@Nonnull String name) {
        if (name.length() < 3) {
            throw new IllegalArgumentException("spec name min length is 3");
        }

        if (!Objects.equals(IdGenerator.generate(name), name)) {
            throw new IllegalArgumentException("spec name is not normalized");
        }
    }
}
