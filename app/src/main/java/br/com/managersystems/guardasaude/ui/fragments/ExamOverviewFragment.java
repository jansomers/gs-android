package br.com.managersystems.guardasaude.ui.fragments;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
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
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.NewExamListener;
import br.com.managersystems.guardasaude.exams.mainmenu.examoverview.SortDialogListener;
import br.com.managersystems.guardasaude.login.LoginPresenter;
import br.com.managersystems.guardasaude.ui.activities.ExamTabActivity;
import br.com.managersystems.guardasaude.ui.dialogs.NewExamDialogFragment;
import br.com.managersystems.guardasaude.ui.dialogs.SortByDialogFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ExamOverviewFragment extends Fragment implements IExamOverview, SortDialogListener, NewExamListener {

    @Bind(R.id.examOverviewList)
    RecyclerView recyclerView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.failText)
    TextView failText;

    Menu menu;
    private ExamOverviewPresenter overviewPresenter;
    private LoginPresenter loginPresenter;
    private ExamAdapter adapter;
    private SharedPreferences sp;
    private List<Exam> examList = Collections.EMPTY_LIST;
    private SearchView.OnQueryTextListener listener;
    private SwipeRefreshLayout swipeRefresh;
    private String sortBy = null;
    private String orderBy=null;

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
        swipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        loginPresenter = new LoginPresenter(this.getActivity(), sp);
        overviewPresenter = new ExamOverviewPresenter(this, sp);
        overviewPresenter.getSortedExamList(sortBy,orderBy);
        adapter = new ExamAdapter(getActivity(), this.examList, this);

        init();
        return view;
    }

    private void init() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                overviewPresenter.getSortedExamList(sortBy,orderBy);
                swipeRefresh.setRefreshing(false);
            }
        });

        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        showOverflowMenu(true);
    }

    private void showOverflowMenu(boolean show) {
        if (menu == null) return;
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        initiateSearchViewListener();
        searchView.setOnQueryTextListener(listener);
        menu.setGroupVisible(R.id.overview_group, show);
    }

        public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sortby:
                showSortByDialog();
                return true;
            case R.id.action_logout:
                loginPresenter.logout();
            default: return super.onOptionsItemSelected(item);
        }
    }



    @OnClick(R.id.fab)
    public void showNexExamDialog() {
        NewExamDialogFragment newExamDialogFragment = new NewExamDialogFragment();
        newExamDialogFragment.setTargetFragment(this,0);
        newExamDialogFragment.show(getFragmentManager(), "NewExamDialog");
    }

    public void showSortByDialog(){
        SortByDialogFragment sortByDialogFragment = new SortByDialogFragment();
        sortByDialogFragment.setTargetFragment(this,0);
        sortByDialogFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void sortExamListBy(String orderBy,String sortBy){
        this.sortBy=sortBy;
        this.orderBy=orderBy;
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

    }

    @Override
    public void onFailureExamList() {
        failText.setText(R.string.exam_overview_failed);
    }

    @Override
    public void onSuccessFindNewExam(AssociatedExamResponse associatedExamResponse) {
        if(associatedExamResponse.getCode().equalsIgnoreCase("exam_and_account_associated")){
            Toast.makeText(getContext(),"Exam associated",Toast.LENGTH_LONG).show();
            overviewPresenter.getSortedExamList(sortBy,orderBy);
        }
        else if(associatedExamResponse.getCode().equalsIgnoreCase("exam_not_found_or_wrong_access_cide")){
            Toast.makeText(getContext(),"Wrong access code",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailureFindNewExam() {

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
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void initiateSearchViewListener() {
        listener =  new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                query = query.toLowerCase();

                final List<Exam> filteredList = new ArrayList<>();

                for (int i = 0; i < examList.size(); i++) {

                    final String patientName = examList.get(i).getPatient().toLowerCase();
                    final String examId = examList.get(i).getIdentification();
                    final String clinicName = examList.get(i).getClinicName();
                    final String date = examList.get(i).getExecutionDate();
                    if (patientName.contains(query) || examId.contains(query) || clinicName.contains(query)||date.contains(query)) {
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
