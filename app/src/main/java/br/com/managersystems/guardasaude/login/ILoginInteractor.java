package br.com.managersystems.guardasaude.login;

import android.content.SharedPreferences;

import br.com.managersystems.guardasaude.login.domain.AuthorisationResult;

/**
 * This interface consists of methods needed to succesfully handle authorization and login requests.
 *
 * In every method, the listener is called, which is, in this case, an instance of the LoginPresenter.
 *
 * The documentation briefly explains what the method does.
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementations / Also see:
 *
 * @see LoginInteractor
 * @see OnLoginFinishedListener
 * @see LoginPresenter
 *
 */
public interface ILoginInteractor {

    /**
     * Handles the request to login and alerts the login presenter (who's listening):
     *    The request can fail if the response holds no information or if the call was unsuccessful.
     *    If the request is successful, the result is passed onto the the listener.
     * @param listener The OnloginFinishedListener that is expecting a notification when the call has finished.
     * @param username64 String that represents the base64 encoded username.
     * @param password64 String that represents the base64 encoded password.
     */
    void handleRequestLoginAttempt(OnLoginFinishedListener listener, String username64, String password64);

    /**
     * Authorizes the user and alerts the login presenter (who's listening):
     *      The authorization fails if the authorisation result's field success equals false.
     *      If the authorization is successful the base user is saved and a mobile token is generated and passed to the presenter.
     *
     * @param listener The OnLoginFinishedListener that is expecting a notification when the call has finished.
     * @param authorisationResult AuthorisationResult that is received from the request.
     * @param username64 String that represents the base64 encoded username.
     */
    void handleAuthorisationResult(OnLoginFinishedListener listener, AuthorisationResult authorisationResult, String username64);

    /**
     * Saves the token, user and role in the shared preferences.
     * @param listener  The OnLoginFinishedListener.
     * @param editor The Editor which allows you to edit the shared preferences.
     * @param patient Boolean which indicates if the role that should be saved is patient or professional.
     */
    void saveUserInfo(OnLoginFinishedListener listener, SharedPreferences.Editor editor, boolean patient);

    void deleteUserInfo(OnLoginFinishedListener listener, SharedPreferences.Editor edit);

    void handlePasswordRequest(OnPasswordResetListener listener, String forgotPwdEmail);
}
