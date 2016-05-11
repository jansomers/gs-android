package br.com.managersystems.guardasaude.login;

import java.util.ArrayList;

public interface OnDomainRetrievedListener {

    void onDomainRetrieved(ArrayList<AccessDomain> domainList);
    void onDomainFailed();
}
