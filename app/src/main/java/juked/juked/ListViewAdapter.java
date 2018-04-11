package juked.juked;


        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;
        import java.util.concurrent.ExecutionException;
        import juked.juked.testRecycledView;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<String> animalNamesList = null;
    private ArrayList<String> arraylist;

    public ListViewAdapter(Context context, List<String> animalNamesList) {
        mContext = context;
        this.animalNamesList = animalNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<String>();
        this.arraylist.addAll(animalNamesList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return animalNamesList.size();
    }

    @Override
    public String getItem(int position) {
        return animalNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.nameofanimal);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(animalNamesList.get(position));


        final View finalView = view;
        view.findViewById(R.id.searchReturnItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv_animalName = finalView.findViewById(R.id.nameofanimal);
                testRecycledView.fhp.querySearchedSong(position);
            }
        });


        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        animalNamesList.clear();
        if (charText.length() == 0) {
            animalNamesList.addAll(arraylist);
        } else {
            for (String wp : arraylist) {
                if (wp.toLowerCase(Locale.getDefault()).contains(charText)) {
                    animalNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
