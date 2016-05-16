package br.com.managersystems.guardasaude.login.domain;

/**
 * POJO class holding the information about a User.
 */
public class BaseUser {
    private static BaseUser mInstance = null;

    private String identifierB64;
    private String passwordB64;

    private BaseUser() {
        identifierB64 ="empty id";
        passwordB64= "empty_password";
    }

    public static BaseUser getInstance(){
        if (mInstance ==null) {
            mInstance = new BaseUser();
        }
        return mInstance;
    }

    public String getIdentifierB64() {
        return identifierB64;
    }

    public void setIdentifierB64(String identifierB64) {
        this.identifierB64 = identifierB64;
    }

    public String getPasswordB64() {
        return passwordB64;
    }

    public void setPasswordB64(String passwordB64) {
        this.passwordB64 = passwordB64;
    }
}
