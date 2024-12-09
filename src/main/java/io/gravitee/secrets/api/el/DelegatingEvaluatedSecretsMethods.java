package io.gravitee.secrets.api.el;

import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * Class that delegate to the actual implementation in a plugin.
 * This exists to be white-listed in expression-language dependency
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
@RequiredArgsConstructor
public class DelegatingEvaluatedSecretsMethods implements EvaluatedSecretsMethods {

    private final EvaluatedSecretsMethods delegate;

    @Override
    public String fromGrant(String contextId, SecretFieldAccessControl secretFieldAccessControl) {
        return delegate.fromGrant(contextId, secretFieldAccessControl);
    }

    @Override
    public String fromGrant(String contextId, String secretKey, SecretFieldAccessControl secretFieldAccessControl) {
        return delegate.fromGrant(contextId, secretKey, secretFieldAccessControl);
    }

    @Override
    public String fromEL(
        String envId,
        String uriOrName,
        String definitionKind,
        String definitionId,
        List<String> locations,
        SecretFieldAccessControl secretFieldAccessControl
    ) {
        return delegate.fromEL(envId, uriOrName, definitionKind, definitionId, locations, secretFieldAccessControl);
    }
}
