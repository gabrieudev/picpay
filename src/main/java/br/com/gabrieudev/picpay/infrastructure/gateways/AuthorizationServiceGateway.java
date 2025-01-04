package br.com.gabrieudev.picpay.infrastructure.gateways;

import org.springframework.stereotype.Service;

import br.com.gabrieudev.picpay.application.gateways.AuthorizationGateway;
import br.com.gabrieudev.picpay.infrastructure.client.AuthorizationClient;

@Service
public class AuthorizationServiceGateway implements AuthorizationGateway {
    private final AuthorizationClient authorizationClient;

    public AuthorizationServiceGateway(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    @Override
    public boolean isAuthorized() {
        return authorizationClient.authorize().getStatusCode().is2xxSuccessful();
    }
    
}
