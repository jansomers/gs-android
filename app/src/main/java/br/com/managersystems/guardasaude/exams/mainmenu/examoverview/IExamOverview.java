package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

import android.content.SharedPreferences;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.login.LoginPresenter;

public interface IExamOverview {
    void onSuccessExamList(ArrayList<Exam> exams);
    void onFailureExamList();
    void onSuccessFindNewExam(AssociatedExamResponse associatedExamResponse);
    void onFailureFindNewExam();
    void navigateToExamDetail(Exam exam);
    void setSharedPreferences(SharedPreferences sharedPreferences);
    SearchView.OnQueryTextListener getSearchViewListener();
    void setLoginPresenter(LoginPresenter loginPresenter);
}
