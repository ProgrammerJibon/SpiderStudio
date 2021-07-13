package app.jibon.spider;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {
    Activity activity;
    String text;
    int drawable;
    public CustomToast(Activity applicationContext, String s, int ic_baseline_notifications_none_24) {
        this.activity = applicationContext;
        this.text = String.valueOf(s);
        this.drawable = ic_baseline_notifications_none_24;
        View view = activity.getLayoutInflater().inflate(R.layout.toast, activity.findViewById(R.id.Custom_toast), false);
        ((TextView) view.findViewById(R.id.Custom_toast_text)).setText(text);
        ((ImageView) view.findViewById(R.id.Custom_toast_icon)).setImageResource(drawable);
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
        ((Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);
    }
}