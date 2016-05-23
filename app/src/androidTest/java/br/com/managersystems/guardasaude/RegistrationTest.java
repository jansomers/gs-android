package br.com.managersystems.guardasaude;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import br.com.managersystems.guardasaude.ui.activities.RegisterActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RegistrationTest {

    @Rule
    public final ActivityTestRule<RegisterActivity> register = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);

    final String INVALID_PASSWORD = "admin";
    final String[] NON_MATCHING_PASSWORDS = new String[]{"Admin123", "Admin1234"};

    static List<TextView> textFields;
    static TextView email;
    static TextView password;
    static TextView vPassword;
    static TextView firstName;
    static TextView lastName;
    static TextView city;
    static TextView language;
    static TextView idType;
    static TextView idInput;
    static TextView passwordNotification;
    static TextView matchingNotification;




    @Before
    public void beforeEachTest() {
        textFields = new ArrayList<>();
        email = (TextView) register.getActivity().findViewById(R.id.email_input);
        textFields.add(email);
        password = (TextView) register.getActivity().findViewById(R.id.password_input);
        textFields.add(password);
        vPassword = (TextView) register.getActivity().findViewById(R.id.password_verification_input);
        textFields.add(vPassword);
        firstName = (TextView) register.getActivity().findViewById(R.id.first_name_input);
        textFields.add(firstName);
        lastName = (TextView) register.getActivity().findViewById(R.id.last_name_input);
        textFields.add(lastName);
        city = (TextView) register.getActivity().findViewById(R.id.forced_city_input);
        textFields.add(city);
        language = (TextView) register.getActivity().findViewById(R.id.forced_language_input);
        textFields.add(language);
        idType = (TextView) register.getActivity().findViewById(R.id.forced_id_type_input);
        textFields.add(idType);
        idInput = (TextView) register.getActivity().findViewById(R.id.id_input);
        textFields.add(idInput);
        passwordNotification = (TextView) register.getActivity().findViewById(R.id.password_input_notification);
        matchingNotification = (TextView) register.getActivity().findViewById(R.id.password_verification_notification);
    }

    @Test
    public void showsAllInputs(){
        for (TextView view : textFields) {
            onView(withId(view.getId())).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        }
    }
    @Test
    public void showCreateAccountButton(){
        onView(withId(R.id.btn_submit_new_account)).check(matches(not(isDisplayed())));
        onView(withId(R.id.btn_submit_new_account)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }


    @Test
    public void showsWrongPasswordFormatNotification() {
        onView(withId(password.getId())).perform(typeText(INVALID_PASSWORD));
        onView(withId(vPassword.getId())).perform(click());
        int errorColor = passwordNotification.getCurrentTextColor();
        if (errorColor == R.color.colorError) {
            assert true;
        }
        else {
            assert false;
        }
    }

    @Test
    public void showsCorrectPasswordFormatNotification() {
        onView(withId(password.getId())).perform(typeText(NON_MATCHING_PASSWORDS[0]));
        onView(withId(vPassword.getId())).perform(click());
        int fieldColor = passwordNotification.getCurrentTextColor();
        if (fieldColor == R.color.colorError) {
            assert false;
        }
        else {
            assert true;
        }
    }

    @Test
    public void showsNonMatchingPasswordsNotification() {
        onView(withId(password.getId())).perform(typeText(NON_MATCHING_PASSWORDS[0]));
        onView(withId(vPassword.getId())).perform(typeText(NON_MATCHING_PASSWORDS[1]));
        onView(withId(firstName.getId())).perform(click());
        int fieldColor = matchingNotification.getCurrentTextColor();
        if (fieldColor == R.color.colorError){
            assert true;
        }
        else {
            assert false;
        }
    }
    public void showsCorrectMatchingPasswordsNotification() {
        onView(withId(password.getId())).perform(typeText(NON_MATCHING_PASSWORDS[0]));
        onView(withId(vPassword.getId())).perform(typeText(NON_MATCHING_PASSWORDS[0]));
        onView(withId(firstName.getId())).perform(click());
        int fieldColor = matchingNotification.getCurrentTextColor();
        if (fieldColor == R.color.colorError){
            assert false;
        }
        else {
            assert true;
        }
    }

    /**
     * Fails because I use a custom implementation of an AutoCompleteTextView
     * Should be looked into , but I can't currently test this. I assume it works , but for automated testing I couldn't find a solution.
     */
    @Test
    public void shouldApplyCPFFormat() {
        onView(withId(R.id.forced_id_type_input)).perform(typeText("CPF"));
        onView(withId(idInput.getId())).perform(typeText("12345678910"));
        if (idInput.getEditableText().toString().matches("\\d{3}.\\d{3}.\\d{3}-\\d{2}")) {
            assert true;
        }
        else {
            assert false;
        }
    }

    @Test
    public void showsSpinners() {
        onView(withId(R.id.gender_input)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.country_input)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
    @Test
    public void showsDisabledBirthday() {
        onView(withId(R.id.birth_date_input)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

}
