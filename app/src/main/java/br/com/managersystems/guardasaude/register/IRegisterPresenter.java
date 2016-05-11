package br.com.managersystems.guardasaude.register;

import android.widget.AutoCompleteTextView;

/**
 * Created by Jan on 11/05/2016.
 */
public interface IRegisterPresenter {
    void retrieveLocations(AutoCompleteTextView cityText, String filter);
}
