package br.com.managersystems.guardasaude.exams.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jan on 3/05/2016.
 */
public class Comment {

    private String date;
    private int authorId;
    private String authorName;
    private String commentText;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The authorId
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     *
     * @param authorId
     * The authorId
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     *
     * @return
     * The authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     *
     * @param authorName
     * The authorName
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     *
     * @return
     * The commentText
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     *
     * @param commentText
     * The commentText
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
