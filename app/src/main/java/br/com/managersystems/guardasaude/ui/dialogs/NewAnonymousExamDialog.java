package br.com.managersystems.guardasaude.ui.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.ui.activities.LoginActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAnonymousExamDialog extends Dialog {

    @Bind(R.id.add_anonymous_accesscode)
    TextInputEditText accessCode;

    @Bind(R.id.add_anonymous_examId)
    TextInputEditText examId;

    @Bind(R.id.btn_anonymous_exam)
    Button findNexExamButton;

    @Bind(R.id.new_anon_exam_error_msg)
    TextView errorText;

    private LoginActivity loginActivity;

    public NewAnonymousExamDialog(LoginActivity loginActivity) {
        super(loginActivity);
        super.setContentView(R.layout.dialog_add_anonymous_exam);
        this.loginActivity = loginActivity;
        ButterKnife.bind(this);
    }

    @Override
    public void show() {
        super.show();
    }


    public void activateRequestBtn(){
        findNexExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accessCodeString = accessCode.getEditableText().toString();
                String examIdString = examId.getEditableText().toString();

                if (accessCodeString.trim().equals("") && examIdString.equals("")) {
                    errorText.setText(loginActivity.getResources().getString(R.string.id_and_ac_required));
                    errorText.setVisibility(View.VISIBLE);
                } else if (examIdString.trim().equals("")) {
                    errorText.setText(loginActivity.getResources().getString(R.string.identification_required));
                    errorText.setVisibility(View.VISIBLE);
                } else if (accessCodeString.trim().equals("")) {
                    errorText.setText(loginActivity.getResources().getString(R.string.accesscode_required));
                    errorText.setVisibility(View.VISIBLE);
                } else {
                    loginActivity.findAnonymousExam(accessCodeString, examIdString);
                }
            }
        });
    }

}
