package br.com.managersystems.guardasaude.login.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * POJO containing the fields and getters/setters
 * for the expected response of an authorization request.
 */
public class AuthorisationResult {

    private String success;
    private String code;
    private String token;
    private List<String> roles = new ArrayList<String>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The success
     */
    public String getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    /**
     * @return The code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return The token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return The roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * @param roles The roles
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

