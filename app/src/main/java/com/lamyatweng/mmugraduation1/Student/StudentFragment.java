package com.lamyatweng.mmugraduation1.Student;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduation1.R;

public class StudentFragment extends Fragment {

    Firebase mStudentRef;

    public StudentFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
        mStudentRef = new Firebase("https://mmugraduation.firebaseio.com/students");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Student");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_student, container, false);
        ListView studentListView = (ListView) rootView.findViewById(R.id.student_list_view);
        FloatingActionButton addStudentFab = (FloatingActionButton) rootView.findViewById(R.id.add_student_fab);

        final StudentCustomAdapter adapter = new StudentCustomAdapter(getActivity());
        mStudentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student;
                adapter.clear();
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    student = studentSnapshot.getValue(Student.class);
                    adapter.add(student);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Snackbar.make(rootView, firebaseError.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        studentListView.setAdapter(adapter);

        addStudentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                StudentAddDialogFragment newFragment = new StudentAddDialogFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(newFragment, null).addToBackStack(null).commit();
            }
        });

        return rootView;
    }
}
