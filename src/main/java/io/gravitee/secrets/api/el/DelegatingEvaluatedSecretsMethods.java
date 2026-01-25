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

import io.gravitee.el.spel.context.DeferredFunctionHolder;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;

/**
 * Class that delegate to the actual implementation in a plugin.
 * This exists to be white-listed in expression-language dependency
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
@RequiredArgsConstructor
public class DelegatingEvaluatedSecretsMethods implements EvaluatedSecretsMethods, DeferredFunctionHolder {

    private final EvaluatedSecretsMethods delegate;

    @Override
    public Single<String> fromGrant(String contextId, SecretFieldAccessControl secretFieldAccessControl) {
        return delegate.fromGrant(contextId, secretFieldAccessControl);
    }

    @Override
    public Single<String> fromGrant(String contextId, String secretKey, SecretFieldAccessControl secretFieldAccessControl) {
        return delegate.fromGrant(contextId, secretKey, secretFieldAccessControl);
    }

    @Override
    public Single<String> fromEL(String contextId, String uriOrName, SecretFieldAccessControl secretFieldAccessControl) {
        return delegate.fromEL(contextId, uriOrName, secretFieldAccessControl);
    }
}
