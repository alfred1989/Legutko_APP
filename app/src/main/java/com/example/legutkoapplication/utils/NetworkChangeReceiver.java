package com.example.legutkoapplication.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import com.example.legutkoapplication.R;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private Activity activity;

    public NetworkChangeReceiver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        int status = NetworkUtil.getConnectivityStatusString(context);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            TextView textView = (TextView) activity.findViewById(R.id.testzasiegu);
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                textView.setText("No Network Connection");
            } else {
                textView.setText("Network Connection");
            }
        }
    }


}
