package br.com.managersystems.guardasaude.register;

import br.com.managersystems.guardasaude.register.domain.OnRegisteredListener;

/**
 * This interface consists of methods needed to successfully handle validation of new accounts,
 * including posting the final form to the server.
 *
 * In every method, the listener is called, which is, in this case, an instance of the RegisterPresenter.
 *
 * The documentation briefly explains what the method does.
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *
 * Implementations / Also see:
 *
 * @see RegisterInteractor
 * @see OnLocationRetrievedListener
 * @see RegisterPresenter
 *
 */
public interface IRegisterInteractor {
    /**
     * Initiates the retrofit client
     * @return RegisterApi object with retrofit built.
     */
    RegisterApi initiateRetrofit();

    /**
     * Gets the list of locations that matches the filter.
     * Notifies the listener afterwards.
     * @param listener OnLocationRetrievedListener representing the register presenter.
     * @param filter String object representing the location filter.
     */
    void getLocations(OnLocationRetrievedListener listener, String filter);

    /**
     * Sends a request to the server to make a new account.
     * Notifies the listener afterwards
     * @param firstName String representing the first name of the new user.
     * @param lastName String representing the last name of the new user.
     * @param email String representing the email address of the new user.
     * @param country String representing the country of the new user. (Abbreviated)
     * @param city String representing the city of the new user.
     * @param password String representing the password of the new user.
     * @param verificationPw String representing the matching verification password of the new user.
     * @param identification String representing the identification code that matches the idType.
     * @param idType String representing the type of identification of the new user.
     * @param gender One Character string representing the gender of the new user.
     * @param birthDate String in dd/MM/yyyy format representing the date of birth of the new user.
     */
    void addNewAccount(OnRegisteredListener listener, String firstName, String lastName, String email, String country, String city, String password, String verificationPw, String identification, String idType, String gender, String birthDate);
}
