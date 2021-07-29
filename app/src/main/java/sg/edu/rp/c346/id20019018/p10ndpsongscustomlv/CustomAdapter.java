package sg.edu.rp.c346.id20019018.p10ndpsongscustomlv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Song> SongList;

    public CustomAdapter(Context context, int resource, ArrayList<Song> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        SongList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvSinger = rowView.findViewById(R.id.textViewSinger);
        TextView tvSong = rowView.findViewById(R.id.textViewSong);
        TextView tvYear = rowView.findViewById(R.id.textViewYear);
        ImageView iv = rowView.findViewById(R.id.imageView);
        RatingBar rb = rowView.findViewById(R.id.ratingBar);

        // Obtain the Android Version information based on the position
        Song currentVersion = SongList.get(position);

        // Set values to the TextView to display the corresponding information
        tvSong.setText(currentVersion.getTitle());
        tvSinger.setText(currentVersion.getSingers());
        tvYear.setText(currentVersion.getYear() + "");
        rb.setRating(currentVersion.getStars());
        if (currentVersion.getYear() >= 2019)
        {
            iv.setImageResource(R.drawable.newsong);
        }
        else
        {
            iv.setVisibility(View.INVISIBLE);
        }



        return rowView;
    }

}
