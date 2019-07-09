package ca.etsmtl.applets.etsmobile.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class BroadCastReciever extends BroadcastReceiver {

    boolean wasNotConnected=false;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
          boolean isNotConnect = intent.getBooleanExtra( ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
          if(isNotConnect)
          {
              wasNotConnected=true;
              Toast.makeText(context,"no may",Toast.LENGTH_SHORT).show();
          }
          else if(!isNotConnect && wasNotConnected)
              Toast.makeText(context,"connected may",Toast.LENGTH_SHORT).show();
        }
    }
}
