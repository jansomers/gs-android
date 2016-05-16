package br.com.managersystems.guardasaude.login;

import java.util.ArrayList;

import br.com.managersystems.guardasaude.login.domain.AccessDomain;

/**
 * This class is a <b>Dummy Implementation</b> of the IDomainInteractor class.
 * The methods used can give an interpretation of how the domain request will be handle,
 * currently without calling a database.
 * <p/>
 * Authors:
 *
 * @author Jan Somers
 * @author Thanee Stevens
 * <p/>
 * Also see:
 * @see IDomainInteractor
 * @see OnDomainRetrievedListener
 * @see LoginPresenter
 */
public class DomainInteractor implements IDomainInteractor {

    private ArrayList<AccessDomain> domainList;
    private AccessDomain liveDomain;
    private AccessDomain demoDomain;

    public DomainInteractor(ArrayList<AccessDomain> domainList) {
        this.domainList = domainList;
        initList();
    }

    /**
     * Initiates the list of possible AccessDomains.
     */
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
