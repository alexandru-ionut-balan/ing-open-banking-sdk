package com.ing.developer.app.apis.greetings;

import com.ing.developer.showcase.client.ApiException;
import com.ing.developer.showcase.client.api.ShowcaseApiApi;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

import static com.ing.developer.app.common.GenericAdapterErrorHandler.throwInternalizedException;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Component
public class GreetingsAdapter {

    private final ShowcaseApiApi greetingsApi;

    public GreetingsAdapter(ShowcaseApiApi greetingsApi) {
        this.greetingsApi = greetingsApi;
    }

    public String getGreeting() {
        try {
            return greetingsApi.greetingsSingleGet(null).getMessage();
        } catch (ApiException e) {
            return throwInternalizedException(e.getCode(), "greetings", e);
        }
    }

    public String getGreetingMtlsPinning() {
        try {
            greetingsApi.getApiClient().setMTLSPinning(true);
            return greetingsApi.mtlsOnlyGreetingsGet(null).getMessage();
        } catch (ApiException e) {
            return throwInternalizedException(e.getCode(), "greetings mtls", e);
        }
    }

    public String getGreetingJWS() {
        try {
            greetingsApi.getApiClient().setMTLSPinning(true).setJwsSigning(true);
            return greetingsApi.signedGreetingsGet(APPLICATION_FORM_URLENCODED, null, null).getMessage();
        } catch (ApiException e) {
            return throwInternalizedException(e.getCode(), "greetings", e);
        }
    }

}
