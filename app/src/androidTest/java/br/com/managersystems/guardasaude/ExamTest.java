package br.com.managersystems.guardasaude;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import br.com.managersystems.guardasaude.ui.activities.LoginActivity;
import br.com.managersystems.guardasaude.ui.activities.MainTabActivity;
import br.com.managersystems.guardasaude.util.TestUtils;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.not;



@RunWith(AndroidJUnit4.class)
public class ExamTest {

    private static final String TEST_USER = "doctor2";
    private static final String TEST_PASSWORD = "Admin1";
    private static final int MAX_LOAD_TIME_IN_MS = 1000;
    private static final String PROFESSIONAL = "ROLE_HEALTH_PROFESSIONAL";
    private static final String PATIENT = "ROLE_PATIENT";
    private Activity currentActivity;
    private boolean overViewLoaded = false;
    private boolean imagesLoaded = false;

    @ClassRule
    public static final ActivityTestRule<LoginActivity> login = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Rule
    public  final ActivityTestRule<MainTabActivity> mainTab = new ActivityTestRule<MainTabActivity>(MainTabActivity.class);


    @BeforeClass
    public static void onceExecutedBeforeAll() {
        login.getActivity();
        onView(withId(R.id.gs_login_username)).perform(ViewActions.clearText()).perform(ViewActions.typeText(TEST_USER));
        onView(withId(R.id.gs_login_password)).perform(ViewActions.clearText()).perform(ViewActions.typeText(TEST_PASSWORD));
        onView(withId(R.id.gs_login_btn)).perform(click());
        onView(withId(R.id.gs_maintab_activity_layout)).check(matches(isDisplayed()));

    }


    @Before
    public void beforeEachTest() {
        mainTab.getActivity();
        while (!overViewLoaded) {
            RecyclerView examOverview = (RecyclerView) mainTab.getActivity().findViewById(R.id.examOverviewList);
            if(examOverview!=null){
                if(examOverview.getAdapter()!=null){
                    overViewLoaded = examOverview.getAdapter().getItemCount() != 0;
                }
            }
        }
        onView(withId(R.id.examOverviewList)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }
    @Test
    public void shouldDisplayInformationFragment(){
        onView(withId(R.id.fragment_information_layout)).check(matches(isDisplayed()));

    }

    @Test
    public void shouldShowsActionGroup() {
    openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Logout")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

    @Test
    public void showsExamId() {
        onView(withId(R.id.gs_exam_information_exam_id))
                .check(matches(not(withText
                        (mainTab.getActivity().getResources().getText(R.string.exam_id).toString()))));
    }

    @Test
    public void showsExamType() {
        onView(withId(R.id.gs_exam_information_exam_type))
                .check(matches(not(withText
                        (mainTab.getActivity().getResources().getText(R.string.exam_type).toString()))));
    }

    @Test
    public void showsClinicId() {
        onView(withId(R.id.gs_exam_information_clinic_id))
                .check(matches(not(withText
                        (mainTab.getActivity().getResources().getText(R.string.exam_clinic).toString()))));
    }

    @Test
    public void showsPatientName() {
        onView(withId(R.id.gs_exam_information_patient))
                .check(matches(not(withText
                        (mainTab.getActivity().getResources().getText(R.string.exam_patient).toString()))));

    }

    @Test
    public void showsReportingPhys() {
        onView(withId(R.id.gs_exam_information_exam_reporting_phys))
                .check(matches(not(withText
                        (mainTab.getActivity().getResources().getText(R.string.exam_reporting_phys).toString()))));
    }

    @Test
    public void showsCommentButtonToProf() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mainTab.getActivity().getApplicationContext());
        String role = preferences.getString("role", "");

        if (role.equals(PROFESSIONAL)) {
            onView(withId(R.id.comments_btn)).
                    check(matches(isDisplayed()));
        }
        else {
            onView(withId(R.id.comments_btn)).
                    check(matches(not(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))).
                    check(matches(not(isClickable())));

        }
    }

    @Test
    public void showsImagesButton() {
        onView(withId(R.id.images_btn)).check(matches(isDisplayed()));
    }

    @Test
    public void showsDocumentsButton() {
        onView(withId(R.id.documents_btn)).check(matches(isDisplayed()));
    }

    @Test
    public void commentLayoutIsHiddenAtStart(){
        onView(withId(R.id.gs_exam_comment_section_layout)).check(matches(not(isDisplayed())));
    }

    @Test
    public void commentLayoutIsShownWhenButtonIsClicked() {
        onView(withId(R.id.comments_btn)).perform(ViewActions.click());
        onView(withId(R.id.gs_exam_comment_section_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void informationIsHiddenWhenButtonsAreShown() {
        onView(withId(R.id.comments_btn)).perform(ViewActions.click());
        onView(withId(R.id.hideable_information_layout)).check(matches(not(isDisplayed())));
    }

    @Test
    public void commentsAreShownInLayoutSection() {
        final int MAX_TRIES = 50;
        int tries = 0;
        boolean areCommentsLoaded = false;
        RecyclerView commentList = (RecyclerView) getActivityInstance().findViewById(R.id.gs_exam_comment_recycler_view);
        onView(withId(R.id.comments_btn)).perform(ViewActions.click());
        onView(withId(R.id.gs_exam_comment_section_layout)).check(matches(isDisplayed()));
        while (!(tries > MAX_TRIES) && !areCommentsLoaded) {
            if (commentList.getAdapter() != null) {
                {
                    if (commentList.getAdapter().getItemCount() == 0) {
                        areCommentsLoaded = false;
                    } else areCommentsLoaded = true;
                    tries++;
                }
            }
                else {
                    onView(withId(R.id.comments_btn)).perform(ViewActions.click());
                    onView(withId(R.id.comments_btn)).perform(ViewActions.click());
                }

        }
        if (areCommentsLoaded) onView(withId(R.id.gs_exam_comment_recycler_view)).check(matches(TestUtils.atPosition(0, withId(R.id.comment_item_layout))));
    }

    @Test
    public void showsReportAfterRightSwipe() {
        onView(withId(R.id.gs_maintab_activity_layout)).perform(swipeLeft());
        onView(withId(R.id.fragment_report_layout)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void showsReport(){
        onView(withId(R.id.gs_maintab_activity_layout)).perform(swipeLeft());
        onView(withId(R.id.report_test_textview)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void showsInformationAfterLeftSwipe() {
        onView(withId(R.id.gs_maintab_activity_layout)).perform(swipeRight());
        onView(withId(R.id.hideable_information_layout)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void showsImageFragmentAfterButtonClick() {
        onView(withId(R.id.images_btn)).perform(click());
        onView(withId(R.id.gs_images_fragment_layout)).check(matches(isDisplayed()));

    }

    /**
     * Gets the current activity that the screen is on
     * @return Activity object representing the current activity
     */
    public Activity getActivityInstance(){
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }

}
