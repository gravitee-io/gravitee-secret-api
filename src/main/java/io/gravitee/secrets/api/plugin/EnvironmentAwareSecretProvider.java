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
package io.gravitee.secrets.api.plugin;

import io.gravitee.secrets.api.core.SecretMap;
import io.gravitee.secrets.api.core.SecretURL;
import io.gravitee.secrets.api.errors.SecretManagerException;
import io.reactivex.rxjava3.core.Maybe;

/**
 * A secret provider that the node declares as a bean, instead of a plugin configured in gravitee.yml. The secrets
 * service registers it for every environment, and passes the environment of the caller on each resolve.
 * <p>
 * It is a separate type from {@link SecretProvider} so that a provider plugin can never be called without an
 * environment, and an environment-aware provider can never be called with one taken from a reference.
 */
public interface EnvironmentAwareSecretProvider {
    /**
     * The provider part of a secret URL that this provider answers, e.g. {@code gravitee} for {@code /gravitee/my-key:value}.
     */
    String id();

    /**
     * Resolves a secret of one environment. If the secret does not exist in that environment, {@link Maybe#empty()} is
     * returned, even when another environment holds a secret with the same path.
     * Any errors are inheritors of {@link SecretManagerException} and signaled in the Maybe.
     *
     * @param environmentId the environment of the caller, set by the secrets service and never by the reference
     * @param secretURL where the secret is located
     * @return a secret map (all keys of a secret)
     */
    Maybe<SecretMap> resolve(String environmentId, SecretURL secretURL);

    /**
     * Performs startup logic if need be. It is called once, before the provider is registered.
     *
     * @return self
     * @throws SecretManagerException in case the provider cannot be started
     */
    default EnvironmentAwareSecretProvider start() throws SecretManagerException {
        return this;
    }

    /**
     * Stops what needs to be stopped when the node stops.
     *
     * @return self
     */
    default EnvironmentAwareSecretProvider stop() {
        return this;
    }
}
