package br.com.managersystems.guardasaude.register;

import android.widget.AutoCompleteTextView;

/**
 * This interface serves as a transportation layer between the view and the interactor.
 * In this case it handles the registration.
 * <p/>
 * The documentation briefly explain what the method does.
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *         <p/>
 *         Implementations / Also see:
 * @see RegisterPresenter
 * @see IRegisterInteractor
 */
public interface IRegisterPresenter {
    void retrieveLocations(AutoCompleteTextView cityText, String filter);

    void registerUser(String firstName, String lastName, String email, String country, String city, String password, String verificationPw, String identification, String idType, String gender, String birthDate);
}
