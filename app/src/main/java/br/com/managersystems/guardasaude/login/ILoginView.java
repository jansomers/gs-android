package br.com.managersystems.guardasaude.login;

import android.widget.Button;

import java.util.ArrayList;

/**
 * This interfaces consists of methods which are important for the implementation.
 * All methods should not manipulate domain data in any way and is used purely for view purposes.
 *
 * Authors:
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementation:
 *
 * @see br.com.managersystems.guardasaude.ui.activities.LoginActivity
 */
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.login.domain.AccessDomain;


public interface ILoginView {


    /**
     * Initializes view elements upon starting the activity.
     */
    void init();

    /**
     * Navigates to the MainTabActivity
     */
    void navigateToOverviewActivity();


    /**
     * Shows and handles a successful login attempt and requests the presenter to save the info.
     *
     * @param patient Boolean that indicates the role of the user.
     */
    void loginSuccess(boolean patient);

    /**
     * Handles a failed request and forwards it to loginFailed();.
     */
    void requestFailed();

    /**
     * Handles a default failed login.
     */
    void loginFailed();

    /**
     * Shows and handles a failed login.
     *
     * @param code String that represents the error message that needs to be shown.
     */
    void loginFailed(String code);

    /**
     * Shows the option dialog to choose a role.
     * Forwards the selected option to the loginSuccess() method.
     *
     * @param roles ArrayList that represents the roles to choose from.
     */
    void showRoleOptionDialog(ArrayList<String> roles);

    // To be implemented and changed...
    void showDomainOptionDialog(ArrayList<AccessDomain> accessDomainArrayList);
    // To be implemented and changed...
    void domainRetrievedFailed();
    // To be implemented and changed...
    void showServerOptionDialog();

    /**
     * Retrieve of anonymous exam is successful
     * Starts new AnonymousExamInformationActivity and passes exam through intent
     */
    void showAnonymousExam(Exam exam);

    /**
     * Shows failure snackbar
     */
    void showAnonymousExamError();
    /**
     * Calls retrieveAnonymousExam method from presenter and passes accessCode and examId
     */
    void findAnonymousExam(Button findNewExamButton, String accessCodeString, String examIdString);
}
