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

    /*@Bind(R.id.report_web_view)
    WebView reportView;
*/
    @Bind(R.id.report_test_textview)
    TextView textView;
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
        Log.d(getClass().getSimpleName(), "Received report from presenter.. Showing webview!");
       /* reportView.getSettings().setJavaScriptEnabled(true);
        reportView.loadData(report, "text/html", "UTF-8");*/
        String text = String.valueOf(Html.fromHtml(report));
        String withOutComments = text.replaceAll("<!--*-->", " ");
        textView.setText(withOutComments);

    }

    @Override
    public void showError() {
        Log.d(getClass().getSimpleName(), "Received error alert from presenter.. Showing error in view!");
        
    }
}
