package br.com.managersystems.guardasaude;

import android.app.Instrumentation;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.widget.EditText;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.managersystems.guardasaude.ui.activities.LoginActivity;
import br.com.managersystems.guardasaude.ui.activities.MainTabActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ExamOverviewTest {

    @ClassRule
    public static final ActivityTestRule<LoginActivity> login = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Rule
    public final ActivityTestRule<MainTabActivity> mainTab = new ActivityTestRule<MainTabActivity>(MainTabActivity.class);

    @BeforeClass
    public static void login(){
        login.getActivity();
        onView(withId(R.id.gs_login_username)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_password)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_username)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_single_role_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_single_role_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(ViewActions.click());
    }

    @Before
    public void init(){
        mainTab.getActivity();
    }

    @Test
    public void checkTab(){
        onView(withText("Exams")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void shouldAddExamToExamList() throws InterruptedException {
        onView(withId(R.id.fab)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.fab)).check(ViewAssertions.matches(isClickable()));

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.new_exam_identification)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_id).toString()));
        onView(withId(R.id.new_exam_accesscode)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_accesscode).toString()));
        onView(withId(android.R.id.button1)).perform(click());
        Thread.sleep(100);
    }

    @Test
    public void shouldNotAddExamToExamListWrongAccessCode() throws InterruptedException {
        onView(withId(R.id.fab)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.fab)).check(ViewAssertions.matches(isClickable()));

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.new_exam_identification)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_id).toString()));
        onView(withId(R.id.new_exam_accesscode)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_accesscode_wrong).toString()));
        onView(withId(android.R.id.button1)).perform(click());

        Thread.sleep(100);
    }

    @Test
    public void shouldSearchForExamsWithText() throws InterruptedException {
        onView(withId(R.id.action_search)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.action_search)).check(ViewAssertions.matches(isFocusable()));

        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("test"), pressKey(KeyEvent.KEYCODE_ENTER));
        Thread.sleep(100);
    }

    @Test
    public void shouldSortExamsOnIdentificationDesc() throws InterruptedException {
        onView(withId(R.id.action_sortby)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.action_sortby)).check(ViewAssertions.matches(isClickable()));

        onView(withId(R.id.action_sortby)).perform(click());
        onView(withId(R.id.action_sortby)).perform(click());

        onView(withId(R.id.identification_button)).perform(click());
        onView(withId(R.id.identification_button)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        Thread.sleep(100);
    }

    @Test
    public void shouldSwipeLeft() throws InterruptedException {
        onView(withId(R.id.maintablayout)).perform(swipeLeft());
        Thread.sleep(100);
    }
}
