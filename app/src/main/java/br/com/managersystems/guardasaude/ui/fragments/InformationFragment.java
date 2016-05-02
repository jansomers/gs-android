package br.com.managersystems.guardasaude.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.exammenu.information.ExamPresenter;
import br.com.managersystems.guardasaude.exams.exammenu.information.IExamInformationView;
import br.com.managersystems.guardasaude.util.AnimationUtils;
import br.com.managersystems.guardasaude.util.StringUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InformationFragment extends Fragment implements IExamInformationView {

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

    @Bind(R.id.gs_exam_information_exam_reporting_phys)
    TextView examRepPhysTextView;

    @Bind(R.id.hideable_information_layout)
    RelativeLayout hideableLayout;

    @Bind(R.id.comments_btn)
    Button commentsBtn;

    @Bind(R.id.images_btn)
    Button imagesBtn;


    ExamPresenter presenter;
    boolean commentsHidden;
    //final Exam DUMMY_EXAM = new Exam(167511113, "TMC56257", "RM ARTICULAR(PORATICULACAO)", "ATIDOR SILVA CORDOSO DOS SANTOS", "12/01/2016 12:10", "Finished", "JOHN SMITH", "CRMPR/98765", "JOSE CANDIDO VALENTE MALAGUIDO", "CRM SC/17989", "/mobile/getExamReport?user=doctor&exid=TMC56257", null);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, view);
        presenter = new ExamPresenter(this);
        init();
        return view;
    }

    private void init() {
        Log.d(getClass().getSimpleName(), "Initializing Information Fragment...");
        Log.d(getClass().getSimpleName(), "Organizing startup views...");
        if (hideableLayout.getVisibility() == View.VISIBLE) commentsHidden = true;
        else {
            hideComments();
            init();
        }
        Log.d(getClass().getSimpleName(), "Notifying the presenter to retrieve the exam information...");
        presenter.retrieveInformation(getActivity().getIntent());
    }

    @Override
    public void showInformation(Exam exam) {
        Log.d(getClass().getSimpleName(), "Received success from Presenter... Showing Information");
        hideableLayout.setVisibility(View.VISIBLE);
        commentsBtn.setVisibility(View.VISIBLE);
        imagesBtn.setVisibility(View.VISIBLE);
        examIdTextView.setText(exam.getIdentification());
        examTypeTextView.setText(exam.getServiceName());
        examStatusImageView.setImageDrawable(ContextCompat.getDrawable(this.getActivity(),exam.getStatus().equals("Finished") ? R.drawable.ic_check_circle_36dp : R.drawable.ic_clock));
        examPatientTextView.setText(StringUtils.anyCaseToNameCase(exam.getPatient()));
        examClinicTextView.setText(StringUtils.anyCaseToNameCase(exam.getClinicName()));
        examDateTextView.setText(exam.getExecutionDate().split(" ")[0]);
        examRepPhysTextView.setText(StringUtils.anyCaseToNameCase(exam.getReportingPhysicianName()));

    }

    @Override
    public void showComments() {
        // set date reporting gone
        AnimationUtils.collapse(hideableLayout);
        commentsBtn.setText(getText(R.string.information));
        commentsBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more_24dp, 0);
        commentsHidden = false;
    }

    @Override
    public void hideComments() {
        AnimationUtils.expand(hideableLayout);
        commentsBtn.setText(getText(R.string.comments));
        commentsBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less_24dp, 0);
        commentsHidden = true;

    }

    @Override
    public void navigateToImages() {

    }

    @Override
    public void showInformationError() {
        Log.d(getClass().getSimpleName(), "Received failure from presenter... Showing Information Error");
        hideableLayout.setVisibility(View.GONE);
        commentsBtn.setVisibility(View.GONE);
        imagesBtn.setVisibility(View.GONE);
        examIdTextView.setText(getText(R.string.information_error));
        examTypeTextView.setText(getText(R.string.information_error_response));


    }

    @OnClick(R.id.comments_btn)
    public void clickComments() {
        if (commentsHidden) {
            showComments();
        }
        else {
            hideComments();
        }
    }
}
