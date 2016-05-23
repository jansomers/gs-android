package br.com.managersystems.guardasaude.ui.fragments;


import android.app.AlertDialog;
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
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.EndlessRecyclerViewScrollListener;
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
    @Bind(R.id.gs_exam_images_progress_bar)
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
    private Snackbar snackSuccessfulNewExam;
    private Snackbar snackWrongACNewExam;
    private Snackbar snackInternalFailNewExam;
    private Snackbar snackAlreadyAssociatedExam;
    private LinearLayoutManager llm;

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

        adapter = new ExamAdapter(getActivity(), this.examList, this);

        init();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Exam> exams = adapter.getExamList();
        adapter.removeAll();
        adapter.addAllExams(exams);
    }

    @Override
    public void init() {
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        overviewPresenter = new ExamOverviewPresenter(this, sp);
        overviewPresenter.getFirstSortedExamList(sortBy, orderBy);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                overviewPresenter.getNextSortedExamList(sortBy, orderBy, String.valueOf(page));
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                overviewPresenter.getFirstSortedExamList(sortBy, orderBy);
                swipeRefresh.setRefreshing(false);
            }
        });

        initSnackBars();

        failText.setVisibility(View.INVISIBLE);

        setHasOptionsMenu(true);
    }

    @Override
    public void initSnackBars() {
        snackSuccessfulNewExam = Snackbar.make(examOverviewCoordinatorLayout, getResources().getText(R.string.exam_associated_succesful), Snackbar.LENGTH_LONG);
        snackSuccessfulNewExam.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        snackAlreadyAssociatedExam = Snackbar.make(examOverviewCoordinatorLayout, getResources().getText(R.string.exam_already_associated), Snackbar.LENGTH_LONG);
        snackAlreadyAssociatedExam.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        snackWrongACNewExam = Snackbar.make(examOverviewCoordinatorLayout, getResources().getText(R.string.exam_associated_fail), Snackbar.LENGTH_LONG);
        snackWrongACNewExam.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorError));
        snackInternalFailNewExam = Snackbar.make(examOverviewCoordinatorLayout, getResources().getText(R.string.exam_associated_internalfail), Snackbar.LENGTH_LONG);
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
        searchView.setSubmitButtonEnabled(true);
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
    public void showNewExamDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_add_exam);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button findBtn = (Button) alertDialog.findViewById(R.id.ass_exam_oke_btn);
        findBtn.setOnClickListener(new NewExamDialogListener(this, alertDialog));
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
        overviewPresenter.getFirstSortedExamList(sortBy, orderBy);
    }

    @Override
    public void showExamList(ArrayList<Exam> exams) {
        progressBar.setVisibility(View.GONE);
        failText.setVisibility(View.GONE);
        this.examList = exams;
        adapter.addAllExams(this.examList);
    }

    @Override
    public void showNextExamList(ArrayList<Exam> exams) {
        progressBar.setVisibility(View.GONE);
        failText.setVisibility(View.GONE);
        this.examList.addAll(exams);
        int curSize = adapter.getItemCount();
        adapter.addAllExams(examList);
        adapter.notifyItemRangeInserted(curSize, examList.size() - 1);
    }

    @Override
    public void showLoadingExamsError() {
        failText.setVisibility(View.VISIBLE);
        failText.setText(R.string.exam_overview_failed);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessFindNewExam(AssociatedExamResponse associatedExamResponse) {
        if (associatedExamResponse.getCode().equalsIgnoreCase("exam_and_account_associated")) {
                snackSuccessfulNewExam.show();
                overviewPresenter.getFirstSortedExamList(sortBy, orderBy);
        } else if (associatedExamResponse.getCode().equalsIgnoreCase("exam_not_found_or_wrong_access_code")) {
            snackWrongACNewExam.show();
        }else if(associatedExamResponse.getCode().equalsIgnoreCase("access_already_exists")){
            snackAlreadyAssociatedExam.show();
        }
    }

    @Override
    public void showInternalFailForNewExam() {
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
        return new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                overviewPresenter.getFilteredExamList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                overviewPresenter.getFirstSortedExamList(sortBy,orderBy);
                return false;
            }
        };
    /* !!! OLD WAY !!!
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
        */
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
