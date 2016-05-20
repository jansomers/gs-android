package br.com.managersystems.guardasaude.register.domain;

/**
 * Created by Jan on 20/05/2016.
 */
public interface OnRegisteredListener {

    void onRegistered();
    void onFailedToRegister();
}
