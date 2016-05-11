package br.com.managersystems.guardasaude.register;

/**
 * Created by Jan on 10/05/2016.
 */
public interface OnLocationRetrievedListener {
    void onSuccessfulLocationResponse(LocationResponse locationResponse);
    void onFailureLocationResponse();
}
