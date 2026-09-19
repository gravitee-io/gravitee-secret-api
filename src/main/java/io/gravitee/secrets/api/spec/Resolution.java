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
package io.gravitee.secrets.api.spec;

import java.time.Duration;

/**
 * Defines how the resolution is performed
 * @param type type of resolution
 * @param duration poll interval when used with {@link Resolution.Type#POLL}
 *                 time-to-live when used with {@link Resolution.Type#TTL}
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public record Resolution(Type type, Duration duration) {
    public Resolution {
        boolean ok = type == Type.ONCE || (duration != null && duration.toMillis() > 0);
        if (!ok) {
            throw new IllegalArgumentException("resolution lacks positive duration for type %s".formatted(type));
        }
    }

    public enum Type {
        /**
         * The secret value is fetch only once
         */
        ONCE,
        /**
         * The secret value is fetch regularly following an interval defined by  {@link Resolution#duration()}
         */
        POLL,
        /**
         * The secret value is fetch once,
         * then when pulling the value from the cache, if the secret expiration exceeds the time-to-live defined in {@link Resolution#duration()}
         * then it will be fetched again in the background
         */
        TTL,
    }
}
