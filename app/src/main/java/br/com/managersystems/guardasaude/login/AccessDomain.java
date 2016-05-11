package br.com.managersystems.guardasaude.login;

/**
 * POJO Class that represent an access domain.
 * Holds the id, tag and url of that domain.
 */
public class AccessDomain {

    private int id;
    private String tag;
    private String domainUrl;

    public AccessDomain(int id, String tag, String domainUrl) {
        this.id = id;
        this.tag = tag;
        this.domainUrl = domainUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }
}
