package br.com.managersystems.guardasaude.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.exammenu.images.GridViewImageAdapter;
import br.com.managersystems.guardasaude.exams.exammenu.images.IImagesView;
import br.com.managersystems.guardasaude.exams.exammenu.images.ImagesPresenter;
import butterknife.ButterKnife;

public class ImagesFragment extends Fragment implements IImagesView {
    private GridView gridView;
    private Activity activity;
    private ImagesPresenter imagesPresenter;
    private GridViewImageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.activity = getActivity();

        View view = inflater.inflate(R.layout.fragment_images, container, false);
        ButterKnife.bind(this, view);

        gridView = (GridView)view.findViewById(R.id.grid_view);

        imagesPresenter = new ImagesPresenter(this,gridView);

        adapter = new GridViewImageAdapter(activity, imagesPresenter.getImagesForExam(), imagesPresenter.getColumnWidth());

        gridView.setAdapter(adapter);

        return view;
    }


}
