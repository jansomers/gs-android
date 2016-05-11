package br.com.managersystems.guardasaude.login;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.exams.domain.Exam;


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
     * Forwards the selected option to the loginSucces() method.
     *
     * @param roles ArrayList that represents the roles to choose from.
     */
    void showRoleOptionDialog(ArrayList<String> roles);

    // Retrieving domains for admins (currently not correct)
    void domainRetrievedSuccesfully(ArrayList<AccessDomain> accessDomainArrayList);

    void domainRetrievedFailed();

    void showServerOptionDialog();

    /**
     * Retrieve of anonymous exam is successful
     * Starts new AnonymousExamInformationActivity and passes exam through intent
     */
    void anonymousExamSucces(Exam exam);

    /**
     * Shows failure snackbar
     */
    void anonymousExamFailure();

    /**
     * Calls retrieveAnonymousExam method from preseneter and passes accesCode and examId
     */
    void findAnonymousExam(String accessCodeString, String examIdString);
}
