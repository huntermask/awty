package edu.washington.hmask.arewethereyet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by huntermask on 5/11/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        String phone = intent.getStringExtra("phone");
        Toast.makeText(context, phone + ": " + message, Toast.LENGTH_LONG).show();
    }
}
