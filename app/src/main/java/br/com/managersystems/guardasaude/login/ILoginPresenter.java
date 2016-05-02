package br.com.managersystems.guardasaude.login;

/**
 * Created by Jan on 14/04/2016.
 */
public interface ILoginPresenter {

    void authorizeLogin(String email, String password);
    String[] encodeLogin(String email, String password);
    void retrieveDomains();

    void saveInfo(boolean patient);
}
