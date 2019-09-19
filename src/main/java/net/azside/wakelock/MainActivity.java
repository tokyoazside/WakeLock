package net.azside.wakelock;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;




    public class MainActivity extends Activity implements OnClickListener {



        final static String TAG = "WakeLock1";
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    Button lockButton;
    Button unlockButton;
    TextView typeTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeTextView = (TextView)findViewById(R.id.lock_type);
        lockButton = (Button)findViewById(R.id.lock_button);
        unlockButton = (Button)findViewById(R.id.unlock_button);
        lockButton.setOnClickListener(this);
        unlockButton.setOnClickListener(this);
        enableLockButton(true);

        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.lock_button:
                acquireLock();
                break;
            case R.id.unlock_button:
                releaseLock();
                break;
        }

    }

    protected void acquireLock(){
        new AlertDialog.Builder(this)
                .setTitle("Lock Type")
                .setItems(R.array.locktype,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "which = " + which);
                                int flag = 0;
                                switch(which){
                                    case 0:
                                        flag = PowerManager.PARTIAL_WAKE_LOCK;
                                        break;
                                    case 1:
                                        flag = PowerManager.SCREEN_DIM_WAKE_LOCK;
                                        break;
//                                    case 2:
//                                        flag = PowerManager.SCREEN_BRIGHT_WAKE_LOCK;
//                                        break;
                                    case 3:
                                        flag = PowerManager.FULL_WAKE_LOCK;
                                        break;
                                }

//                                wakeLock = powerManager.newWakeLock(flag,TAG);
                                wakeLock = powerManager.newWakeLock(1,"WakeLock1";);
                                wakeLock.acquire();
                                enableLockButton(false);
                                showLockType(flag);
                            }
                        })
                .show();
    }

    protected void releaseLock(){
        wakeLock.release();
        enableLockButton(true);
        showLockType(0);
    }

    protected void enableLockButton(boolean b){
        lockButton.setEnabled(b);
        unlockButton.setEnabled(!b);
    }

    protected void showLockType(int flag){
        String txt = "";
        switch(flag){
            case PowerManager.PARTIAL_WAKE_LOCK:
                txt = "PARTIAL_WAKE_LOCK";
                break;
            case PowerManager.SCREEN_DIM_WAKE_LOCK:
                txt = "SCREEN_DIM_WAKE_LOCK";
                break;
            case PowerManager.SCREEN_BRIGHT_WAKE_LOCK:
                txt = "SCREEN_BRIGHT_WAKE_LOCK";
                break;
            case PowerManager.FULL_WAKE_LOCK:
                txt = "FULL_WAKE_LOCK";
                break;
        }
        typeTextView.setText(txt);
    }}

