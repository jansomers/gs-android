package br.com.managersystems.guardasaude.ui.dialogs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.SortDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SortByDialogFragment extends DialogFragment {
    @Bind(R.id.radio_icon_executionDate)
    ImageView iconDate;

    @Bind(R.id.radio_icon_identification)
    ImageView iconIdentification;

    @Bind(R.id.radio_icon_patient)
    ImageView iconPatient;

    @Bind(R.id.patient_button_layout)
    View radioPatientLayout;

    @Bind(R.id.identification_button_layout)
    View radioIdentificationLayout;

    @Bind(R.id.date_button_layout)
    View radioDateLayout;

    @Bind(R.id.radio_patient)
    TextView radioPatient;

    @Bind(R.id.radio_executionDate)
    TextView radioDate;

    @Bind(R.id.radio_identification)
    TextView radioIdentification;

    @Bind(R.id.emergency_button_layout)
    View radioEmergencyLayout;

    @Bind(R.id.radio_emergency)
    TextView radioEmergency;

    private SortDialogListener listener;
    private String sortBy = null;
    private String orderBy = null;
    private int iconPatientCounter = 0;
    private int iconDateCounter = 0;
    private int iconIdentificationCounter = 0;
    private String isEmergency="false";

    public SortByDialogFragment() {
    }

    @NonNull
    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.dialog_sort_exams, null);
        ButterKnife.bind(this, view);
        builder.setView(view);

        listener = (SortDialogListener) getTargetFragment();
        init();
        return builder.create();
    }

    @OnClick(R.id.cancel_sort_btn)
    public void cancelExamClicked() {
        resetCounters();
        getDialog().cancel();
    }

    @OnClick(R.id.sort_exam_oke_btn)
    public void confirmSortClicked(){
        resetCounters();
        sortAndOrderList();
        getDialog().dismiss();

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
        radioPatientLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        radioDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        radioIdentificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        radioEmergencyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        resetTextViewColors();
        switch (view.getId()) {
            case R.id.patient_button_layout:
                sortBy = "patient";
                radioPatient.setTextColor(ContextCompat.getColor(this.getActivity().getApplicationContext(), R.color.colorAccent300));

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
            case R.id.identification_button_layout:
                sortBy = "identification";
                radioIdentification.setTextColor(ContextCompat.getColor(this.getActivity().getApplicationContext(), R.color.colorAccent300));

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
            case R.id.date_button_layout:
                sortBy = "executionDate";
                radioDate.setTextColor(ContextCompat.getColor(this.getActivity().getApplicationContext(), R.color.colorAccent300));
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
            case R.id.emergency_button_layout:
                sortBy=null;
                orderBy=null;
                isEmergency="true";
                radioEmergency.setTextColor(ContextCompat.getColor(this.getActivity().getApplicationContext(), R.color.colorAccent300));
        }
    }

    private void resetTextViewColors() {
        radioPatient.setTextColor(ContextCompat.getColor(this.getActivity().getApplicationContext(), R.color.colorTextColorLight));
        radioDate.setTextColor(ContextCompat.getColor(this.getActivity().getApplicationContext(), R.color.colorTextColorLight));
        radioIdentification.setTextColor(ContextCompat.getColor(this.getActivity().getApplicationContext(), R.color.colorTextColorLight));
        isEmergency="false";
    }


    private void sortAndOrderList() {
        listener.sortExamListBy(orderBy, sortBy,isEmergency);
    }

}
