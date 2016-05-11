package br.com.managersystems.guardasaude.login;

/**
 * This interface serves as a transportation layer between the view and the interactor.
 * In this case it handles authorisation requests.
 *
 * The documentation briefly explain what the method does.
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementations / Also see:
 *
 * @see LoginPresenter
 * @see ILoginInteractor
 *
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

    /**
     * Checks if the token expiration date is still in the future.
     * @param expires String that represents the expiration date of the current token.
     * @return
     */
    boolean validateToken(String expires);

    /**
     * Forwards the request to logout.
     */
    void logout();

    void retrieveAnonymousExam(String accessCodeString, String examIdString);
}
