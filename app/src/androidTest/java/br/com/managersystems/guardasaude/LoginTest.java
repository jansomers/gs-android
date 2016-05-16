package br.com.managersystems.guardasaude;


import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.managersystems.guardasaude.ui.activities.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    TextView progressText;

    @Rule
    public final ActivityTestRule<LoginActivity> login = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Before
    public void init() {
        //Binding views for logging purposes
        progressText = (TextView) login.getActivity().findViewById(R.id.gs_login_progress_text);
    }


    @Test
    public void showsTextInputs(){
        onView(withId(R.id.gs_username_wrapper)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.gs_login_username)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.gs_passwordWrapper)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.gs_login_password)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void showsLoginButton(){
        onView(withId(R.id.gs_login_btn)).check(ViewAssertions
                .matches(isDisplayed())).check(ViewAssertions.matches(isClickable()));
    }

    @Test
    public void showsUnsuccesfulLoginResultWithWrongPassword() {
        onView(withId(R.id.gs_login_username)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_password)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_username)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_multi_role_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_wrong_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(ViewActions.click());
        onView(withId(R.id.gs_login_progress_text)).check(ViewAssertions.matches(not(withText(R.string.login_success))));
        Log.d(getClass().getSimpleName(), progressText.getText().toString());
    }

    @Test
    public void showsUnsuccesfulLoginResultWithWrongUser() {

        onView(withId(R.id.gs_login_username)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_password)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_username)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_wrong_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_multi_role_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(ViewActions.click());
        onView(withId(R.id.gs_login_progress_text)).check(ViewAssertions.matches(not(withText(R.string.login_success))));
        Log.d(getClass().getSimpleName(), progressText.getText().toString());


    }

    @Test
    public void showsDialogWithMultipleRoles() {

        try {
            onView(withId(R.id.gs_role_choose_title)).check(ViewAssertions.matches(not(isDisplayed())));
            fail();
        } catch (NoMatchingViewException e) {
            assert true;
        }
        onView(withId(R.id.gs_login_username)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_password)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_username)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_multi_role_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_multi_role_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(ViewActions.click());
        onView(withId(R.id.gs_role_choose_title)).check(ViewAssertions.matches(isDisplayed()));

    }

    @Test
     public void showsExamOverviewAfterSuccesfulSingeRoleLogin() {
        try {
            onView(withId(R.id.gs_role_choose_title)).check(ViewAssertions.matches(not(isDisplayed())));
            fail();
        } catch (NoMatchingViewException e) {
            assert true;
        }
        onView(withId(R.id.gs_login_username)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_password)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_username)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_single_role_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_single_role_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(ViewActions.click());
        onView(withId(R.id.maintablayout)).check(ViewAssertions.matches(isDisplayed()));
    }
}
