package br.com.managersystems.guardasaude.exams.mainmenu;


public interface IMainTabView {
    /**
     * Sets supportActionToolbar
     * Initializes loginPresenter
     * Sets adapter for viewpager
     */
    void init();

    /**
     * Navigates to new login activity
     */
    void navigateToLogin();

    /**
     * Sets titles for tabs
     */
    void setTabTitles();

    /**
     * Get shared preferences from preference manager
     */
    void getSharedPref();
}
