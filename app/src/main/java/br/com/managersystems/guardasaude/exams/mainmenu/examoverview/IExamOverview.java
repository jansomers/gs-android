package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

import android.content.SharedPreferences;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.login.LoginPresenter;

public interface IExamOverview {
    /**
     * The examlist was received successfully
     * @param exams = The list of exams
     */
    void onSuccessExamList(ArrayList<Exam> exams);

    /**
     * The examlist was not received successfully
     */
    void onFailureExamList();

    /**
     * The new exam was successfully associated with the user
     */
    void onSuccessFindNewExam(AssociatedExamResponse associatedExamResponse);

    /**
     * The new exam could not be associated with the user
     */
    void onFailureFindNewExam();

    /**
     * Navigate to the detail page of an exam
     * @param exam:
     */
    void navigateToExamDetail(Exam exam);

    /**
     * Initiate the searchviewlistener
     * makes it possible for the user to search through the list of exams
     */
    SearchView.OnQueryTextListener getSearchViewListener();

    /**
     * Sets swipeRefreshLayout to call presenter to refresh examlist
     */
    void init();

    /**
     * Initializes the snackbars used in this fragment
     */
    void initSnackBars();

    /**
     * Initializes and show the SortByDialog
     */
    void showSortByDialog();

    /**
     * Sets the shared preferences for the fragment
     */
    void setSharedPreferences(SharedPreferences sharedPreferences);

    /**
     * Sets the loginpresenter for the fragment
     */
    void setLoginPresenter(LoginPresenter loginPresenter);

    void onSuccessNextExamList(ArrayList<Exam> rows);
}
