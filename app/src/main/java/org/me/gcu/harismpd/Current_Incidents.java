package org.me.gcu.harismpd;
/*

Name: Haris Raheem Ajmal
matric number: S2026710
 */
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class Current_Incidents extends AppCompatActivity {
    private ProgressDialog prog;
    private String TAG = Current_Incidents.class.getSimpleName();
    LinkedList<Item> incidentList;
    LinkedList<Item> titles;
    ListView incidentViewList;
    XMLparser xmlParser = new XMLparser();
    private FloatingActionButton btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_incidents);
        prog = ProgressDialog.show(this, "Traffic Scotland","data is being loaded...");
        new LoadDataTask().execute();
        setViews();
    }

    public void setViews(){
        btnRefresh = (FloatingActionButton) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show progress dialog
                prog = ProgressDialog.show(v.getContext(), "Traffic Scotland","loading data...");
                new LoadDataTask().execute();
            }
        });
        incidentViewList = (ListView) findViewById(R.id.current_incidents_list);
        incidentViewList.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View View, int position,
                                    long id) {
                Log.e("ITEM CLICKED", Integer.toString(position));
                String item = (String) parent.getItemAtPosition(position);
                Item choice = titles.get(position);
                item = titles.get(position).getTitle();
                //show roadwork information on dialog
                showCustomDialog(choice);
            }
        });
    }
    private void populateList() {
        titles = new  LinkedList<Item>();

        for (Item anItem : incidentList) {
            titles.add(anItem);
        }

        incidentViewList.setAdapter(new IncidentListAdapter(this, titles));
    }

    private void searchIncidents(String query){
        titles = new  LinkedList<Item>();
        for (Item anItem : incidentList) {
            if(anItem.getTitle().toLowerCase().contains(query.toLowerCase())){
                titles.add(anItem);
            }
        }
        incidentViewList.setAdapter(new IncidentListAdapter(this, titles));
    }
    private void showCustomDialog(Item event)
    {
        // Custom dialog setup
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.incidents_dialog_layout);
        dialog.setTitle("    Incident Details");

        SimpleDateFormat df = new SimpleDateFormat("E dd.MM.yyyy");

        TextView text = (TextView) dialog.findViewById(R.id.infoView);
        text.setText(event.getTitle());

        TextView txtMoreInfo = (TextView) dialog.findViewById(R.id.txtMoreInfo);
        txtMoreInfo.setText(event.getDescription());

        TextView txtPubDate = (TextView) dialog.findViewById(R.id.txtPubDate);
        txtPubDate.setText(event.getPubDate());

        TextView txtGeoLocation = (TextView) dialog.findViewById(R.id.txtGeoLocation);
        txtGeoLocation.setText(event.getGeorssPoint());


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private class LoadDataTask extends AsyncTask<String, Integer, LinkedList<Item>> {

        @Override
        protected LinkedList<Item> doInBackground(String... params) {
            String result = "";
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e(TAG,"in run");

            try
            {
                Log.e(TAG,"in try");
                aurl = new URL("https://trafficscotland.org/rss/feeds/currentincidents.aspx");
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e(TAG,inputLine);

                }
                in.close();

                if(result ==""){
                    Log.e("XML NOT FOUND", "CONNECTION FAILED");
                }
            }
            catch (IOException ae)
            {
                Log.e(TAG, "ioexception");
            }

            xmlParser.parseXML(result);
            return xmlParser.getItemList();
        }
        protected void onPostExecute(LinkedList<Item> list) {
            if(prog.isShowing()){
                prog.dismiss();
            }
            incidentList = list;
            //set data to listview
            populateList();
        }
    }
}