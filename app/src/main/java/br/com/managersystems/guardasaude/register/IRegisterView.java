package br.com.managersystems.guardasaude.register;

import android.support.design.widget.TextInputLayout;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public interface IRegisterView {

    /**
     * Initiates the adapters for the AutoCompleteTextViews.
     * It will add a list of suggestions to the views.
     */
    void initiateAdapters();

    /**
     * Initiates most listeners and TextWatchers which behave uniquely for the TextViews.
     * For example: It forces certain input rules or formats.
     */
    void initiateListeners();

    /**
     * The view keeps a map of all TextView with the TextInputLayout parents.
     * This method adds all the possible inputs and it's parent to a HashMap.
     * This map is used to store these views and to add certain rules to each of them,
     * as well as validating all inputs at the end.
     */
    void addAllInputsToMap();

    /**
     * Adds a 'Clear text' button to the right of the textView, which is only shown when
     * the textview is focused. Also adds a FocusChangeListener to each textView.
     * In the implementation we've made it so that after a view has left focus , without text.
     * The hint asterix changes color to alert the user.
     * For fields like password, where the input is hidden, a more visible alert is shown.
     * @param inputs HashMap object representing all the inputs and their wrappers.
     */
    void addFocusChangeListeners(Map<TextInputLayout, TextView> inputs);

    /**
     * Revalidate all forms after the create account button is clicked. As soon as one of the
     * inputs is marked as invalid or empty it will show the corresponding alert.
     * @return If all fields are valid this method returns true, otherwise it returns false.
     */
    boolean validateFormClientSide();

    /**
     * Shows an alert to the user that the field can't be empty.
     * The methods clearText and focusText are called upon which will delete all inputted text
     * and refocus the textview that needs correction.
     * @param textView TextView object representing an empty input.
     */
    void showFieldsCannotBeEmpty(TextView textView);

    /**
     * Shows an alert to the user that the field has an invalid entry.
     * In our implementation this can be due to the fact that a password has the wrong format,
     * or that both passwords don't match. It can also be due to the fact that an input for the
     * AutoCompleteTextViews does not match an item from the list.
     * The methods clearText and focusText are called upon which will delete all inputted text
     * and refocus the textview that needs correction.
     * @param textView TextView object representing an invalid input.
     */
    void showsInvalidFieldEntry(TextView textView);

    /**
     * Shows the filtered suggestions for the input of a city and is called upon by the presenter,
     * after successfully retrieving the locations.
     * @param cityText  AutoCompleteTextView representing the city textview.
     * @param cityIdNumbers List of strings representing the city ids
     * @param cities List of strings representing the cities that are suggested to the user.
     */
    void showCitySuggestions(AutoCompleteTextView cityText, List<String> cityIdNumbers, List<String> cities);

    void showSuccessfulRegistration();

    void showUnsuccessfulRegistration();
}
