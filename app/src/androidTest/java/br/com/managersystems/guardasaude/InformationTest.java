package br.com.managersystems.guardasaude;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;

import br.com.managersystems.guardasaude.exams.exammenu.ExamTabsPagerAdapter;
import br.com.managersystems.guardasaude.ui.activities.ExamTabActivity;
import br.com.managersystems.guardasaude.ui.fragments.InformationFragment;

@RunWith(AndroidJUnit4.class)
public class InformationTest {

    ActivityTestRule<ExamTabActivity> activity = new ActivityTestRule<ExamTabActivity>(ExamTabActivity.class);
    ExamTabsPagerAdapter examTabsPagerAdapter;
    InformationFragment informationFragment;

    @Before
    public void init() {
        examTabsPagerAdapter = activity.getActivity().getExamTabsPagerAdapter();
        informationFragment = (InformationFragment) examTabsPagerAdapter.getItem(0);
    }
    /*@Test
    public void shouldDisplayInformationFragment(){
        onView(withId(R.id.hideable_information_layout)).check(ViewAssertions.matches(isDisplayed()));
    }*/


}
