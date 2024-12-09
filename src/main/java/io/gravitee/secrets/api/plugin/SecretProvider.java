/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.secrets.api.plugin;

import io.gravitee.secrets.api.core.SecretEvent;
import io.gravitee.secrets.api.core.SecretMap;
import io.gravitee.secrets.api.core.SecretURL;
import io.gravitee.secrets.api.errors.SecretManagerException;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

/**
 * Base service implemented by the plugin. Instance of this class are created by {@link SecretProviderFactory} instance.
 *
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface SecretProvider {
    String PLUGIN_URL_SCHEME = "secret://";
    String PLUGIN_TYPE = "secret-provider";

    /**
     * Resolve a secret (as a key/value pair) as a {@link Maybe}.
     * If the secret does not exist then {@link Maybe#empty()} is returned.
     * Any errors are inheritors of {@link SecretManagerException} and signaled in the Maybe.
     *
     * @param secretURL where the secret is located. It is implementation responsibility to interpret or parse the value or {@link SecretURL#path()}
     * @return a secret map (all keys of a secret) it may contain expiration information
     */
    Maybe<SecretMap> resolve(SecretURL secretURL);

    /**
     * Watches a secret, no event is published if the secret cannot be found.
     * Although, it may start to emit events afterward if the secret is created in the secret provider.
     * If this secret provider does not support watching secrets.
     * It should not return signal an error, it should rather log it.
     *
     * @param secretURL where the secret is located. It is implementation responsibility to interpret or parse the value or {@link SecretURL#path()}
     * @return a {@link Flowable} of event that contains the secret map of an empty secret map in case of deletion.
     */
    Flowable<SecretEvent> watch(SecretURL secretURL);

    /**
     * Performs startup logic if need be (is called once after a new instance is created)
     *
     * @return self
     * @throws SecretManagerException in case secret provider cannot be started
     */
    default SecretProvider start() throws SecretManagerException {
        return this;
    }

    /**
     * Stops what needs to be stopped when the plugin is no longer required or when the Gateway stops
     *
     * @return self
     */
    default SecretProvider stop() {
        return this;
    }
}
