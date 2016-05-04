package br.com.managersystems.guardasaude.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.exammenu.images.GridViewImageAdapter;
import br.com.managersystems.guardasaude.exams.exammenu.images.IImagesView;
import br.com.managersystems.guardasaude.exams.exammenu.images.ImagesPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ImagesFragment extends Fragment implements IImagesView {
    private GridView gridView;
    private Activity activity;
    private ImagesPresenter imagesPresenter;
    private GridViewImageAdapter adapter;
    private SharedPreferences sharedPreferences;
    private View view;
    private static final int GRID_PADDING = 1;
    private static final int NUM_OF_COLUMNS = 2;
    private int columnWidth;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.activity = getActivity();

        view = inflater.inflate(R.layout.fragment_images, container, false);
        ButterKnife.bind(this, view);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        gridView = (GridView)view.findViewById(R.id.grid_view);

        initializeGridLayout();

        imagesPresenter = new ImagesPresenter(this,sharedPreferences);

        imagesPresenter.retrieveExam(getActivity().getIntent());

        return view;
    }

    @Override
    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void noImagesFound(){
        progressBar.setVisibility(View.GONE);
        TextView failText = (TextView)view.findViewById(R.id.imagesFail);
        failText.setText(R.string.noImages);
    }

    public void imagesReceivedSucces() {
        adapter = new GridViewImageAdapter(activity, imagesPresenter.getImagesForExam(), columnWidth);
        gridView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void initializeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((getScreenWidth() - ((NUM_OF_COLUMNS + 1) * padding)) / NUM_OF_COLUMNS);

        gridView.setNumColumns(NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

    @Override
    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) {
            // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
}
