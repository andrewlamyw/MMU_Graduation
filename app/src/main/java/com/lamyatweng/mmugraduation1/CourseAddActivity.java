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
import com.lamyatweng.mmugraduation1.model.Course;

public class CourseAddActivity extends AppCompatActivity {
    Firebase mFirebaseCourseRef;
    TextInputLayout mCourseNameWrapper;
    Spinner mCampusLocationSpinner;
    Spinner mCourseTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_course_add);

        mFirebaseCourseRef = new Firebase("https://mmugraduation.firebaseio.com/courses");


        // Set action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        // Enable the Up button
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("New Course");
        }

        // Initialize course name text input
        mCourseNameWrapper = (TextInputLayout) findViewById(R.id.wrapper_course_name);
        mCourseNameWrapper.setHint("Course Name");
        mCourseNameWrapper.setErrorEnabled(true);

        // Initialize campus location spinner
        mCampusLocationSpinner = (Spinner) findViewById(R.id.campus_location_spinner);
        ArrayAdapter<CharSequence> campusLocationAdapter = ArrayAdapter.createFromResource(this,
                R.array.campus_locations_array, android.R.layout.simple_spinner_item);
        campusLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCampusLocationSpinner.setAdapter(campusLocationAdapter);

        // Initialize course type spinner
        mCourseTypeSpinner = (Spinner) findViewById(R.id.course_type_spinner);
        ArrayAdapter<CharSequence> courseTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.course_types_array, android.R.layout.simple_spinner_item);
        courseTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCourseTypeSpinner.setAdapter(courseTypeAdapter);
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
                String campusLocation = mCampusLocationSpinner.getSelectedItem().toString();
                String courseType = mCourseTypeSpinner.getSelectedItem().toString();

                // Error will be shown if courseName is empty
                if (mCourseNameWrapper.getEditText() != null &&
                        !"".equals(mCourseNameWrapper.getEditText().getText().toString())) {

                    String courseName = mCourseNameWrapper.getEditText().getText().toString();
                    Course newCourse = new Course(courseName, campusLocation, courseType);
                    mFirebaseCourseRef.push().setValue(newCourse);
                    mCourseNameWrapper.setErrorEnabled(false);
                    hideKeyboard();
                    View view = getCurrentFocus();
                    if (view != null) {
                        Snackbar.make(view, "Successfully added", Snackbar.LENGTH_LONG).show();
                    }

                } else {
                    mCourseNameWrapper.setError("Course name should not be empty.");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
