package br.com.managersystems.guardasaude.register;

/**
 * Created by Jan on 11/05/2016.
 */
public interface IRegisterInteractor {
    RegisterApi initiateRetrofit();

    void getLocations(OnLocationRetrievedListener listener, String filter);
}
