package br.com.managersystems.guardasaude.login;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.login.domain.AccessDomain;

public interface OnDomainRetrievedListener {

    void onDomainRetrieved(ArrayList<AccessDomain> domainList);
    void onDomainFailed();
}
