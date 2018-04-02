//package juked.juked;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import org.w3c.dom.Text;
//
//import java.util.List;
//
///**
// * Created by Francisco-Mac on 4/1/18.
// */
//
//public class songListAdapter extends BaseAdapter {
//    private Activity activity;
//    private List<Song> songs;
//    private static LayoutInflater inflater;
//
//    public songListAdapter (Activity a, List<Song> s) {
//        activity = a;
//        songs = s;
//        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    public int getCount() {
//        //return how many users in the lobby
//        return 10;
//    }
//
//    public Object getItem (int position) {
//        return position;
//    }
//
//    public long getItemId (int position) {
//        return position;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View v = convertView;
//
//        if(convertView == null) {
//            v = inflater.inflate(R.layout.songlistview_row, parent, false);
//        }
//
//        TextView title = (TextView) v.findViewById(R.id.songsRowTextView);
//        Song song = songs.get(position);
//
//        title.setText(song.getTitle());
//        return v;
//    }
//
//}
