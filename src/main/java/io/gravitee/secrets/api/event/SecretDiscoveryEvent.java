package io.gravitee.secrets.api.event;

import io.gravitee.secrets.api.discovery.DefinitionMetadata;

/**
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public record SecretDiscoveryEvent(String envId, Object definition, DefinitionMetadata metadata) {}
