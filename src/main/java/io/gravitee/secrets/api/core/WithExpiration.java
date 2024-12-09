package io.gravitee.secrets.api.core;

import java.time.Instant;
import java.util.Optional;

/**
 * Represents something that can expire.
 *
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface WithExpiration {
    /**
     * Specifies the expiration if any
     * @return the {@link Instant} of expiration if any
     */
    Optional<Instant> expiresAt();

    /**
     * Check expiration is reached when calling this method
     * @return true if there is an expiration set and it has expired
     */
    default boolean isExpired() {
        return expiresAt().map(instant -> Instant.now().isAfter(instant)).orElse(false);
    }
}
