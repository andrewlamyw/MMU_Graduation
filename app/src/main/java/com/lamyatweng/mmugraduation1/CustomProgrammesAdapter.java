package com.lamyatweng.mmugraduation1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lamyatweng.mmugraduation1.model.Programme;

public class CustomProgrammesAdapter extends ArrayAdapter<Programme> {
    public CustomProgrammesAdapter(Context context) {
        super(context, 0);
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Programme programme = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_programme, parent, false);
        }
        // Lookup view for data population
        TextView progrName = (TextView) convertView.findViewById(R.id.progrName);
        TextView progrCampus = (TextView) convertView.findViewById(R.id.progrCampus);
        // Populate the data into the template view using the data object
        progrName.setText(programme.getName());
        progrCampus.setText(programme.getCampus());
        // Return the completed view to render on screen
        return convertView;
    }
}
