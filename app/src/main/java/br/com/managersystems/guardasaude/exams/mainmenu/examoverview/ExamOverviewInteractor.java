package br.com.managersystems.guardasaude.exams.mainmenu.examoverview;


import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.ExamList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExamOverviewInteractor implements IExamListInteractor {

    private final String BASE_URL = "https://www.guardasaude.com.br/";
    private ExamApi examApi;


    public ExamOverviewInteractor() {
        examApi = initiateRetrofit();
    }

    @Override
    public ExamApi initiateRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ExamApi.class);
    }


    @Override
    public void getExamList(final OnCallExamListFinishedListener listener, final String userName, final String token, final String orderBy, final String sortBy, final String maxValue, final String offsetValue, final String filterBy, final String accesRole) {
        if (examApi == null) {
            examApi = initiateRetrofit();
        }
        Call<ExamList> call = examApi.getExamsList(userName, token, orderBy, sortBy, maxValue, offsetValue, filterBy, accesRole);
        call.enqueue(new Callback<ExamList>() {
            @Override
            public void onResponse(Call<ExamList> call, Response<ExamList> response) {
                listener.onSuccessGetExamList(response.body());
            }

            @Override
            public void onFailure(Call<ExamList> call, Throwable t) {
                listener.onFailureGetExamList();
            }
        });
    }

    @Override
    public void associateNewExam(final OnCallExamListFinishedListener listener,String user, String token, String exid, String ePassCode) {
        if(examApi==null){
            examApi = initiateRetrofit();
        }
        Call<AssociatedExamResponse> call = examApi.associateNewExam(user,token,exid,ePassCode);
        call.enqueue(new Callback<AssociatedExamResponse>() {
            @Override
            public void onResponse(Call<AssociatedExamResponse> call, Response<AssociatedExamResponse> response) {
                listener.onSuccessFindNewExam(response.body());
                //TODO check response
            }

            @Override
            public void onFailure(Call<AssociatedExamResponse> call, Throwable t) {
                listener.onFailureFindNewExam();
            }
        });
    }


}
