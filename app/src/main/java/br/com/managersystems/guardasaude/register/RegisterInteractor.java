package br.com.managersystems.guardasaude.register;

import br.com.managersystems.guardasaude.register.domain.LocationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jan on 10/05/2016.
 */
public class RegisterInteractor implements IRegisterInteractor {

    private final String BASE_URL = "https://guardasaude.com.br/";
    private RegisterApi client;

    public RegisterInteractor() {
        client = initiateRetrofit();
    }

    @Override
    public RegisterApi initiateRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RegisterApi.class);
    }

    @Override
    public void getLocations(final OnLocationRetrievedListener listener, String filter) {
        if (client == null) {
            client = initiateRetrofit();
        }
        Call<LocationResponse> call = client.getLocationList(filter);
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                listener.onSuccessfulLocationResponse(response.body());
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                //TODO handle failed locations
            }
        });
    }
}
