package io.gravitee.secrets.api.event;

import io.gravitee.secrets.api.discovery.DefinitionMetadata;

/**
 * Event that represents a discovery action to be performed.
 * @param envId the environment ID
 * @param definition the definition object
 * @param metadata definition metadata
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public record SecretDiscoveryEvent(String envId, Object definition, DefinitionMetadata metadata) {}
