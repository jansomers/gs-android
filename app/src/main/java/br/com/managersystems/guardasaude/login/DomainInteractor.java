package br.com.managersystems.guardasaude.login;

import java.util.ArrayList;

/**
 * Created by Jan on 15/04/2016.
 */
public class DomainInteractor implements IDomainInteractor {

    private ArrayList<AccessDomain> domainList;
    private AccessDomain liveDomain;
    private AccessDomain demoDomain;

    public DomainInteractor(ArrayList<AccessDomain> domainList) {
        this.domainList = domainList;
        initList();
    }

    private void initList() {
        liveDomain = new AccessDomain(1, "Live", "managersystems.com.br");
        domainList.add(liveDomain);
        demoDomain = new AccessDomain(2, "Demo", "demo.managersystems.com.br");
        domainList.add(demoDomain);
    }

    public ArrayList<AccessDomain> getDomainList() {
        return domainList;
    }

    @Override
    public void getDomains(LoginPresenter loginPresenter) {
        if (domainList != null) {
            loginPresenter.onDomainRetrieved(getDomainList());
        }
    }
}
