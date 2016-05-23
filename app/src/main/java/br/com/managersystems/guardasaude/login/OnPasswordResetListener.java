package br.com.managersystems.guardasaude.login;


public interface OnPasswordResetListener {

    /**
     * Notifies the view to show a successful reset password request.
     */
    void onPassWordReset();

    /**
     * Alerts the view to show a password reset error.
     */
    void onPassWordResetFailed();
}
