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
        return expiresAt()
            .map(instant -> Instant.now().isAfter(instant))
            .orElse(false);
    }
}
