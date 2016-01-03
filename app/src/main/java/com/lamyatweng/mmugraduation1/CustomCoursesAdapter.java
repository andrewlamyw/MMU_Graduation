package com.lamyatweng.mmugraduation1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lamyatweng.mmugraduation1.model.Course;

public class CustomCoursesAdapter extends ArrayAdapter<Course> {
    public CustomCoursesAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_course, parent, false);
        }

        TextView courseName = (TextView) convertView.findViewById(R.id.courseName);
        TextView courseFaculty = (TextView) convertView.findViewById(R.id.courseFaculty);

        courseName.setText(course.getName());
        courseFaculty.setText(course.getFaculty());

        return convertView;
    }
}
