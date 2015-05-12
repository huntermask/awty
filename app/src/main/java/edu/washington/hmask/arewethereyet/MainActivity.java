package edu.washington.hmask.arewethereyet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private AlarmManager alarmManager;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean alarmRunning = (PendingIntent.getBroadcast(this, 0,
                new Intent("edu.washington.hmask.AWTY_ALARM"),
                PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning) {
            stopMessages();
        } else {
            startMessages();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startMessages() {
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        EditText messageField = (EditText) findViewById(R.id.messageField);
        String message = messageField.getText().toString();
        intentAlarm.putExtra("message", message);
        EditText phoneField = (EditText) findViewById(R.id.phoneField);
        String phoneNumber = phoneField.getText().toString();
        intentAlarm.putExtra("phone", phoneNumber);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intentAlarm, 0);
        EditText intervalField = (EditText) findViewById(R.id.intervalField);
        long interval = Long.getLong(intervalField.getText().toString()) * 60 * 1000;
        alarmManager.setRepeating(AlarmManager.RTC, interval, interval, alarmIntent);
        startButton.setText("Stop");
    }

    private void stopMessages() {
        alarmManager.cancel(PendingIntent.getBroadcast(this, 0,
                new Intent("edu.washington.hmask.AWTY_ALARM"),
                PendingIntent.FLAG_NO_CREATE));
        startButton.setText("Start");
    }
}
