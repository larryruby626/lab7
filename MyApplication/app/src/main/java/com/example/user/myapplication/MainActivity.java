package com.example.user.myapplication;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected Button start;
    protected SeekBar rabbit_SeekBar, tortoise_SeekBar;
    int rabbit_count = 0, tortoise_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        rabbit_SeekBar = (SeekBar) findViewById(R.id.rabbit_SeekBar);
        tortoise_SeekBar = (SeekBar) findViewById(R.id.tortoise_SeekBar);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "開始", Toast.LENGTH_LONG).show();
                runOnUiThread();
                runAsyncTask();
            }
        });
    }

    private void runOnUiThread() {
        rabbit_count = 0;
        new Thread() {
            public void run() {
                do {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rabbit_count += (int) (Math.random() * 3);
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);

                } while (rabbit_count <= 100);

            }
        }.start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    rabbit_SeekBar.setProgress(rabbit_count);
                    break;
            }
            if (rabbit_count >= 100)
                if (tortoise_count < 100)
                    Toast.makeText(MainActivity.this, "兔子完成", Toast.LENGTH_LONG).show();

        }
    };

    private void runAsyncTask() {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                tortoise_count = 0;
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                do {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    tortoise_count += (int) (Math.random() * 3);
                    publishProgress(tortoise_count);

                } while (tortoise_count <= 100);
                return true;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                tortoise_SeekBar.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Boolean status) {
                if (rabbit_count < 100)
                    Toast.makeText(MainActivity.this, "烏龜獲勝", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}