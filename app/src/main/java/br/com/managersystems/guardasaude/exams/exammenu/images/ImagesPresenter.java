package br.com.managersystems.guardasaude.exams.exammenu.images;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.managersystems.guardasaude.exams.domain.Exam;
import br.com.managersystems.guardasaude.exams.domain.ExamImage;
import br.com.managersystems.guardasaude.ui.fragments.ImagesFragment;
import br.com.managersystems.guardasaude.util.Base64Interactor;
import okhttp3.ResponseBody;

public class ImagesPresenter implements IImagesPresenter,OnImagesRetrievedListener {
    private ImagesFragment imagesFragment;
    private ImagesInteractor interactor;
    private GridView gridView;
    private static final int GRID_PADDING = 1;
    private static final int NUM_OF_COLUMNS = 2;
    private int columnWidth;
    Base64Interactor base64Interactor;
    private Exam exam;
    SharedPreferences sp;
    List<ResponseBody> imagesFiles = new ArrayList<>();


    public ImagesPresenter(ImagesFragment imagesFragment, GridView gridView, SharedPreferences sharedPreferences) {
        this.sp = sharedPreferences;
        base64Interactor = new Base64Interactor();
        this.imagesFragment = imagesFragment;
        this.interactor = new ImagesInteractor(this);
        this.gridView = gridView;
        InitializeGridLayout();
    }

    @Override
    public void InitializeGridLayout() {
        Resources r = imagesFragment.getResources();
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
        WindowManager wm = (WindowManager) imagesFragment.getContext().getSystemService(Context.WINDOW_SERVICE);
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



    @Override
    public ArrayList<Bitmap> getImagesForExam() {
        final ArrayList<Bitmap> imageItems = new ArrayList<>();

        if(imagesFiles.size()<=0){
            imagesFragment.noImagesFound();
        }
        else{
        for(ResponseBody response: imagesFiles){
           // imageItems.add(BitmapFactory.decodeByteArray(response.getImage(),0,response.getImage().length));
        }
        }

        return imageItems;
        /*
        TypedArray imgs = imagesFragment.getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(imagesFragment.getResources(), imgs.getResourceId(i, -1));
            imageItems.add(scaleImage(bitmap));
        }
        */
    }

    @Override
    public Bitmap scaleImage(Bitmap bitmap) {
        int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
        return Bitmap.createScaledBitmap(bitmap, 512, nh, true);
    }

    @Override
    public int getColumnWidth() {
        return columnWidth;
    }

    @Override
    public void retrieveExam(Intent intent) {
        interactor.getExam(intent);
    }

    @Override
    public void retrieveImages() {
        byte [] encryptedUser =  sp.getString("user",null).getBytes();
        String user= base64Interactor.decodeBase64ToString(encryptedUser);
        String token = sp.getString("token",null);
        for(ExamImage examImage: exam.getImages()) {
            interactor.getExamImage(user, token, examImage.getExamIdentification(), examImage.getImageIdentification());
        }
    }


    @Override
    public void onExamFailure() {
        Log.d(getClass().getSimpleName(), "Received interactor failure.. ");
        //TODO SHOW ERROR

    }
    @Override
    public void onFailure() {
        Log.d(getClass().getSimpleName(), "Received interactor failure.. ");
        Toast.makeText(imagesFragment.getContext(), "fkjfdsklqfklsd", Toast.LENGTH_LONG).show();
        //TODO Show error
    }

    @Override
    public void onImageFailure() {
        Log.d(getClass().getSimpleName(), "Received interactor failure.. ");
        //TODO SHOW ERROR
    }

    @Override
    public void onExamReceived(Exam exam) {
        this.exam = exam;
        retrieveImages();        
        imagesFragment.examReceivedSucces();
    }

    @Override
    public void onImageSuccess(ResponseBody response) {
        imagesFiles.add(response);
    }
}
