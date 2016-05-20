package br.com.managersystems.guardasaude.register;

import br.com.managersystems.guardasaude.register.domain.LocationResponse;
import br.com.managersystems.guardasaude.register.domain.RegistrationResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Api that consists of calls that are required to register a new user or to fill in the
 * registration form.
 */
public interface RegisterApi {


    @GET("/mobile/getCityList")
    Call<LocationResponse> getCityList(
        @Query("location") String locFilter
    );

    @POST("/mobile/createNewAccount")
    Call<RegistrationResponse> createNewAccount(
            @Query("firstName")String firstName,
            @Query("lastName") String lastName,
            @Query("email") String email,
            @Query("city") String city,
            @Query("password")String password,
            @Query("confirmPassword")String verificationPw,
            @Query("idNumber")String identification,
            @Query("identificationType")String idType,
            @Query("gender")String gender,
            @Query("dob")String birthDate);
}
