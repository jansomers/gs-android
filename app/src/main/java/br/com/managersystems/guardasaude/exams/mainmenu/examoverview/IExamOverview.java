package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;

import android.content.SharedPreferences;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.login.LoginPresenter;

public interface IExamOverview {
    /**
     * The exam list was received successfully
     * @param exams = The list of exams
     * @param total
     */
    void showExamList(ArrayList<Exam> exams, Integer total);

    /**
     * The exam list was not received successfully
     */
    void showLoadingExamsError();

    /**
     * The new exam was successfully associated with the user
     */
    void onSuccessFindNewExam(AssociatedExamResponse associatedExamResponse);

    /**
     * The new exam could not be associated with the user
     */
    void showInternalFailForNewExam();

    /**
     * Navigate to the detail page of an exam
     * @param exam:
     */
    void navigateToExamDetail(Exam exam);

    /**
     * Initiates the searchViewListener
     * makes it possible for the user to search through the list of exams
     */
    SearchView.OnQueryTextListener getSearchViewListener();

    /**
     * Sets swipeRefreshLayout to call presenter to refresh the exam list.
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
     * Sets the loginPresenter for the fragment
     */
    void setLoginPresenter(LoginPresenter loginPresenter);

    /**
     * Shows the following exams
     * @param rows List of exams to show
     */
    void showNextExamList(ArrayList<Exam> rows);
}
