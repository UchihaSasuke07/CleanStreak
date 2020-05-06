package com.example.cleanstreak;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView counterTxt;
    private Button minusBtn;
    private Button plusBtn;
    private Button resetBtn;
    private int counter;

    private View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            switch(view.getId()){
                case R.id.minusBtn:
                    minusCounter();
                    break;
                case R.id.plusBtn:
                    plusCounter();
                    break;
                case R.id.resetBtn:
                    initCounter();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counterTxt=(TextView) findViewById(R.id.counterTxt);
        minusBtn = (Button)findViewById(R.id.minusBtn);
        minusBtn.setOnClickListener(clickListener);
        plusBtn = (Button)findViewById(R.id.plusBtn);
        plusBtn.setOnClickListener(clickListener);
        resetBtn = (Button)findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(clickListener);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("instance-id", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.w("FMX TOKEN", msg);

                    }
                });

        initCounter();
    }

    private void initCounter(){
        counter = 0;
        counterTxt.setText(counter + "");
    }

    private void plusCounter(){
        counter++;
        counterTxt.setText(counter + "");
    }

    private void minusCounter(){
        counter--;
        counterTxt.setText(counter + "");
    }
}