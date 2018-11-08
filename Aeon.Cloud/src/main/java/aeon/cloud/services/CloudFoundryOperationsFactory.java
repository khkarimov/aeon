package aeon.cloud.services;

import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.cloudfoundry.reactor.doppler.ReactorDopplerClient;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;
import org.cloudfoundry.reactor.uaa.ReactorUaaClient;
import org.springframework.stereotype.Service;

/**
 * Factory for CloudFoundryOperations.
 */
@Service
public class CloudFoundryOperationsFactory {

    /**
     * Creates and returns an instance for CloudFoundry operations
     * with the specified credentials.
     *
     * @param apiHost      The PCF API host to connect to.
     * @param username     The username for PCF.
     * @param password     The password for PCF.
     * @param organization The organization to deploy to.
     * @param space        The space to deploy to.
     * @return An instance for CloudFoundry operations.
     */
    public CloudFoundryOperations getCloudFoundryOperations(
            String apiHost,
            String username,
            String password,
            String organization,
            String space
    ) {

        DefaultConnectionContext connectionContext = DefaultConnectionContext.builder()
                .apiHost(apiHost)
                .build();

        PasswordGrantTokenProvider tokenProvider = PasswordGrantTokenProvider.builder()
                .password(password)
                .username(username)
                .build();

        ReactorCloudFoundryClient cloudFoundryClient = ReactorCloudFoundryClient.builder()
                .connectionContext(connectionContext)
                .tokenProvider(tokenProvider)
                .build();

        ReactorDopplerClient dopplerClient = ReactorDopplerClient.builder()
                .connectionContext(connectionContext)
                .tokenProvider(tokenProvider)
                .build();

        ReactorUaaClient uaaClient = ReactorUaaClient.builder()
                .connectionContext(connectionContext)
                .tokenProvider(tokenProvider)
                .build();

        return DefaultCloudFoundryOperations.builder()
                .cloudFoundryClient(cloudFoundryClient)
                .dopplerClient(dopplerClient)
                .uaaClient(uaaClient)
                .organization(organization)
                .space(space)
                .build();
    }
}
