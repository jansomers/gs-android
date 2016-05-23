package br.com.managersystems.guardasaude.login;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.login.domain.AuthorisationResult;
import br.com.managersystems.guardasaude.login.domain.MobileToken;

public interface OnLoginFinishedListener {

    /**
     * Passes the information to the interactor for further authorization in case of a  successful request.
     * @param listener OnLoginFinishedListener that represents the expecting listener.
     * @param authorisationResult Authorisation result that is received by the request.
     * @param username64 String that represents the encoded username.
     */
    void onHandleRequestLoginAttemptSuccess(OnLoginFinishedListener listener, AuthorisationResult authorisationResult, String username64);
    /**
     * Forwards the failed request to the view.
     */
    void onHandleRequestLoginAttemptFailure();

    /**
     * Does the role handling and alerts the view accordingly:
     *  Login is unsuccessful if there the roles list is empty.
     *  Login is successful when a correct role or multiple roles are identified.
     *
     * @param roles ArrayList of String that represents the roles of the authenticated user.
     * @param token MobileToken that holds important information.
     */
    void onAuthorizeSuccess(ArrayList<String> roles, MobileToken token);

    /**
     * Forwards the failed authorization to the view.
     * @param code String that represents the reason of the failure.
     */
    void onAuthorizeFailure(String code);

    /**
     * Identification when info is done saving.
     */
    void onSavedInfo();

    void onDeletedInfo();
}
