package br.com.managersystems.guardasaude.login;

import br.com.managersystems.guardasaude.login.domain.AuthorisationResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Api that consists of login calls.
 */
public interface LoginApi {

    /**
     * Authenticates the user by making a call to the database.
     * Retrofit handles this call accordingly.
     * @param username String representing the username.
     * @param password String representing the password.
     * @return
     */
    @GET("mobile/authenticateUser")
    Call<AuthorisationResult> authenticateUser(
            @Query("LDWI") String username,
            @Query("GKSP") String password);

}
