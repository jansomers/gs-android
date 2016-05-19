package br.com.managersystems.guardasaude.register;

import android.util.Log;

import br.com.managersystems.guardasaude.register.domain.LocationResponse;
import br.com.managersystems.guardasaude.register.domain.RegistrationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * This class is an implementation of the IRegisterInteractor
 * <p/>
 * Authors:
 *
 * @author Jan Somers
 * @author Thanee Stevens
 *         <p/>
 *         Also see:
 * @see IRegisterInteractor
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
        Call<LocationResponse> call = client.getCityList(filter);
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                listener.onSuccessfulLocationResponse(response.body());
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                Log.d(this.getClass().getSimpleName(), "Server didn't send a response after calling the locations");
            }
        });
    }

    @Override
    public void addNewAccount(String firstName, String lastName, String email, String country, String city, String password, String verificationPw, String identification, String idType, String gender, String birthDate) {
        if (client == null) {
            client = initiateRetrofit();
        }
        Call<RegistrationResponse> call = client.createNewAccount(firstName,lastName,email,city,password,verificationPw,identification,idType,gender,birthDate);
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                //DO STH
                RegistrationResponse registrationResponse = response.body();
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                //DO STH
            }
        });

    }
}
