package cn.templateandroid.app.utils;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;


public abstract class DeviceHelper {
	
	private static boolean hasSdcard = false;
    
	public static String getImei(Context context){
		TelephonyManager localTelephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		try{
			return localTelephonyManager.getDeviceId();
		}catch (Exception localException){
			localException.printStackTrace();
	    }
		return "";
	}

	public static String getBuildModel(Context context){
		try{
			return android.os.Build.MODEL;
		}catch (Exception localException){
			localException.printStackTrace();
	    }
		return null;
	}
	
	public static boolean hasSdcard(){
		return hasSdcard;
	}

	public static void sdcard(Context context){
		if (("mounted".equals(Environment.getExternalStorageState())) && (Environment.getExternalStorageDirectory().canWrite())){
			String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();
			String str1 = basePath + "/.moo";
			String str2 = basePath + "/download";
			String str3 = basePath + "/Android";
			//SP.putString(context, "sdcard_root_path", str1);
			//SP.putString(context, "download_path", str2);
			//SP.putString(context, "uuid_path", str3);
			File localFile1 = new File(str1);
			if (!(localFile1.exists())){
				localFile1.mkdirs();
			}
			File localFile2 = new File(str1 + "/.nomedia");
			if (!(localFile2.exists()))
				localFile2.mkdirs();
			File localFile3 = new File(str1 + "/icons");
			if (!(localFile3.exists()))
				localFile3.mkdirs();
			File localFile4 = new File(str2);
			if (!(localFile4.exists()))
				localFile4.mkdirs();
			File localFile5 = new File(str2 + "/moo");
			if (!(localFile5.exists()))
				localFile5.mkdirs();
			File localFile6 = new File(str3 + "/.money");
			if (!(localFile6.exists()))
				localFile6.mkdirs();
			hasSdcard = true;
			return;
		}
	    Log.e("sdcard", "no sd card found");
	  }
    
}
