package br.com.managersystems.guardasaude.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.NewExamListener;


public class NewExamDialogFragment extends DialogFragment {
    private NewExamListener listener;

    public NewExamDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        final View view = inflater.inflate(R.layout.dialog_newxexam, null);

        builder.setView(view)
                .setPositiveButton(R.string.find, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        findNewExam(view);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NewExamDialogFragment.this.getDialog().cancel();
                    }
                }).setCustomTitle(getActivity().getLayoutInflater().inflate(R.layout.dialog_title_newexam,null));

        listener = (NewExamListener)getTargetFragment();

        return builder.create();
    }

    private void findNewExam(View view) {
        EditText username = (EditText)view.findViewById(R.id.new_exam_username);
        EditText protocol = (EditText)view.findViewById(R.id.new_exam_protocol);
        listener.onNewExamInformationRetrieved(username.getText().toString(),protocol.getText().toString());
    }
}
