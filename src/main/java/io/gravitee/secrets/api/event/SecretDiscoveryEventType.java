package io.gravitee.secrets.api.event;

/**
 * Type of discovery event.
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public enum SecretDiscoveryEventType {
    /**
     * Event type emitted when user deploy or create/update
     * a definition that need secrets to be discovered
     */
    DISCOVER,
    /**
     * Event type emitted when the user un-deploy/deletes
     * definition to perform clean-up and evictions
     */
    REVOKE,
}
