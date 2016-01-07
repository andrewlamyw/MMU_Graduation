package com.lamyatweng.mmugraduation1.Programme;

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
import com.lamyatweng.mmugraduation1.R;

public class ProgrammeAddDialogFragment extends DialogFragment {

    public ProgrammeAddDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_programme_add, container, false);
        final Firebase firebaseProgrammeRef = new Firebase("https://mmugraduation.firebaseio.com/programmes");
        final TextInputLayout programmeNameWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_programme_name);
        final Spinner programmeLevelSpinner = (Spinner) view.findViewById(R.id.programme_level_spinner);
        final Spinner facultySpinner = (Spinner) view.findViewById(R.id.faculty_spinner);

        ArrayAdapter<CharSequence> programmeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.programme_level_array, android.R.layout.simple_spinner_item);
        programmeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programmeLevelSpinner.setAdapter(programmeAdapter);

        ArrayAdapter<CharSequence> facultyAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.faculty_array, android.R.layout.simple_spinner_item);
        facultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facultySpinner.setAdapter(facultyAdapter);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("New programme");
        toolbar.setNavigationIcon(R.mipmap.ic_close_white_24dp);
        toolbar.inflateMenu(R.menu.programme_add);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgrammeAddDialogFragment.this.getDialog().cancel();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String name = programmeNameWrapper.getEditText().getText().toString();
                String level = programmeLevelSpinner.getSelectedItem().toString();
                String faculty = facultySpinner.getSelectedItem().toString();

                Programme newProgramme = new Programme(name, level, faculty);
                firebaseProgrammeRef.push().setValue(newProgramme);
                Toast.makeText(getActivity(), "Programme added.", Toast.LENGTH_SHORT).show();
                // Close dialog
                ProgrammeAddDialogFragment.this.getDialog().cancel();
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
