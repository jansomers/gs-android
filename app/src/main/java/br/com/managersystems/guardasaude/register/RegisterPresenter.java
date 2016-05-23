package br.com.managersystems.guardasaude.register;

import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import br.com.managersystems.guardasaude.register.domain.LocationResponse;
import br.com.managersystems.guardasaude.register.domain.LocationRow;
import br.com.managersystems.guardasaude.register.domain.OnRegisteredListener;
import br.com.managersystems.guardasaude.ui.activities.RegisterActivity;
import br.com.managersystems.guardasaude.util.StringUtils;

/**
 * This class is an implementation of the IRegisterPresenter
 * <p/>
 * Authors:
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *         <p/>
 *         Also see:
 * @see IRegisterPresenter
 */
public class RegisterPresenter implements IRegisterPresenter, OnRegisteredListener, OnLocationRetrievedListener{

    AutoCompleteTextView cityText;
    RegisterInteractor interactor;
    IRegisterView activity;
    public RegisterPresenter(RegisterActivity activity) {
        this.activity = activity;
        interactor = new RegisterInteractor();

    }


    public void setCityText(AutoCompleteTextView cityText) {
        this.cityText = cityText;
    }

    @Override
    public void retrieveLocations(AutoCompleteTextView cityText, String filter) {
        setCityText(cityText);
        interactor.getLocations(this, filter);

    }

    @Override
    public void registerUser(String firstName, String lastName, String email, String country, String city, String password, String verificationPw, String identification, String idType, String gender, String birthDate) {
        String digitCPF = StringUtils.tryReformatCPF(identification);
        interactor.addNewAccount(this,firstName,lastName,email,country, city, password, verificationPw,digitCPF,idType,gender, birthDate);
    }

    @Override
    public void onSuccessfulLocationResponse(LocationResponse locationResponse) {
        List<String> cities = new ArrayList<>();
        List<String> cityIds = new ArrayList<>();
        for (LocationRow row : locationResponse.getRows()) {
            cities.add(row.getLocationValue());
            cityIds.add(row.getLocationID());
        }
        activity.showCitySuggestions(cityText,cityIds, cities);
    }

    @Override
    public void onFailureLocationResponse() {

    }

    @Override
    public void onRegistered() {
        activity.showSuccessfulRegistration();
    }

    @Override
    public void onFailedToRegister() {
        activity.showUnsuccessfulRegistration();
    }
}
