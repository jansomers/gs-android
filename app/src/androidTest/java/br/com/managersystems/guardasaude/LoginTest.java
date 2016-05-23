package br.com.managersystems.guardasaude;


import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.managersystems.guardasaude.ui.activities.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class LoginTest {




    @Rule
    public final ActivityTestRule<LoginActivity> login = new ActivityTestRule<LoginActivity>(LoginActivity.class);


    @Test
    public void showsTextInputs(){
        onView(withId(R.id.gs_username_wrapper)).check(matches(isDisplayed()));
        onView(withId(R.id.gs_login_username)).check(matches(isDisplayed()));
        onView(withId(R.id.gs_passwordWrapper)).check(matches(isDisplayed()));
        onView(withId(R.id.gs_login_password)).check(matches(isDisplayed()));
    }

    @Test
    public void showsLoginButton(){
        onView(withId(R.id.gs_login_btn)).check(
                matches(isDisplayed())).check(matches(isClickable()));
    }

    @Test
    public void showsUnsuccesfulLoginResultWithWrongPassword() {
        onView(withId(R.id.gs_login_username)).perform(clearText());
        onView(withId(R.id.gs_login_password)).perform(clearText());
        onView(withId(R.id.gs_login_username)).perform(typeText(login.getActivity().getText(R.string.test_multi_role_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(typeText(login.getActivity().getText(R.string.test_wrong_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(click());
        onView(withId(R.id.gs_login_progress_text)).check(matches(not(withText(R.string.login_success))));
    }

    @Test
    public void showsUnsuccesfulLoginResultWithWrongUser() {

        onView(withId(R.id.gs_login_username)).perform(clearText());
        onView(withId(R.id.gs_login_password)).perform(clearText());
        onView(withId(R.id.gs_login_username)).perform(typeText(login.getActivity().getText(R.string.test_wrong_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(typeText(login.getActivity().getText(R.string.test_multi_role_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(click());
        onView(withId(R.id.gs_login_progress_text)).check(matches(not(withText(R.string.login_success))));


    }

    @Test
    public void showsDialogWithMultipleRoles() {

        try {
            onView(withId(R.id.gs_role_choose_title)).check(matches(not(isDisplayed())));
            fail();
        } catch (NoMatchingViewException e) {
            assert true;
        }
        onView(withId(R.id.gs_login_username)).perform(clearText());
        onView(withId(R.id.gs_login_password)).perform(clearText());
        onView(withId(R.id.gs_login_username)).perform(typeText(login.getActivity().getText(R.string.test_multi_role_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(typeText(login.getActivity().getText(R.string.test_multi_role_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(click());
        onView(withId(R.id.gs_role_choose_title)).check(matches(isDisplayed()));

    }

    @Test
     public void showsExamOverviewAfterSuccesfulSingeRoleLogin() {
        try {
            onView(withId(R.id.gs_role_choose_title)).check(matches(not(isDisplayed())));
            fail();
        } catch (NoMatchingViewException e) {
            assert true;
        }
        onView(withId(R.id.gs_login_username)).perform(clearText());
        onView(withId(R.id.gs_login_password)).perform(clearText());
        onView(withId(R.id.gs_login_username)).perform(typeText(login.getActivity().getText(R.string.test_single_role_user).toString()));
        onView(withId(R.id.gs_login_password)).perform(typeText(login.getActivity().getText(R.string.test_single_role_password).toString()));
        onView(withId(R.id.gs_login_btn)).perform(click());
        onView(withId(R.id.gs_maintab_activity_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void showsNoAccountOption() {
        onView(withId(R.id.gs_login_btn_anonymous_exam)).check(matches(isDisplayed()));
    }

    @Test
    public void showsForgotPasswordOption(){
        onView(withId(R.id.gs_login_btn_forgot_password)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowAnonExamDialogAfterNoAccountOptionClicked(){
        onView(withId(R.id.gs_login_btn_anonymous_exam)).perform(click());
        onView(withId(R.id.gs_anonymous_exam_add_exam_id)).check(matches(isDisplayed()));
        onView(withId(R.id.gs_anonymous_exam_add_access_code)).check(matches(isDisplayed()));
        onView(withId(R.id.gs_anonymous_exam_btn_find)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowResetPasswordDialogAfterForgotPasswordOptionClicked(){
        onView(withId(R.id.gs_login_btn_forgot_password)).perform(click());
        onView(withId(R.id.gs_forgot_pwd_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.gs_forgot_pwd_instruction)).check(matches(isDisplayed()));
        onView(withId(R.id.gs_forgot_pwd_instruction2)).check(matches(isDisplayed()));
    }

}
