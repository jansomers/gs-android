package br.com.managersystems.guardasaude.exams.exammenu;


public interface IExamTabView {
    /**
     * Calls setTabtitles and getSharedPref methods
     */
    void init();

    /**
     * Sets titles for tabs
     */
    void setTabTitles();

    /**
     * Gets shared preferences from preference manager
     */
    void getSharedPref();

    /**
     * Navigates to the login activity after the user logs out.
     */
    void navigateToLogin();
}
