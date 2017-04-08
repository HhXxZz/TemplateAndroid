package cn.templateandroid.app.base;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.os.Vibrator;
import android.util.Log;

import java.util.List;

import cn.templateandroid.app.bean.AppInfo;
import cn.templateandroid.app.bean.UserInfo;
import cn.templateandroid.app.conf.BaseConfig;
import cn.templateandroid.app.di.component.ApplicationComponent;
import cn.templateandroid.app.di.component.DaggerApplicationComponent;
import cn.templateandroid.app.di.module.ApplicationModule;


public class BaseApplication extends Application {
	
	private final String TAG = "XApplication";

	private static BaseApplication instance = null;

	private int appPid = 0;
	private boolean isMainProcess = false;

	private final AppInfo appInfo = new AppInfo();
	
	private final UserInfo userInfo = new UserInfo();
	public Vibrator mVibrator;

	private ApplicationComponent mApplicationComponent;

	public static BaseApplication getInstance(){
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		initApplicationComponent();
		instance = this;
		checkProcess();
        if(isMainProcess){

		}
	}


	// Fixme
	private void initApplicationComponent() {
		mApplicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.build();
	}

	public ApplicationComponent getApplicationComponent() {
		return mApplicationComponent;
	}

	public void checkProcess() {
		appPid = android.os.Process.myPid();
		ActivityManager mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> processList = mActivityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : processList) {
			if (appProcess.pid == appPid) {

			}
		}
	}
	public AppInfo getAppInfo() {
		return this.appInfo;
	}

	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public String getAppPeerid(){
		return this.appInfo.getPeerid();
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void initPolicy(){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			StrictMode.setThreadPolicy(
					new StrictMode.ThreadPolicy.Builder()
							.detectCustomSlowCalls()
							.detectDiskReads()
							.detectDiskWrites()
							.detectNetwork()
							.penaltyLog()
							.penaltyFlashScreen()
							.build()
			);
			
			StrictMode.setVmPolicy(
					new StrictMode.VmPolicy.Builder()
							.detectLeakedSqlLiteObjects()
							.detectLeakedClosableObjects()
							.penaltyLog()
							.build()
			);
		}
	}

}
