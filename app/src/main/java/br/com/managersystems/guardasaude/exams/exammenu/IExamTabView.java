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
     * Get shared preferences from preference manager
     */
    void getSharedPref();

    void navigateToLogin();
}
