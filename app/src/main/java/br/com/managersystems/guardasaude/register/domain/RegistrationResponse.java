package br.com.managersystems.guardasaude.register.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * POJO that holds the response of a registration request.
 */
public class RegistrationResponse {

    String result;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
