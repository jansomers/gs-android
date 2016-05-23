package br.com.managersystems.guardasaude.register.domain;


public interface OnRegisteredListener {

    /**
     * Notifies view that registration was successful.
     */
    void onRegistered();

    /**
     * Alerts the view that registration failed.
     */
    void onFailedToRegister();
}
