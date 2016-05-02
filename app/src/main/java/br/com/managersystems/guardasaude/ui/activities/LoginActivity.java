package br.com.managersystems.guardasaude.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.login.AccessDomain;
import br.com.managersystems.guardasaude.login.ILoginView;
import br.com.managersystems.guardasaude.login.LoginPresenter;
import br.com.managersystems.guardasaude.login.domain.UserRoleEnum;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView {

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


    private LoginPresenter presenter;
    SharedPreferences sp;
    private final boolean VALIDCREDENTIALS = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        //TODO Redirect to login if no accesstoken
        presenter = new LoginPresenter(this,sp);
        init();


    }

    private void init() {
        activateLogo();
        //TODO Remove after testing
        setStartingCredentials("doctor2", "Admin1");
        instantiateProgressBar();
    }
    private void activateLogo() {
        Animation logoAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slow_fade_in_animation);
        gsLogo.setVisibility(View.VISIBLE);
        gsLogo.startAnimation(logoAnimation);
    }
    private void setStartingCredentials(String doctor, String admin) {
        gsUsernameEditText.setText(doctor);
        gsPasswordEditText.setText(admin);
    }
    private void instantiateProgressBar() {
        authenticatingProgressBar.setVisibility(View.GONE);
        authenticatingProgressText.setVisibility(View.GONE);
        authenticatingFinishedImageView.setVisibility(View.GONE);
    }


    @OnClick(R.id.gs_login_btn)
    public void loginClicked(View view) {
        Log.d("LoginActivity: ", "Login button was clicked");
        String email = gsUsernameEditText.getText().toString();
        String password = gsPasswordEditText.getText().toString();
        if (email.equals("") || password.equals("")) {
            showEmptyCredentials();
        }
        else {
            showAuthenticatingProgress();

            presenter.authorizeLogin(email, password);
        }
    }



    @Override
    public void navigateToOverviewActivity(boolean patient) {
        Intent intent = new Intent(this, MainTabActivity.class);
        intent.putExtra("role", patient ? UserRoleEnum.ROLE_PATIENT.toString() : UserRoleEnum.ROLE_HEALTH_PROFESSIONAL.toString());
        Log.d(this.getClass().getSimpleName(), "Navigating to Maintabactivity as: " + intent.getStringExtra("role"));
        startActivity(intent);

    }


    @Override
    public void domainRetrievedSuccesfully(ArrayList<AccessDomain> accessDomainArrayList) {
        String domains[] = new String[accessDomainArrayList.size()];
        int placeCounter = 0;
        for (AccessDomain domain : accessDomainArrayList) {
            domains[placeCounter] = domain.getTagName();
            placeCounter++;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle(getResources().getText(R.string.choose_domain));
        builder.setCustomTitle(getLayoutInflater().inflate(R.layout.domain_dialog_title, null));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dialog_standard_item,domains);

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO ADD event
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.white_dialog_window);

        dialog.show();

    }

    @Override
    public void loginSuccess(boolean patient) {
        hideProgressBar();
        presenter.saveInfo(patient);
        showSuccessfulLogin();
        navigateToOverviewActivity(patient);

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
        showFailedLogin(code);
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
        roleOptionBuilder.setCustomTitle(getLayoutInflater().inflate(R.layout.role_dialog_title, null));
        String[] rolesArray = roles.toArray(new String[roles.size()]);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dialog_standard_item, rolesArray);
        roleOptionBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (adapter.getItem(which).equalsIgnoreCase(UserRoleEnum.ROLE_PATIENT.toString()))
                    loginSuccess(true);
                else loginSuccess(false);
            }
        });
       AlertDialog roleOptionDialog = roleOptionBuilder.create();
        roleOptionDialog.getWindow().setBackgroundDrawableResource(R.drawable.white_dialog_window);
        roleOptionDialog.show();
    }

    private void showEmptyCredentials() {
        hideProgressBar();
        showFailedLogin(getResources().getString(R.string.empty_creds));
    }

    private void showFailedLogin(String string) {
        authenticatingFinishedImageView.setImageResource(R.drawable.ic_error_36dp);
        authenticatingFinishedImageView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        authenticatingFinishedImageView.startAnimation(animation);
        authenticatingProgressText.setTextColor(ContextCompat.getColor(this, R.color.colorError));
        authenticatingProgressText.setText(string);
    }

    public void showServerOptionDialog() {
        presenter.retrieveDomains();

    }



    private void showAuthenticatingProgress() {
        authenticatingProgressBar.setVisibility(View.VISIBLE);
        authenticatingProgressText.setText(getResources().getText(R.string.Authenticating));
        authenticatingProgressText.setTextColor(ContextCompat.getColor(this, R.color.colorAccent300));
        authenticatingProgressText.setVisibility(View.VISIBLE);
        authenticatingFinishedImageView.setVisibility(View.GONE);

    }
    private void hideProgressBar() {
        authenticatingProgressBar.setVisibility(View.GONE);
    }

    private void showSuccessfulLogin() {
        authenticatingFinishedImageView.setImageResource(R.drawable.ic_check_circle_36dp);
        authenticatingProgressText.setText(getResources().getText(R.string.login_succes));

        authenticatingFinishedImageView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        authenticatingFinishedImageView.startAnimation(animation);
        authenticatingProgressText.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

    }
}
