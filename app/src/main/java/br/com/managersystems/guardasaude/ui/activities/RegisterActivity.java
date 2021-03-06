package br.com.managersystems.guardasaude.ui.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.managersystems.guardasaude.R;
import br.com.managersystems.guardasaude.register.IRegisterView;
import br.com.managersystems.guardasaude.register.RegisterPresenter;
import br.com.managersystems.guardasaude.ui.customviews.InstantAutoComplete;
import br.com.managersystems.guardasaude.util.StringUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class RegisterActivity extends AppCompatActivity implements IRegisterView {

    @Bind(R.id.gs_new_account_coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.new_account_scrollview)
    ScrollView scrollView;

    @Bind(R.id.email_input_wrapper)
    TextInputLayout emailWrapper;

    @Bind(R.id.email_input)
    AppCompatEditText emailText;

    @Bind(R.id.password_input_wrapper)
    TextInputLayout passwordWrapper;

    @Bind(R.id.password_input)
    AppCompatEditText passwordText;

    @Bind(R.id.password_verification_input_wrapper)
    TextInputLayout vPasswordWrapper;

    @Bind(R.id.password_verification_input)
    AppCompatEditText vPasswordText;

    @Bind(R.id.password_input_notification)
    TextView passwordInputNotification;


    @Bind(R.id.password_verification_notification)
    TextView passwordNotification;

    @Bind(R.id.first_name_input_wrapper)
    TextInputLayout firstNameInputWrapper;

    @Bind(R.id.first_name_input)
    AppCompatEditText firstNameText;

    @Bind(R.id.last_name_input_wrapper)
    TextInputLayout lastNameInputWrapper;

    @Bind(R.id.last_name_input)
    AppCompatEditText lastNameText;

    @Bind(R.id.country_input)
    AppCompatSpinner countrySpinner;

    @Bind(R.id.city_input_wrapper)
    TextInputLayout cityInputWrapper;

    @Bind(R.id.forced_city_input)
    AutoCompleteTextView cityText;


    @Bind(R.id.gender_input)
    AppCompatSpinner genderSpinner;

    @Bind(R.id.language_input_wrapper)
    TextInputLayout languageInputWrapper;

    @Bind(R.id.forced_language_input)
    InstantAutoComplete languageText;

    @Bind(R.id.btn_birth_date)
    AppCompatImageButton birthDateBtn;

    @Bind(R.id.birth_date_input)
    AppCompatEditText birthDateText;

    @Bind(R.id.id_type_input_wrapper)
    TextInputLayout idTypeInputWrapper;

    @Bind(R.id.forced_id_type_input)
    InstantAutoComplete idTypeText;

    @Bind(R.id.id_input_wrapper)
    TextInputLayout idInputWrapper;

    @Bind(R.id.id_input)
    AppCompatEditText idInputText;

    @Bind(R.id.birth_date_input_wrapper)
    TextInputLayout birthDateInputWrapper;

    @Bind(R.id.btn_submit_new_account)
    Button submitAccount;


    Map<TextInputLayout, TextView> inputs;
    String birth_year;
    String birth_month;
    String birth_day;

    Snackbar invalidSnack;
    Snackbar emptySnack;
    Snackbar successSnack;
    Snackbar failedSnack;

    DatePickerDialog.OnDateSetListener onDateSelectedListener;
    TextWatcher idTypeWatcher;
    TextWatcher cityWatcher;
    TextWatcher nonCityWatcher;
    TextWatcher cpfTextWatcher;
    TextWatcher nonCpfTextWatcher;
    RegisterPresenter presenter;
    Context context;
    private String cpfNo = "";
    private final InputFilter[] CPF_FILTER = new InputFilter[]{new InputFilter.LengthFilter(14)};
    private final InputFilter[] NON_CPF_FILTER = new InputFilter[]{new InputFilter.LengthFilter(20)};
    private ArrayAdapter<String> cityAdapter;
    private ArrayAdapter<String> genderAdapter;
    private ArrayAdapter<String> countryAdapter;
    private ArrayAdapter<String> languageAdapter;
    private ArrayAdapter<String> idTypeAdapter;
    private ArrayList<String> cityIds;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        presenter = new RegisterPresenter(this);
        context = getApplicationContext();
        cityIds = new ArrayList<>();
        inputs = new HashMap<>();

        /*if (BuildConfig.DEBUG) {
            emailText.setText("jan@gmail.com");
            firstNameText.setText("jan");
            lastNameText.setText("somers");
            passwordText.setText("Abcde1");
            vPasswordText.setText("Abcde1");
            birthDateText.setText("03/05/1991");
            idInputText.setText("123.234.234-22");
        }*/
        initiateSnacks();
        initiateAdapters();
        initiateListeners();
        addAllInputsToMap();
        addFocusChangeListeners(inputs);
    }

    @Override
    public void initiateAdapters() {
        genderAdapter = new ArrayAdapter<String>(this, R.layout.spinner_gender_row, getResources().getStringArray(R.array.genders));
        genderSpinner.setAdapter(genderAdapter);
        countryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_country_row, getResources().getStringArray(R.array.countries));
        countrySpinner.setAdapter(countryAdapter);
        cityAdapter = new ArrayAdapter<String>(this, R.layout.spinner_city_row, new String[]{});
        cityText.setAdapter(cityAdapter);
        languageAdapter = new ArrayAdapter<String>(this, R.layout.language_row, getResources().getStringArray(R.array.languages));
        languageText.setAdapter(languageAdapter);
        idTypeAdapter = new ArrayAdapter<String>(this, R.layout.autocomplete_id_type_row, getResources().getStringArray(R.array.id_types));
        idTypeText.setAdapter(idTypeAdapter);

    }

    public void initiateSnacks() {
        emptySnack = Snackbar.make(coordinatorLayout, R.string.must_befilled_in, Snackbar.LENGTH_SHORT);
        emptySnack.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorError));
        invalidSnack = Snackbar.make(coordinatorLayout, R.string.invalid_entry, Snackbar.LENGTH_SHORT);
        invalidSnack.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorError));
        failedSnack = Snackbar.make(coordinatorLayout, R.string.register_failed, Snackbar.LENGTH_LONG);
        failedSnack.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorError));

    }

    @Override
    public void initiateListeners() {
        onDateSelectedListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                birth_year = year < 10 ? "0" + String.valueOf(year) : String.valueOf(year);
                birth_month = monthOfYear < 10 ? "0" + String.valueOf(monthOfYear) : String.valueOf(monthOfYear);
                birth_day = dayOfMonth < 10 ? "0" + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);

                birthDateText.setText(birth_day + "/" + birth_month + "/" + birth_year);
            }
        };
        nonCpfTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        cpfTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (cpfNo.length() < s.length()) {

                    switch (s.length()) {
                        case 4:
                            s.insert(3, ".");
                            break;
                        case 8:
                            s.insert(7, ".");
                            break;
                        case 12:
                            s.insert(11, "-");
                            break;
                    }
                }
                cpfNo = s.toString();

            }
        };
        cityWatcher = new TextWatcher() {
            String filter = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter = s.toString();
                if (countrySpinner.getSelectedItem().toString().toLowerCase().equals("brazil")) {
                    filter = s.toString();
                    if (filter.length() >= 2) {
                        presenter.retrieveLocations(cityText, filter);
                    }
                } else {
                    cityAdapter.clear();
                }

            }
        };
        nonCityWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        cityText.addTextChangedListener(cityWatcher);
        idTypeWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().toLowerCase().equals("cpf")) {
                    try {
                        idInputText.removeTextChangedListener(nonCpfTextWatcher);
                    } catch (Exception e) {
                        Log.d(this.getClass().getSimpleName(), "nonCpfTextWatcher wasn't attached yet, expected exception");
                    }
                    idInputText.setFilters(CPF_FILTER);
                    idInputText.addTextChangedListener(cpfTextWatcher);
                } else {
                    try {
                        idInputText.removeTextChangedListener(cpfTextWatcher);
                    } catch (Exception e) {
                        Log.d(this.getClass().getSimpleName(), "CpfTextWatcher wasn't attached yet, expected exception");
                    }
                    idInputText.setFilters(NON_CPF_FILTER);
                    idInputText.addTextChangedListener(nonCpfTextWatcher);
                }
            }
        };
        idTypeText.addTextChangedListener(idTypeWatcher);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (countrySpinner.getSelectedItem().toString().equals(getText(R.string.brazil))) {
                    try {
                        cityText.removeTextChangedListener(nonCityWatcher);

                    } catch (Exception e) {
                        // This exception can happen only once
                    }
                    cityText.addTextChangedListener(cityWatcher);
                } else {
                    try {
                        cityText.removeTextChangedListener(cityWatcher);
                    } catch (Exception e) {
                        // This exception can happen only once

                    }
                    cityAdapter.clear();
                    cityText.addTextChangedListener(nonCityWatcher);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void addAllInputsToMap() {
        inputs.put(emailWrapper, emailText);
        inputs.put(passwordWrapper, passwordText);
        inputs.put(vPasswordWrapper, vPasswordText);
        inputs.put(firstNameInputWrapper, firstNameText);
        inputs.put(lastNameInputWrapper, lastNameText);
        inputs.put(cityInputWrapper, cityText);
        inputs.put(languageInputWrapper, languageText);
        inputs.put(idTypeInputWrapper, idTypeText);
        inputs.put(idInputWrapper, idInputText);
        birthDateText.setKeyListener(null);
        inputs.put(birthDateInputWrapper, birthDateText);
    }

    @Override
    public void addFocusChangeListeners(Map<TextInputLayout, TextView> inputs) {
        for (Map.Entry<TextInputLayout, TextView> entry : inputs.entrySet()) {
            final TextInputLayout layout = entry.getKey();
            final TextView editText = entry.getValue();
            editText.setLongClickable(false);
            final Drawable leftDrawable = editText.getCompoundDrawables()[0];
            final Drawable crossDrawable = ContextCompat.getDrawable(context, R.drawable.ic_close_24dp);
            final String hint = layout.getHint().toString();
            int strLength = hint.length();
            final SpannableStringBuilder sb = new SpannableStringBuilder(hint);
            final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(229, 57, 53));
            final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
            sb.setSpan(fcs, strLength - 1, strLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sb.setSpan(bss, strLength - 1, strLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            final View.OnTouchListener onCrossTouchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if (motionEvent.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            editText.setText("");


                            return true;
                        }
                    }
                    return false;
                }
            };
            final View.OnTouchListener onNoCrossTouchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            };
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    boolean passWordField = (v.getId() == R.id.password_verification_input || v.getId() == R.id.password_input);
                    if (!hasFocus) {
                        if (editText.getEditableText().toString().isEmpty()) {
                            editText.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
                            editText.setOnTouchListener(onNoCrossTouchListener);
                            layout.setHint("");
                            if (layout.getEditText() != null) {
                                layout.getEditText().setHint(sb);
                            }

                        } else {
                            if (passWordField) {
                                String password = passwordText.getEditableText().toString();
                                String verification = vPasswordText.getEditableText().toString();
                                if (!password.isEmpty() && !StringUtils.isValidPassword(password)) {
                                    passwordInputNotification.setTextColor(ContextCompat.getColor(context, R.color.colorError));
                                    passwordInputNotification.setTextSize(12);

                                } else {
                                    passwordInputNotification.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary300));
                                    passwordInputNotification.setTextSize(11);

                                }
                                if (!password.equals(verification) && !password.isEmpty() && !verification.isEmpty()) {
                                    passwordNotification.setTextColor(ContextCompat.getColor(context, R.color.colorError));
                                    passwordNotification.setTextSize(12);
                                } else {
                                    passwordNotification.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary300));
                                    passwordNotification.setTextSize(11);
                                }

                            }


                        }

                    }
                    if (hasFocus) {
                        if (v.getId() == R.id.forced_city_input) {
                            if (!countrySpinner.getSelectedItem().toString().toLowerCase().equals("brazil".toLowerCase())) {
                                cityAdapter.clear();
                            }
                        }
                        editText.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, crossDrawable, null);
                        editText.setOnTouchListener(onCrossTouchListener);
                        if (layout.getEditText() != null) layout.getEditText().setHint("");
                        layout.setHint(sb);
                    }
                }
            });


        }
    }

    @OnTouch(R.id.btn_birth_date)
    public boolean touchBirthDateBtn(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            v.setElevation(1);
            v.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary100));
            showDatePickerDialog();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            v.setElevation(3);
            v.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        return true;
    }

    @OnClick(R.id.btn_birth_date)
    public void clickBirthDateBtn() {
        showDatePickerDialog();

    }


    @OnClick(R.id.btn_submit_new_account)
    public void clickCreateAccount() {
        boolean readyToRegister = validateFormClientSide();
        if (readyToRegister) {
            disableButton(submitAccount);
            presenter.registerUser(
                    firstNameText.getEditableText().toString(),
                    lastNameText.getEditableText().toString(),
                    emailText.getEditableText().toString(),
                    "BR",
                    cityIds.get(cityAdapter.getPosition(cityText.getEditableText().toString())),
                    passwordText.getEditableText().toString(),
                    vPasswordText.getEditableText().toString(),
                    idInputText.getEditableText().toString(),
                    idTypeText.getEditableText().toString(),
                    genderSpinner.getSelectedItem().toString().substring(0, 1).toUpperCase(),
                    birthDateText.getText().toString());

        /* get all input values and forward to presenter.requestNewAccount */
        }

    }

    private void disableButton(Button submitAccount) {
        submitAccount.setEnabled(false);
        submitAccount.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent200));
        submitAccount.setText(getText(R.string.creating_account));
        submitAccount.setTextColor(ContextCompat.getColor(this, R.color.colorTextColorLight));
    }

    private void enableButton(Button submitAccount) {
        submitAccount.setEnabled(true);
        submitAccount.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        submitAccount.setText(getText(R.string.create_account));
        submitAccount.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
    }

    @Override
    public boolean validateFormClientSide() {
        boolean allFieldsValid = true;
        String password = passwordText.getEditableText().toString();
        for (Map.Entry<TextInputLayout, TextView> entry : inputs.entrySet()) {
            TextView textView = entry.getValue();
            if (!textView.getEditableText().toString().isEmpty() && allFieldsValid) {
                switch (textView.getId()) {
                    case (R.id.email_input):
                        allFieldsValid = StringUtils.isValidEmail(textView.getEditableText().toString());
                        break;
                    case (R.id.password_input):
                        password = textView.getEditableText().toString();
                        allFieldsValid = StringUtils.isValidPassword(password);
                        break;
                    case (R.id.password_verification_input):
                        allFieldsValid = password.equals(textView.getEditableText().toString());
                        break;
                    case (R.id.first_name_input):
                        allFieldsValid = !textView.getEditableText().toString().isEmpty();
                        break;
                    case (R.id.last_name_input):
                        allFieldsValid = !textView.getEditableText().toString().isEmpty();
                        break;
                    case (R.id.forced_city_input):
                        boolean matchFound = false;
                        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cityText.getAdapter();
                        String city = cityText.getText().toString();
                        if (adapter.getPosition(city) >= 0) matchFound = true;
                        allFieldsValid = matchFound;
                        break;
                    case (R.id.forced_language_input):
                        allFieldsValid = StringUtils.stringInArray(textView.getEditableText().toString(), getResources().getStringArray(R.array.languages));
                        break;
                    case (R.id.forced_id_type_input):
                        allFieldsValid = StringUtils.stringInArray(textView.getEditableText().toString(), getResources().getStringArray(R.array.id_types));
                        break;
                    case (R.id.id_input):
                        if (idTypeText.equals(getText(R.string.CPF))) {
                            allFieldsValid = StringUtils.isValidCPF(textView.getEditableText().toString());
                            break;
                        }
                    case (R.id.birth_date_input):
                        allFieldsValid = !textView.getText().toString().isEmpty();
                        break;
                    default:
                        allFieldsValid = !textView.getEditableText().toString().isEmpty();


                }
                if (!allFieldsValid) showsInvalidFieldEntry(textView);
            } else if (textView.getEditableText().toString().isEmpty()) {
                allFieldsValid = false;
                showFieldsCannotBeEmpty(textView);
            }

        }
        return allFieldsValid;
    }

    /**
     * Displays a dialog where users can select their birth date.
     * Sets the starting / max date to the current date.
     */
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSelectedListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    @Override
    public void showFieldsCannotBeEmpty(final TextView textView) {

        emptySnack.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearText(textView);
                focusText(textView);
            }
        }, Snackbar.LENGTH_LONG + 1000);
    }


    @Override
    public void showsInvalidFieldEntry(final TextView textView) {
        invalidSnack.setText(String.valueOf(textView.getText()) + " " + getResources().getString(R.string.invalid_entry));
        invalidSnack.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearText(textView);
                focusText(textView);
            }
        }, Snackbar.LENGTH_LONG + 1000);

        if (birthDateText.getText().toString().isEmpty()) {
            birthDateBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError));
        } else {
            birthDateBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
    }


    @Override
    public void showCitySuggestions(AutoCompleteTextView cityText, List<String> cityIdNumbers, List<String> cities) {
        ArrayAdapter<String> cityAdapter = (ArrayAdapter<String>) cityText.getAdapter();
        cityAdapter.clear();
        cityAdapter.addAll(cities);
        if (cityIdNumbers.size() > 0) {
            cityIds = (ArrayList<String>) cityIdNumbers;
        }
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSuccessfulRegistration() {
        submitAccount.setText(getText(R.string.created_account));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enableButton(submitAccount);
                setResult(RESULT_OK);
                finish();
            }
        }, 300);

    }

    @Override
    public void showUnsuccessfulRegistration() {
        enableButton(submitAccount);
        failedSnack.show();
    }

    /**
     * Requests focus on a textView and scrolls to the bottom of the textView.
     *
     * @param textView TextView object that needs to be focused.
     */
    private void focusText(TextView textView) {
        textView.setFocusableInTouchMode(true);
        textView.requestFocus();
        scrollView.scrollTo(0, textView.getBottom());
    }

    /**
     * Clears the text.
     *
     * @param textView TextView object that needs to be cleared of text.
     */
    private void clearText(TextView textView) {
        textView.getEditableText().clear();
    }
}
