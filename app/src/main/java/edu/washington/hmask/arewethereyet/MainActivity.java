package edu.washington.hmask.arewethereyet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, TextWatcher {

    AlarmManager alarmManager;
    Button startButton;
    EditText messageField;
    EditText phoneField;
    EditText intervalField;
    private static final String BROADCAST_ID = "edu.washington.hmask.AWTY_ALARM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        phoneField = (EditText) findViewById(R.id.phoneField);
        phoneField.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        phoneField.addTextChangedListener(this);

        messageField = (EditText) findViewById(R.id.messageField);
        messageField.addTextChangedListener(this);

        intervalField = (EditText) findViewById(R.id.intervalField);
        intervalField.addTextChangedListener(this);

        if (isAlarmRunning()) {
            startButton.setText("Stop");
            messageField.setEnabled(false);
            phoneField.setEnabled(false);
            intervalField.setEnabled(false);
        }

        validateFields();
    }

    @Override
    public void onClick(View v) {
        if (isAlarmRunning()) {
            stopMessages();
        } else {
            startMessages();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        validateFields();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    private void validateFields() {
        boolean isValid = true;

        if (!phoneField.getText().toString().matches("\\(\\d{3}\\)\\s\\d{3}-\\d{4}")) {
            isValid = false;
        }

        if (messageField.getText().toString() == null || messageField.getText().toString().isEmpty()) {
            isValid = false;
        }

        if (!intervalField.getText().toString().matches("\\d+") || intervalField.getText().toString().equals("0")) {
            isValid = false;
        }


        if (isValid && !isAlarmRunning() || isAlarmRunning()) {
            startButton.setEnabled(true);
        } else if (!isValid && !isAlarmRunning()) {
            startButton.setEnabled(false);
        }
    }

    private boolean isAlarmRunning() {
        return (PendingIntent.getBroadcast(this, 0,
                new Intent(getApplicationContext(), AlarmReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void startMessages() {
        Intent intentAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);
        String message = messageField.getText().toString();
        intentAlarm.putExtra("message", message);
        String phoneNumber = phoneField.getText().toString();
        intentAlarm.putExtra("phone", phoneNumber);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
        long interval = Long.parseLong(intervalField.getText().toString()) * 60 * 1000;
        alarmManager.setRepeating(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis() + interval, interval, alarmIntent);
        startButton.setText("Stop");
        messageField.setEnabled(false);
        phoneField.setEnabled(false);
        intervalField.setEnabled(false);
    }

    private void stopMessages() {
        PendingIntent pi = PendingIntent.getBroadcast(this, 0,
                new Intent(getApplicationContext(), AlarmReceiver.class),
                PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pi);
        pi.cancel();
        startButton.setText("Start");
        messageField.setEnabled(true);
        phoneField.setEnabled(true);
        intervalField.setEnabled(true);
    }
}
