package cn.templateandroid.app.base;

import android.os.Looper;
import android.util.Log;

import java.lang.Thread.UncaughtExceptionHandler;

import cn.templateandroid.app.bean.AppInfo;


public class BaseException extends Exception implements UncaughtExceptionHandler {

	private final static String TAG = "BaseException";

	private UncaughtExceptionHandler mDefaultHandler;

	private BaseException(){
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	private final AppInfo appInfo = new AppInfo();

	public static BaseException getAppExceptionHandler(){
		return new BaseException();
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.d(TAG, "Error : " + ex.getMessage());
		if(!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
            try {  
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error : ", e);
            }  
            //BaseApplication.getInstance().killSelfProcess();
        }
	}
	public AppInfo getAppInfo() {
		return this.appInfo;
	}
	private boolean handleException(Throwable ex) {
		if(ex == null) {
			return false;
		}

		final String crashReport = getCrashReport(ex);
		Log.d(TAG, "crashReport:" + crashReport);
		new Thread() {
			public void run() {
				Looper.prepare();
				Looper.loop();
			}
		}.start();
		return true;
	}

	private String getCrashReport(Throwable ex) {
		AppInfo appInfo = BaseApplication.getInstance().getAppInfo();
		StringBuffer exceptionStr = new StringBuffer();
		exceptionStr.append("Version: "+appInfo.getVername()+"("+appInfo.getVercode()+")\n");
		exceptionStr.append("Android: "+android.os.Build.VERSION.RELEASE+"("+android.os.Build.MODEL+")\n");
		exceptionStr.append("Exception: "+ex.getMessage()+"\n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			exceptionStr.append(elements[i].toString()+"\n");
		}
		return exceptionStr.toString();
	}
}
