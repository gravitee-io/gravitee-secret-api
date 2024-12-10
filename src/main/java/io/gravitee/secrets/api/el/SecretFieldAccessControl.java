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
package io.gravitee.secrets.api.el;

import javax.annotation.Nullable;

/**
 * Object representing the ability for a secret to be used on a given field.
 * This object is added in the EL context then removed after evaluation.
 * EL expression may use this context to allow or deny access to a secret.
 *
 * @param allowed If a secret can be resolved (true = the plugin/entity field supports secrets)
 * @param kind the secret value kind admitted for this field.
 *             Is <code>null</code> when <code>allowed</code> is <code>false</code>
 * @param name the field name where the secret is used
 *             Can be <code>null</code> when <code>allowed</code> is <code>false</code>
 *
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public record SecretFieldAccessControl(boolean allowed, @Nullable FieldKind kind, @Nullable String name) {
    public static final String EL_VARIABLE = "secret_field_access_control_var";
}
