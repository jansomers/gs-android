package br.com.managersystems.guardasaude.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.exammenu.report.IExamReportView;
import br.com.managersystems.guardasaude.exams.exammenu.report.ReportPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;


public class ReportFragment extends Fragment implements IExamReportView {

    @Bind(R.id.report_test_textview)
    TextView reportTextView;

    ReportPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, view);
        presenter = new ReportPresenter(this);
        init();
        return view;
    }

    private void init() {
        Log.d(getClass().getSimpleName(),"Initiating Report: retrieving exam");
        presenter.retrieveExam(getActivity().getIntent());
    }

    @Override
    public void showReport(String report) {
        String text = String.valueOf(Html.fromHtml(report));
        // Tried to remove any comments from the html string.
        String withOutComments = text.replaceAll("<!--*-->", " ");
        reportTextView.setText(withOutComments);

    }

    @Override
    public void showReportError() {
        Log.d(getClass().getSimpleName(), "Received error alert from presenter.. Showing error in view!");
        reportTextView.setText(getActivity().getText(R.string.no_report));
    }
}
