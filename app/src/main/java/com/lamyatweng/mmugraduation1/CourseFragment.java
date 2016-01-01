package com.lamyatweng.mmugraduation1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduation1.model.Programme;

public class CourseFragment extends Fragment {
    Firebase mCourseRef;

    public CourseFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getContext());
        mCourseRef = new Firebase("https://mmugraduation.firebaseio.com/courses");
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.programme_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_progr:
                Intent intent = new Intent(getActivity(), CourseAddActivity.class);
                getActivity().startActivity(intent);
                return true;

            case R.id.action_categorise:
                return true;

            case R.id.action_share:
                return true;

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_course, container, false);
        ListView programmeView = (ListView) rootView.findViewById(R.id.programme_list_1);
        final CustomProgrammesAdapter adapter = new CustomProgrammesAdapter(getActivity());
        mCourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Programme progr;
                adapter.clear();
                for (DataSnapshot progrSnapshot : dataSnapshot.getChildren()) {
                    progr = progrSnapshot.getValue(Programme.class);
                    adapter.add(progr);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        programmeView.setAdapter(adapter);
        return rootView;
    }
}