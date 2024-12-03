package io.gravitee.secrets.api.discovery;

/**
 * Finds locations (fields/configurations) that can contain secret references and notifies a {@link DefinitionSecretRefsListener}
 * @param <T> the type of definition
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface DefinitionSecretRefsFinder<T> {
    /**
     * Compute if the <code>definition</code> passed can be processed
     * @param definition the definition to test
     * @return true if it can handle the definition
     */
    boolean canHandle(Object definition);

    /**
     * Return a description of the definition to be used when checking ACLs
     *
     * @param definition the definition object to analyse
     * @param metadata   additional properties related to the definition
     * @return a descriptor of the definition
     */
    DefinitionDescriptor toDefinitionDescriptor(T definition, DefinitionMetadata metadata);

    /**
     * This method searches in <code>definition</code> for location (fields/configurations) possibly containing secret refs
     * @param definition the definition object to process
     * @param listener the listener to notify when a candidate location is found
     */
    void findSecretRefs(T definition, DefinitionSecretRefsListener listener);
}
