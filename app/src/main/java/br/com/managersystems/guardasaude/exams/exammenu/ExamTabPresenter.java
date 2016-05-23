package br.com.managersystems.guardasaude.exams.exammenu;

import android.content.Intent;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.ui.activities.ExamTabActivity;

public class ExamTabPresenter {
    private ExamTabActivity activity;

    public ExamTabPresenter(ExamTabActivity examTabActivity) {
        this.activity = examTabActivity;
    }

    public void retrieveExam(Intent intent) {
        Exam exam =intent.getParcelableExtra("exam");
        if (!(exam.getId() == 0)) {
            if(exam.getStatus().equalsIgnoreCase(activity.getString(R.string.finished_char))||exam.getStatus().equalsIgnoreCase(activity.getString(R.string.ready_char))||exam.getStatus().equalsIgnoreCase(activity.getString(R.string.available))) {
                activity.setExamStatusIsReady(true);
            }else{
                activity.setExamStatusIsReady(false);
            }
        }
        else {
           //TODO HANDLE NO EXAM FOUND
        }
    }

}
