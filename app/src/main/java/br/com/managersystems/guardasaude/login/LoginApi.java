package br.com.managersystems.guardasaude.login;

import br.com.managersystems.guardasaude.login.domain.AuthorisationResult;
import br.com.managersystems.guardasaude.login.domain.ResetResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Api that consists of login calls.
 */
public interface LoginApi {


    @GET("mobile/authenticateUser")
    Call<AuthorisationResult> authenticateUser(
            @Query("LDWI") String username,
            @Query("GKSP") String password);

    @GET("mobile/resetPassword")
    Call<ResetResponse> resetPassword(
            @Query("email") String email);

}
