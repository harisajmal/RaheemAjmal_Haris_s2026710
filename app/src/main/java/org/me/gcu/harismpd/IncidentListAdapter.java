package org.me.gcu.harismpd;

/*

Name: Haris Raheem Ajmal
Matric Number: S2026710


 */
import java.util.LinkedList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IncidentListAdapter extends BaseAdapter  {

    LinkedList<Item> incidentData;
    Context context;
    String[] data;

    private static LayoutInflater inflater = null;
    public IncidentListAdapter(Context context, String[] data)
    {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public IncidentListAdapter(Context context, LinkedList<Item> incidentData){
        this.context = context;
        this.incidentData = incidentData;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {

        return incidentData.size();
    }

    @Override
    public Object getItem(int position) {

        return incidentData.get(position).toString();
    }

    public Item getEvent(int position){
        return incidentData.get(position);
    };

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.item_layout, null);
        Item item = getEvent(position);
        TextView text;
        text = (TextView) view.findViewById(R.id.text1);
        text.setText(item.getTitle());
        return view;
    }
}