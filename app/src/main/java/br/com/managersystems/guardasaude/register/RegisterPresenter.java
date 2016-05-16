package br.com.managersystems.guardasaude.register;

import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import br.com.managersystems.guardasaude.register.domain.LocationResponse;
import br.com.managersystems.guardasaude.register.domain.LocationRow;
import br.com.managersystems.guardasaude.ui.activities.RegisterActivity;

/**
 * Created by Jan on 10/05/2016.
 */
public class RegisterPresenter implements IRegisterPresenter,OnLocationRetrievedListener{

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
    public void onSuccessfulLocationResponse(LocationResponse locationResponse) {
        List<String> cities = new ArrayList<>();
        for (LocationRow row : locationResponse.getRows()) {
            cities.add(row.getLocationName());
        }
        activity.showCitySuggestions(cityText, cities);
    }

    @Override
    public void onFailureLocationResponse() {

    }
}
