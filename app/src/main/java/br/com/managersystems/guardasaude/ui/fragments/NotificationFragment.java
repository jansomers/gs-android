package br.com.managersystems.guardasaude.ui.fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.exams.mainmenu.notifications.INotificationView;
import br.com.managersystems.guardasaude.exams.mainmenu.notifications.INotificationPresenter;
import br.com.managersystems.guardasaude.exams.mainmenu.notifications.NotificationPresenter;
import butterknife.ButterKnife;

public class NotificationFragment extends Fragment implements INotificationView {
    INotificationPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, view);

        presenter = new NotificationPresenter(this);
        notificationsClicked();
        return view;
    }

    @Override
    public void notificationsClicked() {
        presenter.attemptFetchDummyData();
    }

    @Override
    public void dummyDataSucces(String[] dummyData) {
        for (String dum : dummyData) {
//           Toast.makeText(getContext(),dum,Toast.LENGTH_LONG).show();
        }
    }

}
