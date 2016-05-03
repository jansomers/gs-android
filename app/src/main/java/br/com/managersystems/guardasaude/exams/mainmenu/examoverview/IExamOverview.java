package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

import android.content.SharedPreferences;

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
    void initiateSearchViewListener();
    void setLoginPresenter(LoginPresenter loginPresenter);
}
