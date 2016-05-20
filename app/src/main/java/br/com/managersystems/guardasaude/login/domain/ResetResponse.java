package br.com.managersystems.guardasaude.login.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * POJO that holds the response of a reset password request.
 */
public class ResetResponse {

    String method;
    String success;
    String error;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
