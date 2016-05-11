package br.com.managersystems.guardasaude.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Image;
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
import butterknife.Bind;
import butterknife.ButterKnife;

public class SortByDialogFragment extends DialogFragment {
    @Bind(R.id.radio_icon_executionDate)
    ImageView iconDate;

    @Bind(R.id.radio_icon_identification)
    ImageView iconIdentification;

    @Bind(R.id.radio_icon_patient)
    ImageView iconPatient;

    @Bind(R.id.patient_button)
    View radioPatient;

    @Bind(R.id.identification_button)
    View radioIdentification;

    @Bind(R.id.date_button)
    View radioDate;

    private SortDialogListener listener;
    private String sortBy = null;
    private String orderBy = null;
    private int iconPatientCounter = 0;
    private int iconDateCounter = 0;
    private int iconIdentificationCounter = 0;


    public SortByDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_sort_exams, null);

        ButterKnife.bind(this, view);

        builder.setView(view).setPositiveButton(R.string.sort, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetCounters();
                sortAndOrderList();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetCounters();
                SortByDialogFragment.this.getDialog().cancel();
            }
        });


        listener = (SortDialogListener) getTargetFragment();

        init();

        return builder.create();
    }

    private void resetCounters() {
        iconDateCounter=0;
        iconIdentificationCounter=0;
        iconPatientCounter=0;
    }

    private void init() {
        setIconImages();
        setRadioOnClickListeners();
    }

    private void setIconImages() {
        iconPatient.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_az));
        iconDate.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_09));
        iconIdentification.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_az));
    }

    public void setRadioOnClickListeners() {
        radioPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        radioDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        radioIdentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.patient_button:
                sortBy = "patient";

                iconPatient.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_az_accent));
                iconDate.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_09));
                iconIdentification.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_az));

                if ((iconPatientCounter & 1) == 0) {
                    iconPatient.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_az_accent));
                    orderBy = "asc";
                } else {
                    iconPatient.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_za_accent));
                    orderBy = "desc";
                }

                iconDateCounter=0;
                iconIdentificationCounter=0;
                iconPatientCounter++;
                break;
            case R.id.identification_button:
                sortBy = "identification";

                iconIdentification.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_az_accent));
                iconDate.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_09));
                iconPatient.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_az));

                if ((iconIdentificationCounter & 1) == 0) {
                    iconIdentification.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_az_accent));
                    orderBy = "asc";
                } else {
                    iconIdentification.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_za_accent));
                    orderBy = "desc";
                }

                iconDateCounter=0;
                iconPatientCounter=0;
                iconIdentificationCounter++;
                break;
            case R.id.date_button:
                sortBy = "executionDate";

                iconDate.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_09_accent));
                iconPatient.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_az));
                iconIdentification.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_az));

                if ((iconDateCounter & 1) == 0) {
                    iconDate.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_09_accent));
                    orderBy = "asc";
                } else {
                    iconDate.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort_90_accent));
                    orderBy = "desc";
                }

                iconPatientCounter=0;
                iconIdentificationCounter=0;
                iconDateCounter++;
                break;
        }
    }


    private void sortAndOrderList() {
        listener.sortExamListBy(orderBy, sortBy);
    }

}
