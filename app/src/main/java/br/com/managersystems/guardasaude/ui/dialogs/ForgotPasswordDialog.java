package br.com.managersystems.guardasaude.ui.dialogs;

import android.app.Dialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.ui.activities.LoginActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ForgotPasswordDialog extends Dialog {

    @Bind(R.id.forgot_pwd_email)
    TextInputEditText forgotPwdEmail;

    @Bind(R.id.btn_request_pwd)
    Button requestPwdBtn;

    Snackbar succes;
    Snackbar fail;


    public ForgotPasswordDialog(LoginActivity loginActivity, Snackbar succesSnack, Snackbar failSnack) {
        super(loginActivity);
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.dialog_forgot_pwd);
        this.succes = succesSnack;
        this.fail = failSnack;
        ButterKnife.bind(this);
    }

    @Override
    public void show() {
        super.show();

    }

    public void setPwdText(String email) {
        if (!email.isEmpty()) {
            forgotPwdEmail.setText(email);
        }
    }

    public void activateRequestBtn() {
        requestPwdBtn.setOnClickListener(new View.OnClickListener() {
            // TODO check email validity. If valid and request fails show snackbar, If invalid and no request happened stay in dialog.
            @Override
            public void onClick(View v) {
                //Implement StringUtils.isValidEmail(forgotPwdEmail.getText()
                if (!forgotPwdEmail.getEditableText().toString().isEmpty()) {
                    succes.show();
                    dismiss();

                } else {
                    fail.show();
                    dismiss();

                }
            }
        });
    }
}
