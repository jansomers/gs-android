package br.com.managersystems.guardasaude.exams.exammenu.information;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.managersystems.guardasaude.exams.domain.Comment;
import br.com.managersystems.guardasaude.exams.domain.Document;
import br.com.managersystems.guardasaude.exams.domain.DocumentResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.ui.fragments.InformationFragment;
import br.com.managersystems.guardasaude.util.Base64Interactor;

/**
 * This class is an implementation of the IExamPresenter. It also implements the
 * OnInformationRetrievedListener.
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Also see:
 * @see IExamPresenter
 * @see OnInformationRetrievedListener
 * @see IExamInteractor
 * @see InformationFragment

 */
public class ExamPresenter implements IExamPresenter, OnInformationRetrievedListener {
    
    IExamInformationView informationFragment;
    Base64Interactor base64Interactor;
    ExamInteractor examInteractor;
    List<DocumentResponse> documentFiles = new ArrayList<>();


    public ExamPresenter(InformationFragment informationFragment) {
        this.informationFragment = informationFragment;
        examInteractor = new ExamInteractor(this);
        base64Interactor = new Base64Interactor();
    }

    @Override
    public void retrieveInformation(Intent intent) {
        Log.d(getClass().getSimpleName(), "Received intent from view... Forwarding to interactor");
        examInteractor.getExam(intent);
    }

    @Override
    public void retrieveComments(CharSequence exid, SharedPreferences sp) {
        examInteractor.getCommentsForExam(exid, sp);
    }

    @Override
    public void saveComment(CharSequence comment, CharSequence exid, SharedPreferences sp) {
        examInteractor.postNewComment(comment, exid, sp);
    }


    @Override
    public void onExamFailure() {
        Log.d(getClass().getSimpleName(), "Received interactor failure... Alerting view!");
        informationFragment.showInformationError();
    }

    @Override
    public void onExamSuccess(Exam exam) {
        Log.d(getClass().getSimpleName(), "Received interactor success... Notifying view!");
        informationFragment.showInformation(exam);
    }

    @Override
    public void onCommentsRetrievedFailure() {
        Log.d(this.getClass().getSimpleName(), "Received unable comment call... Alerting view!");
        informationFragment.disableComments();
    }

    @Override
    public void onCommentsRetrievedSuccess(List<Comment> comments) {
        Log.d(this.getClass().getSimpleName(), "Received comments succesfully... Notifying view!");
        informationFragment.enableComments(comments);
    }

    @Override
    public void onPostCommentCallFailed() {
        Log.d(this.getClass().getSimpleName(), "Received comment post call failure... Alerting view!");
        informationFragment.showCommentPostError();
    }

    @Override
    public void onCommentPostedFailure() {
        Log.d(this.getClass().getSimpleName(), "Received comment post call failure... Alerting view!");
        informationFragment.showCommentPostError();
    }

    @Override
    public void onCommentPostedSuccess() {
        Log.d(this.getClass().getSimpleName(), "Received comment post call success!... Notifying view!");
        informationFragment.showNewComment();
    }

    @Override
    public void onDocumentSuccess(DocumentResponse response) {
        documentFiles.add(response);
    }

    @Override
    public void onDocumentFailure() {
        informationFragment.documentNotFound();
    }

    @Override
    public void onAllDocumentsSuccess() throws IOException {

        if (documentFiles.size() <= 0) {
            informationFragment.documentNotFound();
        } else {
            for (DocumentResponse response : documentFiles) {
                if(response.getDocumentValue()!=null) {
                    informationFragment.showPdfDocument(response);
                }
            }
        }
    }

    public void retrieveDocuments(Exam exam, SharedPreferences sp) {
        for (Document document:exam.getDocuments()) {
            examInteractor.getDocument(exam,document.getExamIdentification(),document.getDocIdentification(),sp);
        }
    }


}
