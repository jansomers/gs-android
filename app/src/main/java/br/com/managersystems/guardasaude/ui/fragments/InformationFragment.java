package br.com.managersystems.guardasaude.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.core.deps.guava.io.Files;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.domain.Comment;
import br.com.managersystems.guardasaude.exams.domain.DocumentResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.exammenu.information.CommentsAdapter;
import br.com.managersystems.guardasaude.exams.exammenu.information.ExamPresenter;
import br.com.managersystems.guardasaude.exams.exammenu.information.IExamInformationView;
import br.com.managersystems.guardasaude.util.AnimationUtils;
import br.com.managersystems.guardasaude.util.StatusUtils;
import br.com.managersystems.guardasaude.util.StringUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InformationFragment extends Fragment implements IExamInformationView {

    @Bind(R.id.gs_exam_information_exam_id)
    TextView examIdTextView;

    @Bind(R.id.gs_exam_information_exam_referring_phys)
    TextView examRefPhysTextView;

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

    @Bind(R.id.gs_exam_information_extra)
    TextView extraTextView;

    @Bind(R.id.hideable_information_layout)
    RelativeLayout hideableLayout;

    @Bind(R.id.gs_exam_coordinator_lay)
    CoordinatorLayout coordinatorLay;

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

    @Bind(R.id.doc_comment_image_btns_rel_layout)
    RelativeLayout docCommentImageButtons;

    @Bind(R.id.documents_btn)
    Button documentsButton;

    @Bind(R.id.fragment_information_layout)
    RelativeLayout informationRelLayout;

    ExamPresenter presenter;
    SharedPreferences sp;
    CommentsAdapter adapter;
    TextWatcher commentWatcher;
    Handler finishedDocs;
    boolean commentsHidden;
    boolean isPatient;
    boolean docAndImagesHidden;
    private Exam exam;
    private Snackbar snackDocumentNotFound;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Snackbar emptyComment;
    private Snackbar commentError;


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
        finishedDocs = new Handler();
        init();
        return view;
    }

    private void init() {
        initializeSnacks();
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

    private void initializeSnacks() {
        emptyComment = Snackbar.make(coordinatorLay, R.string.comment_empty, Snackbar.LENGTH_SHORT);
        emptyComment.getView().setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorError));
        commentError =  Snackbar.make(coordinatorLay, getText(R.string.comment_failed),Snackbar.LENGTH_LONG);
        commentError.getView().setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorAccent));
    }

    @Override
    public void showInformation(Exam exam) {
        this.exam = exam;
        Log.d(getClass().getSimpleName(), "Received success from Presenter... Showing Information");
        hideableLayout.setVisibility(View.VISIBLE);
        commentsBtn.setVisibility(isPatient ? View.GONE : View.VISIBLE);
        documentsButton.setVisibility(exam.getDocuments().size() > 0 ? View.VISIBLE : View.GONE);
        imagesBtn.setVisibility(isPatient && exam.getStatus().toLowerCase().equals("available") ? View.GONE : View.VISIBLE);
        docCommentImageButtons.setVisibility(isPatient && docAndImagesHidden ? View.GONE : View.VISIBLE);
        examIdTextView.setText(exam.getIdentification());
        examTypeTextView.setText(exam.getServiceName());
        examStatusImageView.setImageDrawable(ContextCompat.getDrawable(this.getActivity(), exam.getStatus().equalsIgnoreCase(getContext().getString(R.string.finished_char)) || exam.getStatus().equalsIgnoreCase(getContext().getString(R.string.ready_char)) ? R.drawable.ic_check_circle_36dp_accent : R.drawable.ic_clock_primary));
        examPatientTextView.setText(StringUtils.anyCaseToNameCase(exam.getPatient()));
        examClinicTextView.setText(StringUtils.anyCaseToNameCase(exam.getClinicName()));
        examDateTextView.setText(exam.getExecutionDate().split(" ")[0]);
        examRepPhysTextView.setText(StringUtils.anyCaseToNameCase(exam.getReportingPhysicianName()));
        examRefPhysTextView.setText(StringUtils.anyCaseToNameCase(exam.getReferringPhysicianName()));
        if (isPatient) extraTextView.setText(exam.getStatus().equals(getText(R.string.finished_char)) ? getText(R.string.finished) : getText(R.string.in_progress));
        else extraTextView.setText(getText(StatusUtils.showCorrespondingStatus(exam.getStatus())));
    }

    @Override
    public void showComments() {
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
       commentError.show();
    }

    @Override
    public void showNewComment() {
        presenter.retrieveComments(examIdTextView.getText().toString(), sp);

    }

    @Override
    public void documentNotFound() {
        snackDocumentNotFound = Snackbar.make(informationRelLayout, getResources().getText(R.string.snackDocNotFound), Snackbar.LENGTH_LONG);
        snackDocumentNotFound.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorError));
        snackDocumentNotFound.show();
        enableButton(documentsButton);
    }

    @Override
    public void showPdfDocument(DocumentResponse response) {
        try {
            verifyStoragePermissions();

            byte[] pdfString = Base64.decode(response.getDocumentValue(), Base64.DEFAULT);

            //Write pdf file to download directory
            File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), response.getExamDocumentIdentification());
            if (!pdfFile.exists()) {
                pdfFile.getParentFile().mkdirs();
                pdfFile.createNewFile();
            }
            FileOutputStream os = new FileOutputStream(pdfFile, true);
            os.write(pdfString);
            Files.write(pdfString, pdfFile);
            Uri path = Uri.fromFile(pdfFile);

            //Launch PDF reader
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                new AlertDialog.Builder(getActivity()).setTitle(getResources().getText(R.string.noPdfReaderDialogTitle)).setMessage(getResources().getText(R.string.noPdfReaderDialogText)).setCancelable(true).create().show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        stopDocumentsProgress();

    }

    private void stopDocumentsProgress() {
        documentsButton.setText(R.string.opening);

    }

    @OnClick(R.id.comments_btn)
    public void clickComments() {
        if (commentsHidden) {
            showComments();
            presenter.retrieveComments(examIdTextView.getText(), sp);
        } else {
            hideComments();
        }
    }

    @OnClick(R.id.save_comment_btn)
    public void clickSaveComment() {
        if (newCommentText.getEditableText().toString().isEmpty()) showCommentEmptyError();
        else  presenter.saveComment(examIdTextView.getText(), newCommentText.getEditableText().toString(), sp);


    }

    private void showCommentEmptyError() {
        emptyComment.show();
    }

    private void disableButton(Button button) {
        button.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.colorTextColorLight));
        button.setEnabled(false);
    }

    private void enableButton(Button button) {
        button.setTextColor(ContextCompat.getColor(this.getActivity(),R.color.colorError));
        button.setEnabled(true);
    }

    @Override
    @OnClick(R.id.images_btn)
    public void navigateToImages(){
        ViewPager viewPager = (ViewPager)getActivity().findViewById(R.id.gs_maintab_activity_pager);
        viewPager.setCurrentItem(2);
    }

    @OnClick(R.id.documents_btn)
    public void downloadDocuments(){
        showDocumentsProgress();
        presenter.retrieveDocuments(exam, sp);
    }

    private void showDocumentsProgress() {
        disableButton(documentsButton);
        documentsButton.setText(R.string.loading);
    }

    @Override
    public void setDocAndImagesHidden(boolean docAndImagesHidden) {
        this.docAndImagesHidden = docAndImagesHidden;
    }

    @Override
    public void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}