package br.com.managersystems.guardasaude.dummydata;

import br.com.managersystems.guardasaude.exams.mainmenu.notifications.OnFinishListener;

public class DummyDataInteractor {



    public void getDummyData(OnFinishListener onFinishListener) {
        onFinishListener.onSucces(DummyDatabase.dummyBrands());

    }
}
