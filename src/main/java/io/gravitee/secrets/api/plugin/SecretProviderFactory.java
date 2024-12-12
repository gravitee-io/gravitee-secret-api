package io.gravitee.secrets.api.plugin;

/**
 * This is the plugin class for plugins of type "secret-provider".
 *
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface SecretProviderFactory<T extends SecretManagerConfiguration> {
    /**
     * Creates a new instance of a {@link SecretProvider}
     *
     * @param configuration the configuration object
     * @return a secret provider if the configuration can be consumed.
     */
    SecretProvider create(T configuration);
}