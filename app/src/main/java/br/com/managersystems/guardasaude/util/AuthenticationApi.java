package br.com.managersystems.guardasaude.util;

import br.com.managersystems.guardasaude.login.domain.AuthorisationResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthenticationApi {

    @GET("mobile/authenticateUser")
    Call<AuthorisationResult> authenticateUser(
            @Query("LDWI") String username,
            @Query("GKSP") String password);
}
