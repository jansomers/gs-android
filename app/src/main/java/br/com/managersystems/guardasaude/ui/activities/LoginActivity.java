package br.com.managersystems.guardasaude.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.BuildConfig;
import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.login.ILoginView;
import br.com.managersystems.guardasaude.login.LoginPresenter;
import br.com.managersystems.guardasaude.login.domain.AccessDomain;
import br.com.managersystems.guardasaude.ui.dialogs.ForgotPasswordDialog;
import br.com.managersystems.guardasaude.ui.dialogs.NewAnonymousExamDialog;
import br.com.managersystems.guardasaude.util.StringUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    @Bind(R.id.gs_login_username)
    EditText gsUsernameEditText;

    @Bind(R.id.gs_login_password)
    EditText gsPasswordEditText;

    @Bind(R.id.gs_login_progressbar)
    ProgressBar authenticatingProgressBar;

    @Bind(R.id.gs_login_progress_text)
    TextView authenticatingProgressText;

    @Bind(R.id.gs_login_succes_imageview)
    ImageView authenticatingFinishedImageView;

    @Bind(R.id.gs_login_logo)
    ImageView gsLogo;

    @Bind(R.id.gs_login_btn_forgot_password)
    Button forgotPwdBtn;

    @Bind(R.id.gs_login_btn)
    Button loginBtn;

    @Bind(R.id.gs_login_coordinator_layout)
    CoordinatorLayout loginCoordinatorLayout;


    private LoginPresenter presenter;

    SharedPreferences sp;


    private Snackbar snackSuccessfulPwdReq;
    private Snackbar snackFailedPwdReq;
    private Snackbar snackInternalFailNewExam;

    /* ********************************************
    TODO implement domain chooser
    TODO implement profile switcher

    ******************************************** */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        presenter = new LoginPresenter(this, sp);

    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void init() {
        activateLogo();
        initSnacks();
        if (BuildConfig.DEBUG) {
            setStartingCredentials("doctor2", "Admin1");
        }
        instantiateProgressBar();
    }

    /**
     * Initiates snackbars.
     */
    private void initSnacks() {
        snackSuccessfulPwdReq = Snackbar.make(loginCoordinatorLayout, getResources().getText(R.string.snackReqPwd), Snackbar.LENGTH_LONG);
        snackSuccessfulPwdReq.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        snackFailedPwdReq = Snackbar.make(loginCoordinatorLayout, getResources().getText(R.string.snackNoReqPwd), Snackbar.LENGTH_LONG);
        snackFailedPwdReq.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorError));
        snackInternalFailNewExam = Snackbar.make(loginCoordinatorLayout,getResources().getText(R.string.exam_associated_internalfail),Snackbar.LENGTH_LONG);
        snackInternalFailNewExam.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorError));
    }

    /**
     * Fades in the GuardaSaude logo.
     */
    private void activateLogo() {
        Animation logoAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slow_fade_in_animation);
        gsLogo.setVisibility(View.VISIBLE);
        gsLogo.startAnimation(logoAnimation);
    }

    /**
     * Sets the credentials in both textInputs.
     *
     * @param username String that sets the username.
     * @param password String that sets the password.
     */
    private void setStartingCredentials(String username, String password) {
        gsUsernameEditText.setText(username);
        gsPasswordEditText.setText(password);
    }

    /**
     * Hides the elements of the progress bar.
     */
    private void instantiateProgressBar() {
        authenticatingProgressBar.setVisibility(View.GONE);
        authenticatingProgressText.setVisibility(View.GONE);
        authenticatingFinishedImageView.setVisibility(View.GONE);
    }


    /**
     * Handles the authorization when the login button is clicked.
     * @param view Button object representing the login button.
     */
    @OnClick(R.id.gs_login_btn)
    public void loginClicked(View view) {
        Log.d("LoginActivity: ", "Login button was clicked");
        loginBtn.setEnabled(false);
        String email = gsUsernameEditText.getText().toString();
        String password = gsPasswordEditText.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            showEmptyCredentials();
        } else {
            showAuthenticatingProgress();
            presenter.authorizeLogin(email, password);
        }
    }


    @Override
    public void navigateToOverviewActivity() {
        Intent intent = new Intent(this, MainTabActivity.class);
        Log.d(this.getClass().getSimpleName(), "Navigating to MainTabActivity as: " + sp.getString("role", "NO_ROLE"));
        startActivity(intent);
    }

    @Override
    public void showDomainOptionDialog(ArrayList<AccessDomain> accessDomainArrayList) {
        String domains[] = new String[accessDomainArrayList.size()];
        int placeCounter = 0;
        for (AccessDomain domain : accessDomainArrayList) {
            domains[placeCounter] = domain.getTag();
            placeCounter++;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle(getResources().getText(R.string.choose_domain));
        builder.setCustomTitle(getLayoutInflater().inflate(R.layout.dialog_domain_title, null));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dialog_role_custom_checked_textview, domains);

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_dialog_window_white);

        dialog.show();

    }

    /**
     * Shows the dialog to retrieve your password
     */
    @OnClick(R.id.gs_login_btn_forgot_password)
    public void forgotPwdBtnClicked() {
        String username = gsUsernameEditText.getText().toString();
        final ForgotPasswordDialog dialog = new ForgotPasswordDialog(this, snackSuccessfulPwdReq, snackFailedPwdReq);
        dialog.setPwdText(username);
        dialog.activateRequestBtn();
        dialog.show();
    }

    @Override
    public void loginSuccess(boolean patient) {
        hideProgressBar();
        showSuccessfulLogin();
        presenter.requestSaveInfo(patient);
        loginBtn.setEnabled(true);
        navigateToOverviewActivity();

    }



    @Override
    public void requestFailed() {
        Log.d(this.getClass().getSimpleName(), "Login request Failed! Communication went wrong");
        hideProgressBar();
        showFailedLogin(getResources().getString(R.string.server_error));
    }


    @Override
    public void loginFailed(String code) {
        Log.d(this.getClass().getSimpleName(), "Login failed! Reason: " + code);
        hideProgressBar();
        if (code.equals("auth_failed"))
            showFailedLogin(getResources().getString(R.string.auth_failed));
        else showFailedLogin(code);
    }


    @Override
    public void loginFailed() {
        Log.d(this.getClass().getSimpleName(), "Login Failed!");
        hideProgressBar();
        showFailedLogin(getResources().getString(R.string.login_failed));

    }

    @Override
    public void domainRetrievedFailed() {

    }


    public void showRoleOptionDialog(ArrayList<String> roles) {
        hideProgressBar();
        showSuccessfulLogin();
        AlertDialog.Builder roleOptionBuilder = new AlertDialog.Builder(this);
        roleOptionBuilder.setCustomTitle(getLayoutInflater().inflate(R.layout.dialog_role_title, null));
        String[] rolesArray = roles.toArray(new String[roles.size()]);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dialog_role_custom_checked_textview, StringUtils.rolesToDisplayRoles(rolesArray));
        roleOptionBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (adapter.getItem(which).equalsIgnoreCase("patient"))
                    loginSuccess(true);
                else loginSuccess(false);
            }
        });
        AlertDialog roleOptionDialog = roleOptionBuilder.create();
        roleOptionDialog.getWindow().setBackgroundDrawableResource(R.drawable.background_dialog_window_white);
        roleOptionDialog.show();
    }

    /**
     * Handles a login attempt with one or more credentials missing.
     */
    private void showEmptyCredentials() {
        hideProgressBar();
        showFailedLogin(getResources().getString(R.string.empty_creds));
    }

    /**
     * Shows a failed login attempt alert.
     *
     * @param string String that represents the error message.
     */
    private void showFailedLogin(String string) {
        authenticatingFinishedImageView.setImageResource(R.drawable.ic_error_36dp_error);
        authenticatingFinishedImageView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        authenticatingFinishedImageView.startAnimation(animation);
        authenticatingProgressText.setTextColor(ContextCompat.getColor(this, R.color.colorError));
        authenticatingProgressText.setText(string);
        loginBtn.setEnabled(true);
    }

    /**
     * Shows the server options.
     */
    public void showServerOptionDialog() {
        presenter.retrieveDomains();

    }

    @Override
    public void showAnonymousExam(Exam exam) {
        Intent intent = new Intent(this, AnonymousExamInformationActivity.class);
        intent.putExtra("exam", exam);
        startActivity(intent);
    }

    @Override
    public void showAnonymousExamError() {
        snackInternalFailNewExam.show();
    }

    /**
     * Shows the authentication progress and hides any results.
     */
    private void showAuthenticatingProgress() {
        authenticatingProgressBar.setVisibility(View.VISIBLE);
        authenticatingProgressText.setText(getResources().getText(R.string.authenticating_message));
        authenticatingProgressText.setTextColor(ContextCompat.getColor(this, R.color.colorAccent300));
        authenticatingProgressText.setVisibility(View.VISIBLE);
        authenticatingFinishedImageView.setVisibility(View.GONE);

    }

    /**
     * Hides the authentication progress
     */
    private void hideProgressBar() {
        authenticatingProgressBar.setVisibility(View.GONE);
    }

    /**
     * Shows a successful login on the screen.
     */
    private void showSuccessfulLogin() {
        authenticatingFinishedImageView.setImageResource(R.drawable.ic_check_circle_36dp_accent);
        authenticatingProgressText.setText(getResources().getText(R.string.login_success));
        authenticatingFinishedImageView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        authenticatingFinishedImageView.startAnimation(animation);
        authenticatingProgressText.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

    }

    @OnClick(R.id.gs_login_btn_anonymous_exam)
    public void showNewAnonymousExamDialog(){
        final NewAnonymousExamDialog dialog = new NewAnonymousExamDialog(this);
        dialog.activateRequestBtn();
        dialog.show();

    }

    @Override
    public void findAnonymousExam(String accessCodeString, String examIdString) {
        presenter.retrieveAnonymousExam(accessCodeString, examIdString);
    }


    public LoginPresenter getPresenter() {
        return presenter;
    }
    @Override
    public void onBackPressed() {
        //Don't press back
    }
}
