package in.sibin.expirydate;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.util.Log;

public class ShowNotification extends IntentService {

    public static String PRODUCT_NAME = "product_name";
    private static String LOG_TAG = "ShowNotification";

    public ShowNotification() {
        super("ShowNotification");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(LOG_TAG, "NotificationService started");

        Notification n  = new Notification.Builder(this)
                .setContentTitle("Proudct expiring")
                .setContentText(intent.getExtras().getString(PRODUCT_NAME) + " will expire tomorrow.")
                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);


    }

}
