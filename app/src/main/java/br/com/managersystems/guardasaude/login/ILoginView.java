package br.com.managersystems.guardasaude.login;

import java.util.ArrayList;


public interface ILoginView {
    // Retrieving domains for admins (currently not correct)
    void domainRetrievedSuccesfully(ArrayList<AccessDomain> accessDomainArrayList);
    void domainRetrievedFailed();

    // Login results
    void loginSuccess(boolean patient);
    void requestFailed();
    void loginFailed(String code);
    void loginFailed();

    // Navigation
    void navigateToOverviewActivity(boolean patient);

    void showRoleOptionDialog(ArrayList<String> roles);

    void showServerOptionDialog();
}
