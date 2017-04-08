package cn.templateandroid.app.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class DownloadHelper {

	private final static String TAG = "DownloadHelper";

	private final static String SAVE_FOLDER_NAME = "truemerger";
	private static String mStoragePath = null;

	public DownloadHelper(Context context){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			mStoragePath = Environment.getExternalStorageDirectory().getPath();
		}else{
			mStoragePath = context.getCacheDir().getPath();;
		}
		if(!mStoragePath.endsWith(File.separator)){
			mStoragePath = mStoragePath + File.separator + SAVE_FOLDER_NAME;
		}
	}

	private String getStorageDirectory(){
		return mStoragePath;
	}


	public void savaFile(String oldPath, String filename) throws IOException {
		try {
			int byteRead;
			File oldFile = new File(oldPath);
			if (oldFile.exists()) {
				String path = getStorageDirectory();
				File folderFile = new File(path);
				if(!folderFile.exists()){
					folderFile.mkdir();
				}
				String newPath = path + File.separator + filename;
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ( (byteRead = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteRead);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
