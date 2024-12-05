package io.gravitee.secrets.api.core;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface WithExpiration {
    Optional<Instant> expiresAt();

    default boolean isExpired() {
        return expiresAt().map(instant -> Instant.now().isAfter(instant)).orElse(false);
    }
}
