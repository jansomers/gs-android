package br.com.managersystems.guardasaude.exams.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * POJO that holds the response after retrieving all comments.
 */
public class CommentResponse {
    private String dataClassification;
    private String examIdentification;
    private int quantity;
    private List<Comment> comments = new ArrayList<Comment>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The dataClassification
     */
    public String getDataClassification() {
        return dataClassification;
    }

    /**
     *
     * @param dataClassification
     * The dataClassification
     */
    public void setDataClassification(String dataClassification) {
        this.dataClassification = dataClassification;
    }

    /**
     *
     * @return
     * The examIdentification
     */
    public String getExamIdentification() {
        return examIdentification;
    }

    /**
     *
     * @param examIdentification
     * The examIdentification
     */
    public void setExamIdentification(String examIdentification) {
        this.examIdentification = examIdentification;
    }

    /**
     *
     * @return
     * The quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     * The quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     * The comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

