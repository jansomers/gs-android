package br.com.managersystems.guardasaude.login;

import java.util.ArrayList;

/**
 * Created by Jan on 15/04/2016.
 */
public interface OnDomainRetrievedListener {

    void onDomainRetrieved(ArrayList<AccessDomain> domainList);
    void onDomainFailed();
}
