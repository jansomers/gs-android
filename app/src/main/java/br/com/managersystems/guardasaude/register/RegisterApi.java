package br.com.managersystems.guardasaude.register;

import br.com.managersystems.guardasaude.register.domain.LocationResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RegisterApi {

    @GET("/mobile/getLocationList")
    Call<LocationResponse> getLocationList(
        @Query("location") String locFilter
    );
}
