package br.com.managersystems.guardasaude.login;

/**
 * Created by Jan on 14/04/2016.
 */
public interface ILoginPresenter {

    /**
     * Passes the credentials encoded to the logininteractor
     * @param username String that represents the username.
     * @param password String that represents the password.
     */
    void authorizeLogin(String username, String password);

    /**
     * TODO To be implemented
     */
    void retrieveDomains();

    /**
     * Forwards the request to save the information.
     * @param patient Boolean that represents if the profile is a patient or professional.
     */
    void requestSaveInfo(boolean patient);

    boolean validateToken(String expires);

    void logout();
}
