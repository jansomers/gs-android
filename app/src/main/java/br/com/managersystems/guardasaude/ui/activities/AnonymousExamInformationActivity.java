package br.com.managersystems.guardasaude.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.managersystems.guardasaude.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnonymousExamInformationActivity extends AppCompatActivity{
    @Bind(R.id.gs_exam_information_exam_id)
    TextView examIdTextView;

    @Bind(R.id.gs_exam_information_exam_type)
    TextView examTypeTextView;

    @Bind(R.id.gs_exam_information_status)
    ImageView examStatusImageView;

    @Bind(R.id.gs_exam_information_clinic_id)
    TextView examClinicTextView;

    @Bind(R.id.gs_exam_information_patient)
    TextView examPatientTextView;

    @Bind(R.id.gs_exam_information_exam_date)
    TextView examDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_information);
        ButterKnife.bind(this);

        Intent i = getIntent();
    }

    @OnClick(R.id.create_account_btn)
    void onCreateAccountClicked(){
        //TODO go to create account activity
    }
}
