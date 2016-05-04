package br.com.managersystems.guardasaude.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.SortDialogListener;

public class SortByDialogFragment extends DialogFragment {
    private SortDialogListener listener;
    private String sortBy=null;
    private String orderBy=null;

    public SortByDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_sort_exams, null);

        builder.setView(view).setPositiveButton(R.string.sort, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sortAndOrderList();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SortByDialogFragment.this.getDialog().cancel();
            }
        });


        listener = (SortDialogListener) getTargetFragment();

        findRadioButtons(view);
        findSortButtons(view);

        return builder.create();
    }

    private void sortAndOrderList() {
        listener.sortExamListBy(orderBy, sortBy);
    }

    private void findSortButtons(final View view) {
        View asc = view.findViewById(R.id.sortAsc);
        asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSortClicked(v,view);
            }
        });

        View desc = view.findViewById(R.id.sortDesc);
        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSortClicked(v,view);
            }
        });
    }

    private void onSortClicked(View v,View view) {
        TextView textasc = (TextView)view.findViewById(R.id.asc);
        TextView textdesc = (TextView)view.findViewById(R.id.desc);
        ImageView ascImg = (ImageView)view.findViewById(R.id.ascImg);
        ImageView descImg = (ImageView)view.findViewById(R.id.descImg);

        switch (v.getId()) {
            case R.id.sortAsc:
                this.sortBy="asc";
                textasc.setTextColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
                textdesc.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextColor));
                ascImg.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
                descImg.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorTextColor));

                break;
            case R.id.sortDesc:
                this.sortBy="desc";
                textdesc.setTextColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
                textasc.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextColor));
                descImg.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
                ascImg.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorTextColor));
                break;
        }
    }

    public void findRadioButtons(View view) {
        View patient = view.findViewById(R.id.radio_patient);
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        View executionDate = view.findViewById(R.id.radio_executionDate);
        executionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        View report = view.findViewById(R.id.radio_identification);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radio_patient:
                if (checked)
                    this.orderBy="patient";
                break;
            case R.id.radio_identification:
                if (checked)
                    this.orderBy="identification";
                break;
            case R.id.radio_executionDate:
                if (checked)
                    this.orderBy="executionDate";
                break;
        }
    }

}
