package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import br.com.managersystems.guardasaude.exams.domain.CommentResponse;
import br.com.managersystems.guardasaude.exams.domain.ExamList;
import br.com.managersystems.guardasaude.exams.domain.ReportResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExamApi {

    @GET("mobile/listExams")
    Call<ExamList> getExamsList(
            @Query("user") String username,
            @Query("token") String token,
            @Query("orderBy") String orderBy,
            @Query("sortBy") String sortBy,
            @Query("maxValue") String maxValue,
            @Query("offsetValue")String offsetValue,
            @Query("filterBy")String filterBy,
            @Query("accessRole") String accessRole);

    @GET("mobile/getExamReport")
    Call<ReportResponse> getReport(
            @Query("user") String username,
            @Query("token") String token,
            @Query("exid") String exId);

    @GET("mobile/getExamImage")
    Call<ResponseBody> getExamImage(
            @Query("user") String username,
            @Query("token") String token,
            @Query("exid") String exId,
            @Query("edid") String exDocId
    );
    @GET("mobile/getExamComments")
    Call<CommentResponse> getComments(
            @Query("user")String username,
            @Query("token")String token,
            @Query("exid") String exId);
}
