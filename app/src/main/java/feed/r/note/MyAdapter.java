package feed.r.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyAdapter extends ArrayAdapter<Note> {
    ArrayList<Note> heroList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;
    public MyAdapter(@NonNull MainActivity context, int resource, @NonNull List<Note> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Note note = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.textView);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView2);

        // Populate the data into the template view using the data object
        tvTitle.setText(note.Title);
        tvDate.setText(note.Date);
        // Return the completed view to render on screen
        return convertView;
    }
}
//////////////////////
