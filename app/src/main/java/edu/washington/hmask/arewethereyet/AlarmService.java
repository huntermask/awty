package edu.washington.hmask.arewethereyet;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
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

        h.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), phone + ": " + message, Toast.LENGTH_LONG).show();
            }
        });

    }
}


