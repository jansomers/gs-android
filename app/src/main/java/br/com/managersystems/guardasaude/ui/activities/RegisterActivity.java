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
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;

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


    @Bind(R.id.spinner_gender)
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


    Map<TextInputLayout, TextView> inputs;
    String birth_year;
    String birth_month;
    String birth_day;

    DatePickerDialog.OnDateSetListener onDateSelectedListener;
    TextWatcher idTypeWatcher;
    TextWatcher cityWatcher;
    TextWatcher nonCityWatcher;
    TextWatcher cpfTextWatcher;
    TextWatcher nonCpfTextWatcher;
    RegisterPresenter presenter;
    Context context;
    private String cpfNo = "";
    private final InputFilter[] CPFFILTER = new InputFilter[]{new InputFilter.LengthFilter(14)};
    private final InputFilter[] NONCPFFILTER = new InputFilter[]{new InputFilter.LengthFilter(20)};
    private ArrayAdapter<String> cityAdapter;
    private ArrayAdapter<String> genderAdapter;
    private ArrayAdapter<String> countryAdapter;
    private ArrayAdapter<String> languageAdapter;
    private ArrayAdapter<String> idTypeAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        presenter = new RegisterPresenter(this);
        context = getApplicationContext();
        inputs = new HashMap<>();

        initiateAdapters();
        initiateListeners();
        addAllInputsToMap();
        addFocusChangeListeners(inputs);
    }

    @Override
    public void initiateAdapters() {
        genderAdapter = new ArrayAdapter<String>(this, R.layout.gender_row, getResources().getStringArray(R.array.genders));
        genderSpinner.setAdapter(genderAdapter);
        countryAdapter = new ArrayAdapter<String>(this, R.layout.country_row, getResources().getStringArray(R.array.countries));
        countrySpinner.setAdapter(countryAdapter);
        cityAdapter = new ArrayAdapter<String>(this, R.layout.city_row, new String[]{});
        cityText.setAdapter(cityAdapter);
        languageAdapter = new ArrayAdapter<String>(this, R.layout.language_row, getResources().getStringArray(R.array.languages));
        languageText.setAdapter(languageAdapter);
        idTypeAdapter = new ArrayAdapter<String>(this, R.layout.id_type_row, getResources().getStringArray(R.array.id_types));
        idTypeText.setAdapter(idTypeAdapter);

    }

    @Override
    public void initiateListeners() {
        onDateSelectedListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                birth_year = year <10 ? "0" + String.valueOf(year) : String.valueOf(year);
                birth_month = monthOfYear < 10 ? "0" + String.valueOf(monthOfYear) : String.valueOf(monthOfYear);
                birth_day = dayOfMonth < 10 ? "0" +  String.valueOf(dayOfMonth): String.valueOf(dayOfMonth);
                StringBuilder dateStringBuilder = new StringBuilder(10);

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
                if (countrySpinner.getSelectedItem().toString().toLowerCase().equals("brazil")) {
                    filter = s.toString();
                    if (filter.length() >= 2) {
                        presenter.retrieveLocations(cityText, filter);
                    }
                } else {
                    cityAdapter.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter = s.toString();

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
                    idInputText.setFilters(CPFFILTER);
                    idInputText.addTextChangedListener(cpfTextWatcher);
                } else {
                    try {
                        idInputText.removeTextChangedListener(cpfTextWatcher);
                    } catch (Exception e) {
                        Log.d(this.getClass().getSimpleName(), "CpfTextWatcher wasn't attached yet, expected exception");
                    }
                    idInputText.setFilters(NONCPFFILTER);
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
                }
                else  {
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
            final View.OnTouchListener oncrossTouchListener = new View.OnTouchListener() {
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
                            layout.getEditText().setHint(sb);
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
                            if (!countrySpinner.getSelectedItem().toString().toLowerCase().equals("brazil".toLowerCase())){
                                cityAdapter.clear();
                            }
                        }
                        editText.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, crossDrawable, null);
                        editText.setOnTouchListener(oncrossTouchListener);
                        layout.getEditText().setHint("");
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


    @OnClick(R.id.submit_new_account_btn)
    public void clickCreateAccount() {
        boolean readyToRegister = validateFormClientSide();
        if (readyToRegister) { /* get all input values and forward to presenter.requestNewAccount */} else {
        } /* do nothing */

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
                        for (int i = 0; i < cityText.getAdapter().getCount(); i++) {
                            if (allFieldsValid = true) {
                                if (!cityText.getText().equals(cityText.getAdapter().getItem(i)))
                                    allFieldsValid = false;
                            }
                        }
                        break;
                    case (R.id.forced_language_input):
                        allFieldsValid = StringUtils.stringInArray(textView.getEditableText().toString(), getResources().getStringArray(R.array.languages));
                        break;
                    case (R.id.forced_id_type_input):
                        allFieldsValid = StringUtils.stringInArray(textView.getEditableText().toString(), getResources().getStringArray(R.array.id_types));
                        break;
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

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSelectedListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    @Override
    public void showFieldsCannotBeEmpty(final TextView textView) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.must_befilled_in, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorError));
        snackbar.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearText(textView);
                focusText(textView);
            }
        }, Snackbar.LENGTH_LONG + 350);
    }


    @Override
    public void showsInvalidFieldEntry(final TextView textView) {
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.append(" ");
        sb.append(getResources().getString(R.string.invalid_entry));
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, sb.toString(), Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorError));
        snackbar.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearText(textView);
                focusText(textView);
            }
        }, Snackbar.LENGTH_LONG + 750);

        if (birthDateText.getText().toString().isEmpty()) {
            birthDateBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError));
        } else {
            birthDateBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
    }


    @Override
    public void showCitySuggestions(AutoCompleteTextView cityText, List<String> cities) {
        ArrayAdapter<String> cityAdapter = (ArrayAdapter<String>) cityText.getAdapter();
        cityAdapter.clear();
        cityAdapter.addAll(cities);
        cityAdapter.notifyDataSetChanged();
    }

    private void focusText(TextView textView) {
        textView.setFocusableInTouchMode(true);
        textView.requestFocus();
        scrollView.scrollTo(0, textView.getBottom());
    }

    private void clearText(TextView textView) {
        textView.getEditableText().clear();
    }
}
