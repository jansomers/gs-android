package br.com.managersystems.guardasaude.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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
    @Bind(R.id.gs_exam_images_progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.gs_exam_images_grid_view)
    GridView gridView;

    @Bind(R.id.gs_exam_images_error_text)
    TextView failText;

    private ImagesPresenter imagesPresenter;
    private SharedPreferences sharedPreferences;
    private View view;
    private static final int GRID_PADDING = 1;
    private static final int NUM_OF_COLUMNS = 2;
    private int columnWidth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_images, container, false);
        ButterKnife.bind(this, view);

        progressBar.setVisibility(View.VISIBLE);
        failText.setVisibility(View.GONE);

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
        failText.setVisibility(View.VISIBLE);
        failText.setText(R.string.noImages);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void imagesReceivedSuccess() {
        failText.setVisibility(View.GONE);
        gridView.setAdapter(new GridViewImageAdapter(getActivity(), imagesPresenter.getImagesForExam(), columnWidth));
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void initializeGridLayout() {
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GRID_PADDING, getResources().getDisplayMetrics());

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
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) {
            // Older device
            point.x = displaymetrics.widthPixels;
            point.y = displaymetrics.heightPixels;
        }
        columnWidth = point.x;
        return columnWidth;
    }
}
