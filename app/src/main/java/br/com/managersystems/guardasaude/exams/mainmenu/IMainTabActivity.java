package br.com.managersystems.guardasaude.exams.mainmenu;


public interface IMainTabActivity {
    /**
     * Sets supportactiontoolbar
     * Initializes loginpresenter
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
