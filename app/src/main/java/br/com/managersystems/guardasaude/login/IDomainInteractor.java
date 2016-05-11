package br.com.managersystems.guardasaude.login;

/**
 * <b>***IMPORTANT*** Not implemented due to certification problems.</b>
 * <p/>
 * This interface consists of methods that are need to successfully handle domain requests,
 * called by the presenter.
 * <p/>
 * In every method, the listener is called, which is, in this case, the loginpresenter.
 * <p/>
 * The documentation briefly explains what the method does.
 *
 * Authors:
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementation
 */
public interface IDomainInteractor {
    void getDomains(LoginPresenter loginPresenter);
}
