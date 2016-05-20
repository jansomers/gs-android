package br.com.managersystems.guardasaude.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.exammenu.information.anonymousexam.AnonymousExamInformationPresenter;
import br.com.managersystems.guardasaude.exams.exammenu.information.anonymousexam.IAnonymousExamInformationPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnonymousExamInformationActivity extends AppCompatActivity {
    @Bind(R.id.gs_anon_exam_information_exam_id)
    TextView examIdTextView;

    @Bind(R.id.gs_anon_exam_information_exam_type)
    TextView examTypeTextView;

    @Bind(R.id.gs_anon_exam_information_status)
    ImageView examStatusImageView;

    @Bind(R.id.gs_anon_exam_information_clinic_id)
    TextView examClinicTextView;

    @Bind(R.id.gs_anon_exam_information_patient)
    TextView examPatientTextView;

    @Bind(R.id.gs_anon_exam_information_exam_date)
    TextView examDateTextView;

    @Bind(R.id.gs_anon_exam_information_exam_reporting_phys)
    TextView examPhysicianTextView;

    IAnonymousExamInformationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_information);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        presenter = new AnonymousExamInformationPresenter(this);
        presenter.retrieveExam(getIntent());
    }

    @OnClick(R.id.create_account_btn)
    void onCreateAccountClicked() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void setExamInformation(Exam exam) {
        examClinicTextView.setText(exam.getClinicName());
        examDateTextView.setText(exam.getExecutionDate());
        examIdTextView.setText(exam.getIdentification());
        examPatientTextView.setText(exam.getPatient());
        examTypeTextView.setText(exam.getServiceName());
        examPhysicianTextView.setText(exam.getReportingPhysicianName());
        if (exam.getStatus().equalsIgnoreCase(getString(R.string.finished)) || exam.getStatus().equalsIgnoreCase(getString(R.string.ready)) || exam.getStatus().equalsIgnoreCase(getString(R.string.available))) {
            examStatusImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_check_circle_36dp_accent));
        } else {
            examStatusImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_clock_primary));
        }
    }

    public void examNotFound() {
        Log.d("ExamNotFound", "Internal failure, exam not found");
    }
}
