package io.gravitee.secrets.api.el;

import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
@RequiredArgsConstructor
public class DelegatingEvaluatedSecretsMethods implements EvaluatedSecretsMethods {

    private final EvaluatedSecretsMethods delegate;

    @Override
    public String fromGrant(String contextId, SecretFieldAccessControl runtimeContext) {
        return delegate.fromGrant(contextId, runtimeContext);
    }

    @Override
    public String fromGrant(String contextId, String secretKey, SecretFieldAccessControl runtimeContext) {
        return delegate.fromGrant(contextId, secretKey, runtimeContext);
    }

    @Override
    public String fromEL(
        String envId,
        String uriOrName,
        String definitionKind,
        String definitionId,
        List<String> locations,
        SecretFieldAccessControl runtimeContext
    ) {
        return delegate.fromEL(envId, uriOrName, definitionKind, definitionId, locations, runtimeContext);
    }
}
