package io.gravitee.secrets.api.el;

import java.util.List;

/**
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface EvaluatedSecretsMethods {
    String fromGrant(String contextId, SecretFieldAccessControl runtimeContext);

    String fromGrant(String contextId, String secretKey, SecretFieldAccessControl runtimeContext);

    String fromEL(
        String envId,
        String uriOrName,
        String definitionKind,
        String definitionId,
        List<String> locations,
        SecretFieldAccessControl runtimeContext
    );
}
