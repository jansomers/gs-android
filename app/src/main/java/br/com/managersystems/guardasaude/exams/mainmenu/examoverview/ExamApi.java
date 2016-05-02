package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import br.com.managersystems.guardasaude.exams.domain.ExamList;
import br.com.managersystems.guardasaude.exams.domain.ReportResponse;
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
            @Query("user") String user,
            @Query("token") String token,
            @Query("exid") String identification);
}
