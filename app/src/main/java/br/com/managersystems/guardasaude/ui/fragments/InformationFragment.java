package br.com.managersystems.guardasaude.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.domain.Comment;
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

    @Bind(R.id.new_comment_input)
    EditText newCommentText;

    @Bind(R.id.save_comment_btn)
    ImageButton saveCommentBtn;

    @Bind(R.id.gs_exam_comment_recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.gs_exam_comment_section_layout)
    LinearLayout commentLayout;


    ExamPresenter presenter;
    SharedPreferences sp;
    CommentsAdapter adapter;
    TextWatcher commentWatcher;
    boolean commentsHidden;
    boolean isPatient;
      //final Exam DUMMY_EXAM = new Exam(167511113, "TMC56257", "RM ARTICULAR(PORATICULACAO)", "ATIDOR SILVA CORDOSO DOS SANTOS", "12/01/2016 12:10", "Finished", "JOHN SMITH", "CRMPR/98765", "JOSE CANDIDO VALENTE MALAGUIDO", "CRM SC/17989", "/mobile/getExamReport?user=doctor&exid=TMC56257", null);




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, view);
        presenter = new ExamPresenter(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        commentWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                saveCommentBtn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (newCommentText.getText().toString().isEmpty()) saveCommentBtn.setEnabled(false);
                else {
                    saveCommentBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        init();
        return view;
    }

    private void init() {
        isPatient = (sp.getString("role", "").equals("ROLE_PATIENT"));
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
        commentsBtn.setVisibility(isPatient ? View.GONE : View.VISIBLE);
        imagesBtn.setVisibility(isPatient && exam.getStatus().toLowerCase().matches("finished | ready")? View.GONE: View.VISIBLE);
        examIdTextView.setText(exam.getIdentification());
        examTypeTextView.setText(exam.getServiceName());
        examStatusImageView.setImageDrawable(ContextCompat.getDrawable(this.getActivity(), exam.getStatus().equalsIgnoreCase(getContext().getString(R.string.finished)) || exam.getStatus().equalsIgnoreCase(getContext().getString(R.string.ready)) ? R.drawable.ic_check_circle_36dp_accent : R.drawable.ic_clock_primary));
        examPatientTextView.setText(StringUtils.anyCaseToNameCase(exam.getPatient()));
        examClinicTextView.setText(StringUtils.anyCaseToNameCase(exam.getClinicName()));
        examDateTextView.setText(exam.getExecutionDate().split(" ")[0]);
        examRepPhysTextView.setText(StringUtils.anyCaseToNameCase(exam.getReportingPhysicianName()));

    }

    @Override
    public void showComments() {
        // set date reporting gone
        AnimationUtils.collapse(hideableLayout);
        AnimationUtils.expand(commentLayout);
        commentsBtn.setText(getText(R.string.information));
        commentsBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more_24dp_primary, 0);
        commentsHidden = false;
    }

    @Override
    public void hideComments() {
        AnimationUtils.collapse(commentLayout);
        AnimationUtils.expand(hideableLayout);
        commentsBtn.setText(getText(R.string.comments));
        commentsBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less_24dp_primary, 0);
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

    @Override
    public void disableComments() {
        if (!commentsHidden) hideComments();
        if (commentsBtn.getVisibility() != View.GONE) commentsBtn.setVisibility(View.GONE);
    }

    @Override
    public void enableComments(List<Comment> comments) {
        adapter = new CommentsAdapter(comments, this.getActivity().getApplication(), recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.onDataChanged(recyclerView);

    }

    @Override
    public void showCommentPostError() {
        Toast.makeText(this.getContext(), "Failed to post comment, try again", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showNewComment() {
        presenter.retrieveComments(examIdTextView.getText().toString(), sp);

    }

    @OnClick(R.id.comments_btn)
    public void clickComments() {
        if (commentsHidden) {
            showComments();
            presenter.retrieveComments(examIdTextView.getText(), sp);
        }
        else {
            hideComments();
        }
    }

    @OnClick(R.id.save_comment_btn)
    public void clickSaveComment(){
        presenter.saveComment(examIdTextView.getText(), newCommentText.getEditableText().toString(), sp);

    }
}
