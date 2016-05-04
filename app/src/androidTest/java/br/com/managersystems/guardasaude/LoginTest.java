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
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.not;

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
    public void shouldShowTextInputs(){
        onView(withId(R.id.gs_usernameWrapper)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.gs_login_username)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.gs_passwordWrapper)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.gs_login_password)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayLoginButton(){
        onView(withId(R.id.gs_login_btn)).check(ViewAssertions.matches(isDisplayed())).check(ViewAssertions.matches(isClickable()));
    }

    @Test
    public void shouldShowUnsuccesfulLoginResultWithWrongPassword() {
        onView(withId(R.id.gs_login_username)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_password)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_username)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_multi_role_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_wrong_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(ViewActions.click());
        onView(withId(R.id.gs_login_progress_text)).check(ViewAssertions.matches(not(withText(R.string.login_succes))));
        Log.d(getClass().getSimpleName(), progressText.getText().toString());
    }

    @Test
    public void shouldShowUnsuccesfulLoginResultWithWrongUser() {

        onView(withId(R.id.gs_login_username)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_password)).perform(ViewActions.clearText());
        onView(withId(R.id.gs_login_username)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_wrong_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(ViewActions.typeText(login.getActivity().getText(R.string.test_multi_role_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(ViewActions.click());
        onView(withId(R.id.gs_login_progress_text)).check(ViewAssertions.matches(not(withText(R.string.login_succes))));
        Log.d(getClass().getSimpleName(), progressText.getText().toString());


    }

    @Test
    public void shouldShowDialogWithMultipleRoles() {

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
    public void shouldShowExamOverviewAfterSuccesfulRoleSelection() {
        //TODO Change Dialog to Seperate Class
        //**** FAILING ON PURPOSE ****
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
        onView(withId(R.id.gs_role_choose_title)).check(ViewAssertions.matches(isDisplayed()));
        fail();
    }

    @Test
     public void shouldShowExamOverviewAfterSuccesfulSingeRoleLogin() {
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
    }
}
