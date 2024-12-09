package io.gravitee.secrets.api.errors;

/**
 * Base exception for all secret errors in the secret service plugin
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
public class ServiceSecretsException extends RuntimeException {

    public ServiceSecretsException() {}

    public ServiceSecretsException(String message) {
        super(message);
    }

    public ServiceSecretsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceSecretsException(Throwable cause) {
        super(cause);
    }
}
