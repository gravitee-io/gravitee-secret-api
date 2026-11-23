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
package io.gravitee.secrets.api.event;

/**
 * Event that tells the secrets service a secret has changed in its provider.
 * @param envId the environment the secret belongs to
 * @param provider the secret provider id
 * @param path the path of the secret URL, e.g. {@code llm-key} for {@code /gravitee/llm-key:value?envId=E}
 * @author GraviteeSource Team
 */
public record SecretInvalidationEvent(String envId, String provider, String path) {}
