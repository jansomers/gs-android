package br.com.managersystems.guardasaude.register;

import br.com.managersystems.guardasaude.register.domain.LocationResponse;


public interface OnLocationRetrievedListener {
    /**
     * Calls the view to show the filtered locations.
     * @param locationResponse LocationResponse object that holds the list of locations.
     */
    void onSuccessfulLocationResponse(LocationResponse locationResponse);

    /**
     * Calls the view to not show the filter locations because something went wrong.
     */
    void onFailureLocationResponse();
}
