package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.ui.fragments.ExamOverviewFragment;

public class NewExamDialogListener implements View.OnClickListener {
    private ExamOverviewFragment fragment;
    private final Dialog dialog;

    public NewExamDialogListener(ExamOverviewFragment examOverviewFragment, Dialog dialog) {
        this.dialog = dialog;
        fragment = examOverviewFragment;
    }

    @Override
    public void onClick(View view) {
        TextView errorText = (TextView)dialog.findViewById(R.id.new_exam_error_msg);
        EditText identification = (EditText)dialog.findViewById(R.id.new_exam_identification);
        EditText accessCode = (EditText)dialog.findViewById(R.id.new_exam_access_code);

        if(identification.getText().toString().trim().equals("") && accessCode.getText().toString().trim().equals("")){
            errorText.setText(fragment.getResources().getString(R.string.id_and_ac_required));
            errorText.setVisibility(View.VISIBLE);
        }
        else if(identification.getText().toString().trim().equals("")){
            errorText.setText(fragment.getResources().getString(R.string.identification_required));
            errorText.setVisibility(View.VISIBLE);
        }else if(accessCode.getText().toString().trim().equals("")){
            errorText.setText(fragment.getResources().getString(R.string.accesscode_required));
            errorText.setVisibility(View.VISIBLE);
        } else{
            fragment.onNewExamInformationRetrieved(identification.getText().toString(), accessCode.getText().toString());
            dialog.cancel();
        }
    }
}
