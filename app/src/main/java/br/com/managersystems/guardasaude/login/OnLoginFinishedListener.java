package br.com.managersystems.guardasaude.login;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.login.domain.AuthorisationResult;
import br.com.managersystems.guardasaude.login.domain.MobileToken;

public interface OnLoginFinishedListener {

    void onHandleRequestLoginAttemptSuccess(OnLoginFinishedListener listener, AuthorisationResult authorisationResult, String email64, String password64);
    void onHandleRequestLoginAttemptFailure();
    void onAuthorizeSuccess(ArrayList<String> roles, MobileToken token);
    void onAuthorizeFailure(String code);
    void complete();
}
