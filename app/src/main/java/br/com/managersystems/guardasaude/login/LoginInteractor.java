package br.com.managersystems.guardasaude.login;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import br.com.managersystems.guardasaude.login.domain.AuthorisationResult;
import br.com.managersystems.guardasaude.login.domain.BaseUser;
import br.com.managersystems.guardasaude.login.domain.MobileToken;
import br.com.managersystems.guardasaude.login.domain.UserRoleEnum;
import br.com.managersystems.guardasaude.util.Base64Interactor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is an implementation of the ILoginInteractor
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Also see:
 * @see ILoginInteractor
 */
public class LoginInteractor implements ILoginInteractor {

    private final String BASE_URL = "https://guardasaude.com.br/";
    private LoginApi client;

    public LoginInteractor() {
        client = initiateRetrofit();
    }

    /**
     * Initiates the retrofit instances for the LoginApi.
     * @return LoginApi instance representing the client.
     */
    private LoginApi initiateRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(LoginApi.class);
    }


    @Override
    public void handleRequestLoginAttempt(final OnLoginFinishedListener listener, final String username64, final String password64) {
        if (client == null) {
            initiateRetrofit();
        }
        Call<AuthorisationResult> call = client.authenticateUser(username64, password64);
        call.enqueue(new Callback<AuthorisationResult>() {
            @Override
            public void onResponse(Call<AuthorisationResult> call, Response<AuthorisationResult> response) {
                if (response.body() == null) {
                    Log.d(this.getClass().getSimpleName(), "Response has an empty body, unauthorizing");
                    listener.onHandleRequestLoginAttemptFailure();
                } else {
                    AuthorisationResult authorisationResult = response.body();
                    listener.onHandleRequestLoginAttemptSuccess(listener, authorisationResult, username64);

                }
            }

            @Override
            public void onFailure(Call<AuthorisationResult> call, Throwable t) {
                Log.d(this.getClass().getSimpleName(), "IN ONFAILURE CALL", t.getCause());
                listener.onHandleRequestLoginAttemptFailure();
            }
        });

    }


    @Override
    public void handleAuthorisationResult(OnLoginFinishedListener listener, AuthorisationResult authorisationResult, String username64) {

        if (authorisationResult.getSuccess().equals("false"))
            listener.onAuthorizeFailure(authorisationResult.getCode());
        else {
            BaseUser.getInstance().setIdentifierB64(username64);
            MobileToken token = new MobileToken(BaseUser.getInstance(), authorisationResult.getToken(), new Date());
            listener.onAuthorizeSuccess((ArrayList<String>) authorisationResult.getRoles(), token);
        }
    }

    @Override
    public void saveUserInfo(OnLoginFinishedListener listener, SharedPreferences.Editor editor, boolean patient) {
        Log.d(this.getClass().getSimpleName(), "Succesful Auhorization... Saving token, user, expiredate");
        editor.putString("token", MobileToken.getToken());
        editor.putString("user", MobileToken.getBaseUser().getIdentifierB64());
        Log.d(this.getClass().getSimpleName(), "Mobile token enddate" + MobileToken.getEndDate().toString());
        editor.putString("expires", MobileToken.getEndDate().toString());
        editor.putString("role", patient ? UserRoleEnum.ROLE_PATIENT.toString() : UserRoleEnum.ROLE_HEALTH_PROFESSIONAL.toString());
        editor.apply();
        Log.d(this.getClass().getSimpleName(), "Token: " + MobileToken.getToken() + " + User: " + new Base64Interactor().decodeBase64ToString(MobileToken.getBaseUser().getIdentifierB64().getBytes()) + " + Expire date: " + MobileToken.getEndDate() + " committed to SharedPreferences");
        listener.onSavedInfo();

    }

    @Override
    public void deleteUserInfo(OnLoginFinishedListener listener, SharedPreferences.Editor editor) {
        editor.putString("token", "");
        editor.putString("user", "");
        editor.putString("expires", "");
        editor.putString("role", "");
        editor.apply();
        listener.onDeletedInfo();
    }

}
