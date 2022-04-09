package org.me.gcu.harismpd;

/*

Name: Haris Raheem Ajmal
Matric Number: S2026710
 */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private Button incidentsButton;
    private Button plannedButton;
    private Button roadButton;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        incidentsButton = (Button)findViewById(R.id.incidentsButton);
        plannedButton = (Button)findViewById(R.id.plannedButton);
        roadButton = (Button)findViewById(R.id.roadButton);
        exitButton = (Button)findViewById(R.id.exitButton);
      

        incidentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Current_Incidents.class);
                startActivity(intent);
            }
        });

        plannedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Planned_Roadworks.class);
                startActivity(intent);
            }
        });

        roadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Road_works.class);
                startActivity(intent);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)     {
                // Check for exit button. Pop up dialogue if found
                if (v == exitButton)
                {
                    showtbDialog();
                }
            }
            private void showtbDialog()
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you confirm you want to close this App?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getApplicationContext(), "App closed", Toast.LENGTH_SHORT).show();
                        MainActivity.this.finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getApplicationContext(), "You selected No", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}