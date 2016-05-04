package br.com.managersystems.guardasaude.exams.mainmenu.notifications;


import br.com.managersystems.guardasaude.dummydata.DummyDataInteractor;
import br.com.managersystems.guardasaude.ui.fragments.NotificationFragment;

public class NotificationPresenter implements INotificationPresenter,OnFinishListener {

    DummyDataInteractor dummyDataInteractor;
    INotificationView notificationView;

    public NotificationPresenter(NotificationFragment notificationFragment) {
        dummyDataInteractor = new DummyDataInteractor();
        notificationView = notificationFragment;
    }

    @Override
    public void attemptFetchDummyData() {
        dummyDataInteractor.getDummyData(this);
    }


    @Override
    public void onSucces(String[] dummyData) {
        notificationView.dummyDataSucces(dummyData);
    }
}
