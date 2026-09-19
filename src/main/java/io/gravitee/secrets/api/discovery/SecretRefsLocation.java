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
package io.gravitee.secrets.api.discovery;

import org.springframework.util.StringUtils;

/**
 * Indicates where is located a string candidate to contain secret refs (plugin, mAPI configuration etc.)
 * @param kind a string to specify the kind of entity is holding the candidate string (e.g. plugin)
 * @param id a string to identify specifically the kind (e.g. plugin id)
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public record SecretRefsLocation(String kind, String id) {
    public static final String PLUGIN_KIND = "plugin";

    public SecretRefsLocation {
        boolean ok = StringUtils.hasText(kind) && StringUtils.hasText(id);
        if (!ok) {
            throw new IllegalArgumentException("secret refs location must have a kind and an id");
        }
    }

    public static final SecretRefsLocation NOWHERE = new SecretRefsLocation("_", "_");
}
