package com.lamyatweng.mmugraduation1;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.firebase.client.Firebase;
import com.lamyatweng.mmugraduation1.model.Programme;

public class ProgrammeAddActivity extends AppCompatActivity {
    Firebase mFirebaseProgrammeRef;
    TextInputLayout mNameWrapper;
    Spinner mFacultySpinner;
    Spinner mLevelSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        mFirebaseProgrammeRef = new Firebase("https://mmugraduation.firebaseio.com/courses");
        setContentView(R.layout.activity_programme_add);

        // Set up action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        // Enable the Up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("New Programme");
        }

        // Initialize courseName textInput
        mNameWrapper = (TextInputLayout) findViewById(R.id.wrapper_programme_name);
        mNameWrapper.setErrorEnabled(true);

        // Initialize courseFaculty spinner
        mFacultySpinner = (Spinner) findViewById(R.id.faculty_spinner);
        ArrayAdapter<CharSequence> courseFacultyAdapter = ArrayAdapter.createFromResource(this,
                R.array.faculty_array, android.R.layout.simple_spinner_item);
        courseFacultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFacultySpinner.setAdapter(courseFacultyAdapter);

        // Initialize courseLevel spinner
        mLevelSpinner = (Spinner) findViewById(R.id.course_level_spinner);
        ArrayAdapter<CharSequence> courseLevelAdapter = ArrayAdapter.createFromResource(this,
                R.array.programme_level_array, android.R.layout.simple_spinner_item);
        courseLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLevelSpinner.setAdapter(courseLevelAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.course_add, menu);
        return true;
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_course:
                String courseFaculty = mFacultySpinner.getSelectedItem().toString();
                String courseLevel = mLevelSpinner.getSelectedItem().toString();

                // Error will be shown if courseName is blank
                if (mNameWrapper.getEditText() != null &&
                        !"".equals(mNameWrapper.getEditText().getText().toString())) {

                    String courseName = mNameWrapper.getEditText().getText().toString();
                    Programme newProgramme = new Programme(courseName, courseFaculty, courseLevel);
                    mFirebaseProgrammeRef.push().setValue(newProgramme);
                    mNameWrapper.setErrorEnabled(false);
                    hideKeyboard();
                    View view = getCurrentFocus();
                    if (view != null) {
                        Snackbar.make(view, "Successfully added", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    mNameWrapper.setError("Programme name should not be blank.");
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
