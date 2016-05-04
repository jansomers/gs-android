package br.com.managersystems.guardasaude.exams.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jan on 4/05/2016.
 */
public class PostCommentResponse {

    private String result;
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
