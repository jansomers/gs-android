package br.com.managersystems.guardasaude;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.EditText;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.managersystems.guardasaude.ui.activities.LoginActivity;
import br.com.managersystems.guardasaude.ui.activities.MainTabActivity;
import br.com.managersystems.guardasaude.util.RecyclerViewMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ExamOverviewTest {

    boolean overViewLoaded = false;

    @ClassRule
    public static final ActivityTestRule<LoginActivity> login = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Rule
    public final ActivityTestRule<MainTabActivity> mainTab = new ActivityTestRule<MainTabActivity>(MainTabActivity.class);

    @BeforeClass
    public static void login() {
        login.getActivity();
        onView(withId(R.id.gs_login_username)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_password)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_username)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_single_role_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_single_role_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(ViewActions.click());
    }

    @Before
    public void init() {
        mainTab.getActivity();
    }

    @Test
    public void testTab() {
        onView(withText("Exams")).check(matches(isDisplayed()));
    }

    @Test
    public void checkRefresh() throws InterruptedException {
        onView(withId(R.id.swipeRefreshLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldAddExamToExamList() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        onView(withId(R.id.fab)).check(matches(isClickable()));

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.new_exam_identification)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_id).toString()));
        onView(withId(R.id.new_exam_access_code)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_accesscode).toString()));
        onView(withId(R.id.ass_exam_oke_btn)).perform(click());
        onView(withRecyclerView(R.id.examOverviewList).atPositionOnView(0, R.id.exam_id)).check(matches(withText(login.getActivity().getText(R.string.test_add_exam_id).toString())));
    }

    @Test
    public void shouldNotAddExamToExamListWrongAccessCode() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        onView(withId(R.id.fab)).check(matches(isClickable()));

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.new_exam_identification)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_id).toString()));
        onView(withId(R.id.new_exam_access_code)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_add_exam_accesscode_wrong).toString()));
        onView(withId(R.id.ass_exam_oke_btn)).perform(click());
    }


    @Test
    public void shouldSearchForExamsWithText() throws InterruptedException {
        onView(withId(R.id.action_search)).check(matches(isDisplayed()));
        onView(withId(R.id.action_search)).check(matches(isFocusable()));

        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(login.getActivity().getText(R.string.test_add_exam_id).toString()));
        pressKey(KeyEvent.KEYCODE_ENTER);
        onView(withRecyclerView(R.id.examOverviewList).atPositionOnView(0, R.id.exam_id)).check(matches(withText(login.getActivity().getText(R.string.test_add_exam_id).toString())));
    }




    @Test
    public void shouldSortExamsOnIdentification() {
        onView(withId(R.id.action_sortby)).check(matches(isDisplayed()));
        onView(withId(R.id.action_sortby)).check(matches(isClickable()));

        onView(withId(R.id.action_sortby)).perform(click());
        onView(withId(R.id.action_sortby)).perform(click());

        onView(withId(R.id.identification_button_layout)).perform(click());
        onView(withId(R.id.identification_button_layout)).perform(click());
        onView(withId(R.id.sort_exam_oke_btn)).perform(click());

        onView(withRecyclerView(R.id.examOverviewList).atPositionOnView(0, R.id.exam_id)).check(matches(withText(login.getActivity().getText(R.string.test_add_exam_id).toString())));
    }

    @Test
    public void checkRecyclerView() throws InterruptedException {
        onView(withId(R.id.examOverviewList)).check(matches(isDisplayed()));
        while (!overViewLoaded)
        {
            RecyclerView examOverview = (RecyclerView) mainTab.getActivity().findViewById(R.id.examOverviewList);
            if (examOverview.getAdapter().getItemCount() == 0) {
                overViewLoaded = false;
            } else overViewLoaded = true;
        }

        //Sort on ID DESC
        onView(withId(R.id.action_sortby)).perform(click());
        onView(withId(R.id.identification_button_layout)).perform(click());
        onView(withId(R.id.identification_button_layout)).perform(click());
        onView(withId(R.id.sort_exam_oke_btn)).perform(click());

        Thread.sleep(1500);

        //LOOKUP GKS0004
        onView(withRecyclerView(R.id.examOverviewList).atPositionOnView(0, R.id.exam_id)).check(matches(withText(login.getActivity().getText(R.string.test_add_exam_id).toString())));
        onView(withRecyclerView(R.id.examOverviewList).atPositionOnView(0, R.id.status_text)).check(matches(withText("W")));
        onView(withRecyclerView(R.id.examOverviewList).atPositionOnView(0, R.id.patient_name)).check(matches(withText("John Smith")));
        onView(withRecyclerView(R.id.examOverviewList).atPositionOnView(0, R.id.status_icon)).check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.examOverviewList).atPositionOnView(0, R.id.clinic_name)).check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.examOverviewList).atPositionOnView(0, R.id.execution_date)).check(matches(isDisplayed()));

        //GOTO GKS0004
        onView(withRecyclerView(R.id.examOverviewList).atPosition(0)).perform(click());
    }

    @Test
    public void shouldShowActionGroup() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Logout")).check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

}
