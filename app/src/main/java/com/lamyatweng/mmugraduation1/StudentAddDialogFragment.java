package com.lamyatweng.mmugraduation1;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.lamyatweng.mmugraduation1.model.Student;

public class StudentAddDialogFragment extends DialogFragment {
    Firebase mFirebaseStudentRef;
    TextInputLayout mStudentNameWrapper;
    TextInputLayout mStudentIdWrapper;
    Spinner mCourseSpinner;
    Spinner mStatusSpinner;
    TextInputLayout mBalanceCreditHour;
    TextInputLayout mCgpa;
    Spinner mMuet;
    TextInputLayout mFinancialDue;

    public StudentAddDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
        mFirebaseStudentRef = new Firebase("https://mmugraduation.firebaseio.com/students");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_student_add, container, false);
        // name
        mStudentNameWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_student_name);
        mStudentIdWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_student_id);
        // course
        mCourseSpinner = (Spinner) view.findViewById(R.id.course_spinner);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.course_array, android.R.layout.simple_spinner_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCourseSpinner.setAdapter(courseAdapter);
        // status
        mStatusSpinner = (Spinner) view.findViewById(R.id.status_spinner);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatusSpinner.setAdapter(statusAdapter);
        // credit hour
        mBalanceCreditHour = (TextInputLayout) view.findViewById(R.id.wrapper_student_balanceCreditHour);
        mCgpa = (TextInputLayout) view.findViewById(R.id.wrapper_student_cgpa);
        // muet
        mMuet = (Spinner) view.findViewById(R.id.muet_spinner);
        ArrayAdapter<CharSequence> muetAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.muet_array, android.R.layout.simple_spinner_item);
        muetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMuet.setAdapter(muetAdapter);
        // financial
        mFinancialDue = (TextInputLayout) view.findViewById(R.id.wrapper_student_financialDue);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("New student");
        toolbar.setNavigationIcon(R.mipmap.ic_close_white_24dp);
        toolbar.inflateMenu(R.menu.student_add);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentAddDialogFragment.this.getDialog().cancel();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String name = mStudentNameWrapper.getEditText().getText().toString();
                int id = Integer.parseInt(mStudentIdWrapper.getEditText().getText().toString());
                String course = mCourseSpinner.getSelectedItem().toString();
                String status = mStatusSpinner.getSelectedItem().toString();
                int balanceCreditHour = Integer.parseInt(mBalanceCreditHour.getEditText().getText().toString());
                double cgpa = Double.parseDouble(mCgpa.getEditText().getText().toString());
                int muet = Integer.parseInt(mMuet.getSelectedItem().toString());
                double financialDue = Double.parseDouble(mFinancialDue.getEditText().getText().toString());

                Student newStudent = new Student(name, id, course, status, balanceCreditHour, cgpa, muet, financialDue);
                mFirebaseStudentRef.push().setValue(newStudent);
                Toast.makeText(getActivity(), "Student added.", Toast.LENGTH_SHORT).show();
                // Close dialog
                StudentAddDialogFragment.this.getDialog().cancel();
                return false;
            }
        });


        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_DialogWhenLarge_NoActionBar);
        return dialog;
    }
}
