package com.example.user.myapplication1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity1 extends AppCompatActivity {

    protected EditText tall;
    protected EditText weight;
    protected RadioButton boy;
    protected RadioButton girl;
    protected RadioGroup radioGroup;
    protected Button button;
    protected TextView standardWeight;
    protected TextView bodyFat;
   int gender =1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        tall = (EditText) findViewById(R.id.tall);
        weight=(EditText) findViewById(R.id.weight);
        boy = (RadioButton) findViewById(R.id.boy);
        girl = (RadioButton)findViewById(R.id.girl);
        radioGroup = (RadioGroup)  findViewById(R.id.radioGroup);
        button= (Button) findViewById( R.id.button);
        standardWeight=(TextView) findViewById(R.id.standardweight);
        bodyFat=(TextView) findViewById(R.id.bodyFat);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.boy:
                        gender = 1 ;
                        break;
                    case  R.id.girl:
                        gender =2;
                        break;

                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAsyncTask();
            }

        });

    }


    private void runAsyncTask() {

        new AsyncTask<Void,Integer,Boolean>(){
            private ProgressDialog dialog = new ProgressDialog(MainActivity1.this);
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                dialog.setMessage("計算中...");
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.show();
            }
            @Override
            protected Boolean doInBackground(Void ... voids){
                int progress = 0 ;
                 while (progress <=100){
                     try{
                         Thread.sleep(50);
                         publishProgress(Integer.valueOf(progress));
                         progress++;
                     } catch (InterruptedException e){
                         e.printStackTrace();
                     }
                 }
                 return  true;
            }
            @Override
            protected void onProgressUpdate(Integer... values){
                super.onProgressUpdate(values);
                dialog.setProgress(values[0]);
            }
            @Override
            protected void onPostExecute(Boolean status){
                dialog.dismiss();
                double cal_Tall = Double.parseDouble(tall.getText().toString());
                double cal_weight = Double.parseDouble(weight.getText().toString());
                double cal_standardWeight = 0;
                double cal_bodyFat=0;
                if (gender==1){
                    cal_standardWeight =22*cal_Tall/100*cal_Tall/100;
                    cal_bodyFat = (cal_weight-(0.88*cal_standardWeight))/cal_weight*100;
                }
                if(gender==2){
                    cal_standardWeight =22*cal_Tall/100*cal_Tall/100;
                    cal_bodyFat = (cal_weight-(0.82*cal_standardWeight))/cal_weight*100;
                }
                standardWeight.setText(String.format("%.2f",cal_standardWeight));
                bodyFat.setText(String.format("%.2f",cal_bodyFat));
            }
        }.execute();



    }


}
