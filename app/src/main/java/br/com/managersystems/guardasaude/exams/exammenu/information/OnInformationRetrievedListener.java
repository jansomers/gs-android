package br.com.managersystems.guardasaude.exams.exammenu.information;

import java.util.List;

import br.com.managersystems.guardasaude.exams.domain.Comment;
import br.com.managersystems.guardasaude.exams.domain.Exam;

/**
 * Created by Jan on 26/04/2016.
 */
public interface OnInformationRetrievedListener {

    void onFailure();
    void onExamSuccess(Exam exam);

    void onUnableToMakeCommentsCall();
    void onCommentsRetrievedSuccesfully(List<Comment> comments);

    void onPostCommentCallFailed();
    void onCommentPostedFailure();
    void onCommentPostedSuccess();
}
