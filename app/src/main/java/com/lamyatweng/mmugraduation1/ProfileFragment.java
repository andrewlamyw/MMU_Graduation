package com.lamyatweng.mmugraduation1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.lamyatweng.mmugraduation1.Student.Student;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Firebase.setAndroidContext(getActivity());
        Firebase studentRef = new Firebase("https://mmugraduation.firebaseio.com/students");

        final TextView name = (TextView) rootView.findViewById(R.id.profile_student_name);
        final TextView id = (TextView) rootView.findViewById(R.id.profile_student_id);
        final TextView programme = (TextView) rootView.findViewById(R.id.profile_student_programme);
        final TextView status = (TextView) rootView.findViewById(R.id.profile_student_status);
        final TextView email = (TextView) rootView.findViewById(R.id.profile_student_email);
        final TextView creditHour = (TextView) rootView.findViewById(R.id.profile_student_balanceCreditHour);
        final TextView cgpa = (TextView) rootView.findViewById(R.id.profile_student_CGPA);
        final TextView muet = (TextView) rootView.findViewById(R.id.profile_student_muet);
        final TextView financial = (TextView) rootView.findViewById(R.id.profile_student_financialDue);

        SessionManager session = new SessionManager(getActivity().getApplicationContext());
        session.checkLogin();
        String userEmail = session.getUserEmail();

        Query queryRef = studentRef.orderByChild("email").equalTo(userEmail);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);
                name.setText(student.getName());
                id.setText(student.getId());
                programme.setText(student.getProgramme());
                status.setText(student.getStatus());
                email.setText(student.getEmail());
                creditHour.setText(String.valueOf(student.getBalanceCreditHour()));
                cgpa.setText(String.valueOf(student.getCgpa()));
                muet.setText(String.valueOf(student.getMuet()));
                financial.setText(String.valueOf(student.getFinancialDue()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return rootView;
    }
}