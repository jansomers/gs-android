package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;



import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.CommentResponse;
import br.com.managersystems.guardasaude.exams.domain.DocumentResponse;
import br.com.managersystems.guardasaude.exams.domain.ExamList;
import br.com.managersystems.guardasaude.exams.domain.IndividualExamResponse;
import br.com.managersystems.guardasaude.exams.domain.PostCommentResponse;
import br.com.managersystems.guardasaude.exams.domain.ReportResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
            @Query("accessRole") String accessRole,
            @Query("isEmergency") String isEmergency);

    @GET("mobile/getExamReport")
    Call<ReportResponse> getReport(
            @Query("user") String username,
            @Query("token") String token,
            @Query("exid") String exId);

    @GET("mobile/getExamImage")
    Call<DocumentResponse> getExamImage(
            @Query("user") String username,
            @Query("token") String token,
            @Query("exid") String exId,
            @Query("edid") String exDocId
    );

    @GET("mobile/getDocument")
    Call<DocumentResponse> getDocument(
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

    @POST("mobile/associateNewExam")
    Call<AssociatedExamResponse> associateNewExam(
            @Query("user") String username,
            @Query("token") String token,
            @Query("exid") String exId,
            @Query("epasscode") String ePassCode
    );

    @POST("mobile/saveExamComments")
    Call<PostCommentResponse> postComment(
            @Query("user") String username,
            @Query("token") String token,
            @Query("exid") String exId,
            @Query("msg") String comment
    );

    @POST("mobile/readIndividualExam")
    Call<IndividualExamResponse> associateAnonymousExam(
            @Query("exid") String exId,
            @Query("epasscode") String ePassCode
    );

}
