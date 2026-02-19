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

import io.gravitee.secrets.api.spec.SecretSpec;
import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Define methods that are called as an EL to resolve secrets.
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface EvaluatedSecretsMethods {
    Logger DEFAULT_METHODS_LOGGER = LoggerFactory.getLogger(EvaluatedSecretsMethods.class);
    String SECRETS_FEATURE_DISABLED = "[secrets feature disabled]";
    String DISABLED_ERROR_MESSAGE =
        "gravitee-en-secrets feature is required to be in you license to use EL {#secret.get(...)}. " +
        "Check the plugin 'service-secrets' is part of your Gravitee distribution." +
        "'" +
        SECRETS_FEATURE_DISABLED +
        "' has been return instead";

    /**
     * This is called when the user entered a secret ref that is not processed
     * because the plugin is absent or the feature missing in the license
     * @param nameOrUri user configured name or uri
     * @param key user configured key
     * @return a string signaling that the feature is disabled
     */
    default String get(String nameOrUri, String key) {
        return get(nameOrUri);
    }

    /**
     *This is called when the user entered a secret ref that is not processed
     * because the plugin is absent or the feature missing in the license
     * @param nameOrUri user configured name or uri
     * @return a string signaling that the feature is disabled
     */
    default String get(String nameOrUri) {
        DEFAULT_METHODS_LOGGER.error(DISABLED_ERROR_MESSAGE);
        return SECRETS_FEATURE_DISABLED;
    }

    /**
     * Pulls an already resolved secret from the cache only if has been is granted to be used in the context it was discovered into.
     * The discovery context id used a token to check the secret was granted.
     * @param contextId discovery context ID
     * @param secretFieldAccessControl caller of this EL may pass this to describe the field to access control can be performed.
     * @return the secret or an empty string depending on {@link SecretSpec#onErrorStrategy()}
     */
    Single<String> fromGrant(String contextId, SecretFieldAccessControl secretFieldAccessControl);

    /**
     * Pulls an already resolved secret from the cache only if has been is granted to be used in the context it was discovered into.
     * The discovery context id used a token to check the secret was granted. This method is called when the secret key is not known in advance (EL).
     * @param contextId discovery context ID
     * @param secretKey the secret key value (EL evaluated)
     * @param secretFieldAccessControl caller of this EL may pass this to describe the field to access control can be performed.
     * @return the secret or an empty string depending on {@link SecretSpec#onErrorStrategy()}
     */
    Single<String> fromGrant(String contextId, String secretKey, SecretFieldAccessControl secretFieldAccessControl);

    /**
     * Finds or generate (for URIs) a spec from <code>uriOrName</code>.
     * Then using <code>envId</code> <code>definitionKind</code>
     * <code>definitionId</code>  <code>locations</code>
     * creates a temporary discovery context and checks if it is granted
     * @param contextId discovery context ID
     * @param uriOrName evaluated value of the EL set in the ref.
     *                  Starting with '/' it is considered a URI else a name
     * @param secretFieldAccessControl caller of this EL may pass this to describe the field to access control can be performed.
     * @return the secret or an empty string depending on {@link SecretSpec#onErrorStrategy()}
     */
    Single<String> fromEL(String contextId, String uriOrName, SecretFieldAccessControl secretFieldAccessControl);
}
