package br.com.managersystems.guardasaude;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.managersystems.guardasaude.ui.activities.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class AnonymousExamTest {
    @Rule
    public final ActivityTestRule<LoginActivity> login = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void OpenDialog() {
        onView(withId(R.id.gs_login_btn_anonymous_exam)).perform(ViewActions.click());
    }

    @Test
    public void shouldNotShowExamWrongAccessCode() throws InterruptedException {
        onView(withId(R.id.gs_anonymous_exam_add_exam_id)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_id).toString()));
        onView(withId(R.id.gs_anonymous_exam_add_access_code)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_accesscode_wrong).toString()));
        onView(withId(R.id.gs_anonymous_exam_btn_find)).perform(ViewActions.click());
        onView(withText(login.getActivity().getText(R.string.test_add_exam_id).toString()));
        //Allow time for snackbar to be displayed
        Thread.sleep(500);
        pressBack();
        pressBack();
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.exam_associated_internalfail))).check(matches(isDisplayed()));
    }

    @Test
    public void showsAnonymousExamInformationCorrectAccessCode(){
        onView(withId(R.id.gs_anonymous_exam_add_exam_id)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_id).toString()));
        onView(withId(R.id.gs_anonymous_exam_add_access_code)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_accesscode).toString()));
        onView(withId(R.id.gs_anonymous_exam_btn_find)).perform(ViewActions.click());
        onView(withId(R.id.fragment_anonymous_information)).check(matches(isDisplayed()));
        onView(withId(R.id.gs_anon_exam_information_exam_id)).check(matches(withText(login.getActivity().getText(R.string.test_add_exam_id).toString())));
        onView(withId(R.id.gs_anon_exam_information_patient)).check(matches(withText(login.getActivity().getText(R.string.test_anon_patient).toString())));
        onView(withId(R.id.gs_anon_exam_information_exam_reporting_phys)).check(matches(withText(login.getActivity().getText(R.string.test_anon_reporting_phys).toString())));
        onView(withId(R.id.gs_anon_exam_information_status)).check(matches(isDisplayed()));
        onView(withId(R.id.anon_exam_string)).check(matches(isDisplayed()));
        onView(withId(R.id.anon_exam_clinic_icon)).check(matches(isDisplayed()));
        onView(withId(R.id.anon_exam_date_icon)).check(matches(isDisplayed()));
        onView(withId(R.id.anon_exam_phys_icon)).check(matches(isDisplayed()));
        onView(withId(R.id.anon_exam_patient_icon)).check(matches(isDisplayed()));
    }

    @Test
    public void showsCreateAccountButton(){
        onView(withId(R.id.gs_anonymous_exam_add_exam_id)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_id).toString()));
        onView(withId(R.id.gs_anonymous_exam_add_access_code)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_accesscode).toString()));
        onView(withId(R.id.gs_anonymous_exam_btn_find)).perform(ViewActions.click());
    }
}
