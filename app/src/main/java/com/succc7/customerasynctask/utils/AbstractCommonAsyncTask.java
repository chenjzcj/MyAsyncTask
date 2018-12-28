package com.succc7.customerasynctask.utils;

import android.os.Handler;
import android.os.Message;

/**
 * 自定义AsyncTask
 *
 * @author Created by Felix.Zhong on 2018-7-30 17:26:07
 */
public abstract class AbstractCommonAsyncTask {
    /**
     * 子线程任务执行完毕的标识
     */
    private static final int DOINBACKGROUND = 0;

    /**
     * 执行任务之前调用此方法,运行在主线程中
     */
    public abstract void onPreExecute();

    /**
     * 运行在子线程中,需要执行耗时任务
     */
    public abstract String doInBackground(String... params);

    /**
     * 执行任务之后调用此方法,运行在主线程中
     */
    public abstract void onPostExecute(String result);

    private InternalHandler mHandler;

    /**
     * 开始执行异步任务
     *
     * @param params 参数
     */
    public void execute(final String... params) {
        onPreExecute();
        if (mHandler == null) {
            mHandler = new InternalHandler();
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                String result = doInBackground(params);
                mHandler.obtainMessage(DOINBACKGROUND, result).sendToTarget();
            }
        }).start();
    }

    class InternalHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DOINBACKGROUND) {
                onPostExecute((String) msg.obj);
            }
        }
    }
}
