package br.com.managersystems.guardasaude.ui.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.domain.AssociatedExamResponse;
import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.ExamAdapter;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.ExamOverviewPresenter;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.IExamOverview;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.NewExamDialogListener;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.NewExamListener;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.SortDialogListener;
import br.com.managersystems.guardasaude.login.LoginPresenter;
import br.com.managersystems.guardasaude.ui.activities.ExamTabActivity;
import br.com.managersystems.guardasaude.ui.dialogs.SortByDialogFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ExamOverviewFragment extends Fragment implements IExamOverview, SortDialogListener, NewExamListener {
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.examOverviewList)
    RecyclerView recyclerView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.failText)
    TextView failText;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefresh;

    @Bind(R.id.gs_exam_overview_coordinator_layout)
    CoordinatorLayout examOverviewCoordinatorLayout;

    Menu menu;
    private ExamOverviewPresenter overviewPresenter;
    private LoginPresenter loginPresenter;
    private ExamAdapter adapter;
    private SharedPreferences sp;
    private List<Exam> examList = Collections.EMPTY_LIST;
    private String sortBy = null;
    private String orderBy = null;
    private Snackbar snackSuccesfulNewExam;
    private Snackbar snackWrongACNewExam;
    private Snackbar snackInternalFailNewExam;

    public ExamOverviewFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_examoverview, container, false);
        ButterKnife.bind(this, view);

        progressBar.setVisibility(View.VISIBLE);

        loginPresenter = new LoginPresenter(this.getActivity(), sp);

        overviewPresenter = new ExamOverviewPresenter(this, sp);
        overviewPresenter.getSortedExamList(sortBy, orderBy);

        adapter = new ExamAdapter(getActivity(), this.examList, this);

        init();

        return view;
    }


    @Override
    public void init() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                overviewPresenter.getSortedExamList(sortBy, orderBy);
                swipeRefresh.setRefreshing(false);
            }
        });

        initSnackBars();

        setHasOptionsMenu(true);
    }

    @Override
    public void initSnackBars() {
        snackSuccesfulNewExam = Snackbar.make(examOverviewCoordinatorLayout, getResources().getText(R.string.exam_associated_succesful), Snackbar.LENGTH_LONG);
        snackSuccesfulNewExam.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        snackWrongACNewExam = Snackbar.make(examOverviewCoordinatorLayout, getResources().getText(R.string.exam_associated_fail), Snackbar.LENGTH_LONG);
        snackWrongACNewExam.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorError));
        snackInternalFailNewExam = Snackbar.make(examOverviewCoordinatorLayout,getResources().getText(R.string.exam_associated_internalfail),Snackbar.LENGTH_LONG);
        snackInternalFailNewExam.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorError));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        showOverflowMenu(true);
    }

    private void showOverflowMenu(boolean show) {
        if (menu == null) return;
        MenuItem actionSearchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(actionSearchItem);
        searchView.setOnQueryTextListener(getSearchViewListener());
        menu.setGroupVisible(R.id.overview_group, show);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sortby:
                showSortByDialog();
                return true;
            case R.id.action_logout:
                loginPresenter.logout();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick(R.id.fab)
    public void showNexExamDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_add_exam)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.create().cancel();
                    }
                })
                .setPositiveButton(R.string.find, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button findbtn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        findbtn.setOnClickListener(new NewExamDialogListener(this,alertDialog));
    }

    @Override
    public void showSortByDialog() {
        SortByDialogFragment sortByDialogFragment = new SortByDialogFragment();
        sortByDialogFragment.setTargetFragment(this, 0);
        sortByDialogFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void sortExamListBy(String orderBy, String sortBy) {
        this.sortBy = sortBy;
        this.orderBy = orderBy;
        overviewPresenter.getSortedExamList(sortBy, orderBy);
    }

    @Override
    public void onSuccessExamList(ArrayList<Exam> exams) {
        this.examList = exams;
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        adapter.addAllExams(this.examList);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailureExamList() {
        failText.setText(R.string.exam_overview_failed);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessFindNewExam(AssociatedExamResponse associatedExamResponse) {
        if (associatedExamResponse.getCode().equalsIgnoreCase("exam_and_account_associated")) {
            snackSuccesfulNewExam.show();
            overviewPresenter.getSortedExamList(sortBy, orderBy);
        } else if (associatedExamResponse.getCode().equalsIgnoreCase("exam_not_found_or_wrong_access_code")) {
            snackWrongACNewExam.show();
        }
    }

    @Override
    public void onFailureFindNewExam() {
        snackInternalFailNewExam.show();
    }

    @Override
    public void navigateToExamDetail(Exam exam) {
        Intent intent = new Intent(getContext(), ExamTabActivity.class);
        intent.putExtra("exam", exam);
        startActivity(intent);
    }

    @Override
    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sp = sharedPreferences;
    }

    @Override
    public void setLoginPresenter(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    @Override
    public SearchView.OnQueryTextListener getSearchViewListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                query = query.toLowerCase();

                final List<Exam> filteredList = new ArrayList<>();

                for (int i = 0; i < examList.size(); i++) {

                    final String patientName = examList.get(i).getPatient().toLowerCase();
                    final String examId = examList.get(i).getIdentification().toLowerCase();
                    final String clinicName = examList.get(i).getClinicName().toLowerCase();
                    final String date = examList.get(i).getExecutionDate().toLowerCase();
                    if (patientName.contains(query) || examId.contains(query) || clinicName.contains(query) || date.contains(query)) {
                        filteredList.add(examList.get(i));
                    }
                }

                adapter.addAllExams(filteredList);
                adapter.notifyDataSetChanged();
                return true;

            }

            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        };

    }


    @Override
    public void onNewExamInformationRetrieved(String identification, String accessCode) {
        overviewPresenter.findNewExam(identification, accessCode);
    }

    @Override
    public void onStop() {
        super.onDestroy();
    }

}
