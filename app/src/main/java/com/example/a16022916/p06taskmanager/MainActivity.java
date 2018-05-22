package com.example.a16022916.p06taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public DatabaseHelper db = new DatabaseHelper(this);

    int requestCodeForAdd = 1;

    int reqCode = 12345;
    ListView lvItem;
    Button btnAdd;

    ArrayList<Item> alItems;
    ArrayList<String> alstrItems;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItem = findViewById(R.id.mainLvTask);
        btnAdd = findViewById(R.id.mainAddTask);

        alstrItems = new ArrayList<>();
        alItems = new ArrayList<Item>();
        alItems = db.getAllItems();

        for (Item item : alItems){
            String strList = String.valueOf(item.getId()) + " " + item.getName() + "\n" + item.getDescription() + "\n"+item.getTime();
            alstrItems.add(strList);
        }

        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,alstrItems);
        lvItem.setAdapter(aa);

        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, alItems.get(i).getTime());

                Context thisContext = MainActivity.this;
                Class toClass = ScheduledNotificationReceiver.class;
                Intent intent = new Intent(thisContext,toClass);
                intent.putExtra("name", alItems.get(i).getName());
                intent.putExtra("desc", alItems.get(i).getDescription());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(thisContext,
                        reqCode,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context thisContext = MainActivity.this;
                Class toClass = AddActivity.class;
                Intent intent = new Intent(thisContext,toClass);
                intent.putExtra("arrayList", alItems);
                startActivityForResult(intent, requestCodeForAdd);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Only handle when 2nd activity closed normally
        //  and data contains something
        if(resultCode == RESULT_OK){
            if (data != null) {
                if(requestCode == requestCodeForAdd){
                    DatabaseHelper mydb = new DatabaseHelper(MainActivity.this);
                    alItems.clear();
                    alItems.addAll(mydb.getAllItems());
                    mydb.close();
                    aa.notifyDataSetChanged();
                }
            }
        }
    }
}
