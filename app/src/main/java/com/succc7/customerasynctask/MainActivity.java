package com.succc7.customerasynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.succc7.customerasynctask.utils.AbstractCommonAsyncTask;

/**
 * @author Created by Felix.Zhong on 2018-7-30 17:26:07
 */
public class MainActivity extends Activity {

    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tv_info);
    }

    private void setInfo(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvInfo.setText(new StringBuilder(tvInfo.getText().toString().trim()).append("\n").append(msg));
            }
        });
    }

    public void click(View v) {
        new AbstractCommonAsyncTask() {

            @Override
            public void onPreExecute() {
                printThreadName("当前是onPreExecute方法");
            }

            @Override
            public String doInBackground(String... params) {
                printThreadName("当前是doInBackground方法");
                SystemClock.sleep(5000);
                setInfo("参数 : " + params[0] + "," + params[1]);
                return "请求成功";
            }

            @Override
            public void onPostExecute(String result) {
                setInfo("handleMessage:子线程任务执行完成:" + result);
                printThreadName("当前是onPostExecute方法");
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }

        }.execute("参数1", "参数2");
    }

    private void printThreadName(String msg) {
        String threadName = Thread.currentThread().getName();
        setInfo("当前线程是 : " + threadName + ",消息 :" + msg);
    }
}
