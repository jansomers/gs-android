package br.com.managersystems.guardasaude.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.ExamOverviewInteractor;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.exammenu.information.anonymousexam.OnAnonymousExamRetrievedListener;
import br.com.managersystems.guardasaude.login.domain.AccessDomain;
import br.com.managersystems.guardasaude.login.domain.AuthorisationResult;
import br.com.managersystems.guardasaude.login.domain.MobileToken;
import br.com.managersystems.guardasaude.login.domain.UserRoleEnum;
import br.com.managersystems.guardasaude.ui.activities.LoginActivity;
import br.com.managersystems.guardasaude.util.Base64Interactor;
/**
 * This class is an implementation of the ILoginPresenter. It also implements the
 * OnReportRetrievedListener.
 *
 * This presenter can be used either from the LoginActivity or from any activity that you log out from.
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Also see:
 * @see ILoginPresenter
 * @see OnDomainRetrievedListener
 * @see OnLoginFinishedListener
 * @see ILoginInteractor
 * @see LoginActivity
*/
public class LoginPresenter implements ILoginPresenter, OnDomainRetrievedListener, OnLoginFinishedListener,OnAnonymousExamRetrievedListener {

    ILoginView loginActivity;
    Activity logoutActivity;
    SharedPreferences sp;
    LoginInteractor loginInteractor;
    DomainInteractor domainInteractor;
    Base64Interactor base64Interactor;
    ExamOverviewInteractor examOverviewInteractor;

    public LoginPresenter(LoginActivity loginActivity, SharedPreferences sp) {
        this.loginActivity = loginActivity;
        this.sp = sp;
        loginInteractor = new LoginInteractor();
        domainInteractor = new DomainInteractor(new ArrayList<AccessDomain>());
        base64Interactor = new Base64Interactor();
        examOverviewInteractor = new ExamOverviewInteractor();
    }

    public LoginPresenter(Activity logoutActivity, SharedPreferences sp) {
        this.logoutActivity = logoutActivity;
        loginInteractor = new LoginInteractor();
        this.sp = sp;
    }

    @Override
    public void authorizeLogin(String username, String password) {
        Log.d(this.getClass().getSimpleName(), "Presenter received loginRequest -- Forwarding to Encoder");
        Log.d(this.getClass().getSimpleName(), "Presenter received encoded login -- Forwarding to Validation");
        loginInteractor.handleRequestLoginAttempt(this, base64Interactor.encodeStringToBase64(username), base64Interactor.encodeStringToBase64(password));
    }

    @Override
    public void retrieveDomains() {
        domainInteractor.getDomains(this);

    }

    @Override
    public void requestSaveInfo(boolean patient) {
        loginInteractor.saveUserInfo(this, sp.edit(), patient);
    }

    @Override
    public boolean validateToken(String expires) {
        //dd/MM/yyyy(HH:mm:ss)
        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            Log.d(this.getClass().getSimpleName(), "expires: " + format.parse(expires));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(this.getClass().getSimpleName(), "date: " + new Date().toString());
        if (expires.isEmpty()) {
            Log.d(this.getClass().getSimpleName(), "No expiredate found, initializing login activity");
            return false;
        }
        try {
            if (format.parse(expires).before(new Date())) {
                Log.d(this.getClass().getSimpleName(), "Expire date exceeded, initializing login activity");
                return false;
            }
        } catch (ParseException e) {
            //TODO implement exception
        }
        try {
            if (format.parse(expires).after(new Date())) {
                Log.d(this.getClass().getSimpleName(), "Token not expired, continuing");
                return true;
            }
        } catch (ParseException e) {
            //TODO implement exception
        }
        Log.d(this.getClass().getSimpleName(), "HOW DID THE METHOD GET HERE UURGH?");
        return false;
    }

    @Override
    public void logout() {
        loginInteractor.deleteUserInfo(this, sp.edit());
    }


    @Override
    public void onHandleRequestLoginAttemptSuccess(OnLoginFinishedListener listener, AuthorisationResult authorisationResult, String username64) {
        loginInteractor.handleAuthorisationResult(listener, authorisationResult, username64);
    }


    @Override
    public void onHandleRequestLoginAttemptFailure() {
        loginActivity.requestFailed();

    }

    @Override
    public void onAuthorizeFailure(String code) {

        loginActivity.loginFailed(code);

    }

    @Override
    public void onSavedInfo() {
        //Do nothing
    }

    @Override
    public void onDeletedInfo() {
        Intent intent = new Intent(logoutActivity, LoginActivity.class);
        logoutActivity.startActivity(intent);
    }

    @Override
    public void onAuthorizeSuccess(ArrayList<String> roles, MobileToken token) {
        if (roles.isEmpty()) {
            Log.d(this.getClass().getSimpleName(),"No roles identified for User! Failing login");
            loginActivity.loginFailed();
        }
        else if (roles.size() > 1) {
            Log.d(this.getClass().getSimpleName(), "Multiple roles identified for User! Forwarding");
            loginActivity.showRoleOptionDialog(roles);
        }
        else {
            if (roles.get(0).equals(UserRoleEnum.ROLE_HEALTH_PROFESSIONAL.toString())) {
                Log.d(this.getClass().getSimpleName(), "Health Professional Identified! Forwarding to view!");
                loginActivity.loginSuccess(false);
            }
            else if (roles.get(0).equals(UserRoleEnum.ROLE_PATIENT.toString())) {
                Log.d(this.getClass().getSimpleName(), "Health Professional Identified! Forwarding to view!");
                loginActivity.loginSuccess(true);
            }
        }
    }

    @Override
    public void onDomainRetrieved(ArrayList<AccessDomain> domainList) {
        loginActivity.domainRetrievedSuccesfully(domainList);
    }

    @Override
    public void onDomainFailed() {
        loginActivity.domainRetrievedFailed();
    }

    @Override
    public void retrieveAnonymousExam(String accessCodeString, String examIdString) {
        examOverviewInteractor.getAnonymousExam(this,accessCodeString,examIdString);
    }

    @Override
    public void examRetrievedSucces(Exam exam) {
        loginActivity.showAnonymousExam(exam);
    }

    @Override
    public void examRetrievedFailure() {
        loginActivity.showAnonymousExamError();
    }
}
