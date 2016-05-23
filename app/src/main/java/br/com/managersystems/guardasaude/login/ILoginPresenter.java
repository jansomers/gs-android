package br.com.managersystems.guardasaude.login;

import br.com.managersystems.guardasaude.ui.dialogs.ForgotPasswordDialog;

/**
 * This interface serves as a transportation layer between the view and the interactor.
 * In this case it handles authorisation requests.
 * <p/>
 * The documentation briefly explain what the method does.
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *         <p/>
 *         Implementations / Also see:
 * @see LoginPresenter
 * @see ILoginInteractor
 */
public interface ILoginPresenter {

    /**
     * Passes the credentials encoded to the loginInteractor
     *
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
     *
     * @param patient Boolean that represents if the profile is a patient or professional.
     */
    void requestSaveInfo(boolean patient);

    /**
     * Checks if the token expiration date is still in the future.
     *
     * @param expires String that represents the expiration date of the current token.
     * @return Boolean object that is true if user is patient and false if user isn't.
     */
    boolean validateToken(String expires);

    /**
     * Forwards the request to logout.
     */
    void logout();

    /**
     * Calls getAnonymousExam method from interactor.
     *
     * @param accessCodeString String object representing the access code of the exam.
     * @param examIdString     String object representing the identification of the exam.
     */
    void retrieveAnonymousExam(String accessCodeString, String examIdString);

    /**
     * Calls the interactor to reset the password.
     *
     * @param dialog         ForgotPasswordDialog object.
     * @param forgotPwdEmail String object representing the email address of the user.
     */
    void requestPassWord(ForgotPasswordDialog dialog, String forgotPwdEmail);
}
