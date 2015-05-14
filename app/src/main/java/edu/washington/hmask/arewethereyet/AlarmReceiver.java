package edu.washington.hmask.arewethereyet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by huntermask on 5/12/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Create a new Intent with the same extras payload, but pointing at the AlarmService
        Intent i = new Intent(context, AlarmService.class);
        i.putExtras(intent);

        // Fire the intent
        context.startService(i);
    }
}
