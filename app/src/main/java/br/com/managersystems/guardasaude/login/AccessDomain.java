package br.com.managersystems.guardasaude.login;

/**
 * Created by Jan on 15/04/2016.
 */
public class AccessDomain {

    private int id;
    private String tagName;
    private String domainUrl;

    public AccessDomain(int id, String tagName, String domainUrl) {
        this.id = id;
        this.tagName = tagName;
        this.domainUrl = domainUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }
}
