package br.com.managersystems.guardasaude.exams.exammenu.information;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import br.com.managersystems.guardasaude.exams.domain.Comment;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.ui.fragments.InformationFragment;

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
    ExamInteractor examInteractor;

    public ExamPresenter(InformationFragment informationFragment) {
        this.informationFragment = informationFragment;
        examInteractor = new ExamInteractor(this);
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
}
