package com.example.lenovo.my_app_mov2;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Lenovo on 04/2/2018.
 */

public class NotesAdapter extends ArrayAdapter<com.example.lenovo.my_app_mov2.NoteAttr> {
    public NotesAdapter(Context context, int resource, List<com.example.lenovo.my_app_mov2.NoteAttr> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
        }


        TextView Title = (TextView) convertView.findViewById(R.id.Tit);
        TextView Step = (TextView) convertView.findViewById(R.id.Steps);

        com.example.lenovo.my_app_mov2.NoteAttr noteAttr = getItem(position);
        Title.setText(noteAttr.getTitle());
        Step.setText(noteAttr.getStep());

        return convertView;
    }
}
