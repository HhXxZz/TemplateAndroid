package cn.templateandroid.app.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.templateandroid.app.conf.MsgWhat;


public class APKHelper {
	
	public void install(Context context, File file) {
		if(file != null){
			 /*获取当前系统的android版本号*/
			int currentapiVersion = android.os.Build.VERSION.SDK_INT;
			if(currentapiVersion<24){
				Intent intent = new Intent();
				// 执行动作
				intent.setAction(Intent.ACTION_VIEW);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// 执行的数据类型
				intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
				// 编者按：此处Android应为android，否则造成安装不了
				context.startActivity(intent);
			}else {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				Uri bmpUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
				intent.setDataAndType(bmpUri, "application/vnd.android.package-archive");
				intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				context.startActivity(intent);
			}
		}
	}

	public File download(String Url, ProgressDialog pd) throws Exception {
		Log.d("UpdateHelper", "downloadFile URL：" + Url);
		// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			URL url = new URL(Url);
			int pos = Url.lastIndexOf((int) '/');
			String packageName = Url.substring(pos + 1);
			if (StringHelper.isEmpty(packageName)) {
				return null;
			}
			Log.d("UpdateHelper", "downloadFile 下载文件名：" + packageName);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// 获取到文件的大小
			pd.setMax(conn.getContentLength()/1024);
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(),
					packageName);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// 获取当前下载量
				pd.setProgress(total/1024);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}

	// 从服务器下载apk:
	public File download(String Url, Handler handle) throws Exception {
		Log.d("UpdateHelper", "downloadFile URL：" + Url);
		// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		Log.d("UpdateHelper",
				"getExternalStorageState："
						+ Environment.getExternalStorageState());
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(Url);
			int pos = Url.lastIndexOf((int) '/');
			String packageName = Url.substring(pos + 1);
			Log.d("UpdateHelper", "downloadFile 下载文件名：" + packageName);
			if (StringHelper.isEmpty(packageName)) {
				return null;
			}

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// 获取到文件的大小
			int maxLength = conn.getContentLength();

			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(),
					packageName);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// 获取当前下载量
				int progress = (int) Math.floor(total * 100 / maxLength);

				Log.d("XProcessor sendMessage", "progress" + progress);
				Message msg = new Message();
				msg.what = MsgWhat.OK;
				msg.obj = new Integer(progress);
				handle.sendMessage(msg);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}


    
}
