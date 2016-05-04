package br.com.managersystems.guardasaude.exams.exammenu.information;

import java.util.List;

import br.com.managersystems.guardasaude.exams.domain.Comment;
import br.com.managersystems.guardasaude.exams.domain.Exam;

/**
 * Created by Jan on 26/04/2016.
 */
public interface IExamInformationView {
    void showInformation(Exam exam);
    void showComments();
    void hideComments();
    void navigateToImages();
    void showInformationError();

    void disableComments();
    void enableComments(List<Comment> comments);
}
