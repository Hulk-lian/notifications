package com.jtcode.callbroadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;

public class Call_broadcastReceiver extends BroadcastReceiver {
    private static final int CALLNOTIFICATION=1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle= intent.getExtras();
        if(bundle!=null){
            String state=bundle.getString(TelephonyManager.EXTRA_STATE);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                String number=bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

                Intent intent1= new Intent(context,Telephone_Activity.class);
                intent1.putExtra("number", number);
                PendingIntent  pendingIntent= PendingIntent.getActivity(context,CALLNOTIFICATION,intent1,PendingIntent.FLAG_ONE_SHOT);//es un token que contiene un intent implicito//algo pendiente de que se ejecute un intent
                NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
                builder.setContentTitle(context.getApplicationInfo().name).setContentText("llamada del numero: "+number)
                        .setSmallIcon(R.mipmap.ic_launcher);

                //parametros extra
                builder.setDefaults(Notification.DEFAULT_ALL);

                //Añadir el objeto PendingItem a la notificacion
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);//para que se cierre

                //Añadir la nitificacion al notification manager
                NotificationManager notificationManager= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(CALLNOTIFICATION,builder.build());

            }
        }
    }
}
