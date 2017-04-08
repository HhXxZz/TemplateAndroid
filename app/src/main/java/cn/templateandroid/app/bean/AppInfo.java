package cn.templateandroid.app.bean;

import android.content.pm.PackageInfo;

public class AppInfo {

	public static final String KEY_PEERID = "peerid";
	public static final String KEY_IMEI = "imei";
	public final static String KEY_FIRSTLANUCH = "firstlanuch";
	public static final String KEY_VERCODE = "vercode";
	public static final String KEY_VERNAME = "vername";
	public static final String KEY_APKURL = "apkurl";
	public static final String KEY_CLIENTTYPE = "clienttype";

	public static final String KEY_CLIENTTYPE_ANDROID = "android";

	private String peerid;
	private String imei;
	private boolean firstlaunch;
	
	private int vercode;
	private String vername;
	private boolean upgrade;
	private String apkurl;
	private String packagename;

	public String getPeerid() {
		return peerid;
	}

	public void setPeerid(String peerid) {
		this.peerid = peerid;
	}

	public int getVercode() {
		return vercode;
	}

	public void setVercode(int vercode) {
		this.vercode = vercode;
	}

	public String getVername() {
		return vername;
	}

	public void setVername(String vername) {
		this.vername = vername;
	}

	public boolean isUpgrade() {
		return upgrade;
	}

	public void setUpgrade(boolean upgrade) {
		this.upgrade = upgrade;
	}

	public String getApkurl() {
		return apkurl;
	}

	public void setApkurl(String apkurl) {
		this.apkurl = apkurl;
	}
	
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPkgInfo(PackageInfo pkgInfo) {
		//this.pkgInfo = pkgInfo;
		this.setVercode(pkgInfo.versionCode);
		this.setVername(pkgInfo.versionName);
		this.setPackagename(pkgInfo.packageName);
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public void setFirstLaunch(boolean firstlaunch){
		this.firstlaunch = firstlaunch;
	}
	
	public boolean isFirstLaunch(){
		return firstlaunch;
	}
	
	public void setNotFirstLaunch(){
		firstlaunch = false;
	}
	
	
	

}

