package br.com.managersystems.guardasaude.login;

import android.content.SharedPreferences;

import br.com.managersystems.guardasaude.login.domain.AuthorisationResult;

/**
 * Created by Jan on 15/04/2016.
 */
public interface ILoginInteractor {
    void handleRequestLoginAttempt(OnLoginFinishedListener listener, String email64, String password64);
    void handleAuthorisationResult(OnLoginFinishedListener listener, AuthorisationResult authorisationResult, String email64, String password64);
    void saveUserInfo(OnLoginFinishedListener listener, SharedPreferences.Editor editor, boolean patient);
}
