package edu.washington.hmask.arewethereyet;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by huntermask on 5/12/15.
 */
public class AlarmService extends IntentService {

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String message = intent.getStringExtra("message");
        final String phone = intent.getStringExtra("phone");

        Handler h = new Handler(getApplicationContext().getMainLooper());

        // Send the message using logic run in the main thread
        h.post(new Runnable() {
            @Override
            public void run() {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone.replaceAll("[^\\+0-9]", ""), null, message, null, null);
            }
        });

    }
}


