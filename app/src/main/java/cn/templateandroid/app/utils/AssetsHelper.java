package cn.templateandroid.app.utils;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class AssetsHelper {

	public static String readFile(Context context, String fileName) {
		InputStreamReader inputReader = null;
		BufferedReader bufReader = null;
		StringBuffer resultBuf = new StringBuffer(1024);
		try {
			String lineStr = "";
			inputReader = new InputStreamReader(context.getAssets().open(fileName));
			bufReader = new BufferedReader(inputReader);
			while((lineStr = bufReader.readLine()) != null){
				resultBuf.append(lineStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(inputReader != null){
					inputReader.close();
				}
				if(bufReader != null){
					bufReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultBuf.toString();
	}

	public static JSONObject getJSONObject(Context context, String filename) {
		JSONObject result = null;
		String resultStr = readFile(context, filename);
		if(resultStr != null){
			result = JSONObject.parseObject(resultStr);
		}
		return result;
	}

	public static JSONArray getJSONArray(Context context, String filename) {
		JSONArray result = null;
		String resultStr = readFile(context, filename);
		if(resultStr != null){
			result = JSONArray.parseArray(resultStr);
		}
		return result;
	}
    
}
