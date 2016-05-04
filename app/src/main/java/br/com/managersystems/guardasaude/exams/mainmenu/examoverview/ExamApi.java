package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.ExamImage;
import br.com.managersystems.guardasaude.exams.domain.ExamImageResponse;
import br.com.managersystems.guardasaude.exams.domain.ExamList;
import br.com.managersystems.guardasaude.exams.domain.ReportResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

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

    @GET("mobile/getExamImage")
    Call<ResponseBody> getExamImage(
            @Query("user") String username,
            @Query("token") String token,
            @Query("exid") String exId,
            @Query("edid") String exDocId
    );

    @POST("mobile/associateNewExam")
    Call<AssociatedExamResponse> associateNewExam(
            @Query("user") String username,
            @Query("token") String token,
            @Query("exid") String exId,
            @Query("epasscode") String ePassCode
    );

}
