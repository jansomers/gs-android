package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

import android.content.SharedPreferences;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.login.LoginPresenter;

public interface IExamOverview {
    void onSuccess(ArrayList<Exam> exams);
    void onFailure();
    void navigateToExamDetail(Exam exam);
    void initiateSearchViewListener();
    void setSharedPreferences(SharedPreferences sharedPreferences);

    void setLoginPresenter(LoginPresenter loginPresenter);
}
