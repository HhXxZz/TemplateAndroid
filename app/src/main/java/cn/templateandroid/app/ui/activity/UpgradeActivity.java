package cn.templateandroid.app.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import java.io.File;

import cn.templateandroid.app.R;
import cn.templateandroid.app.base.BaseActivity;
import cn.templateandroid.app.utils.APKHelper;

public class UpgradeActivity extends BaseActivity {
	public static final String EXTRAS_KEY_VERSION = "version";
	public static final String EXTRAS_KEY_APKURL = "apkurl";

	private String mVersion = null;
	private String mApkurl = null;
	private ProgressDialog mDialog = null;
	private DownloadThread mThread = null;

	@Override
	public void onClick(View v) {
		int res = v.getId();
		switch (res) {
		case R.id.upgrade_next_btn:
			this.finish();
			break;
		case R.id.upgrade_now_btn:
			installApk();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upgrade);

		findViewById(R.id.upgrade_next_btn).setOnClickListener(this);
		findViewById(R.id.upgrade_now_btn).setOnClickListener(this);

		TextView versionName = (TextView) this.findViewById(R.id.version_name_tv);

		Intent intent = this.getIntent();
		mVersion = intent.getStringExtra(EXTRAS_KEY_VERSION);
		mApkurl = intent.getStringExtra(EXTRAS_KEY_APKURL);

		versionName.setText("版本：V" +mVersion);
	}

	@Override
	protected int getLayoutId() {
		return 0;
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initInjector() {

	}

	public void installApk(){
		mDialog = new ProgressDialog(this);
		mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mDialog.setMessage("正在下载");
		mDialog.setProgressNumberFormat("%1dKB / %2dKB");
		mDialog.show();
		
		if(mThread == null){
			mThread = new DownloadThread();
			mThread.start();
		}
	}
	
	class DownloadThread extends Thread {
		@Override
		public void run() {
			try {
				APKHelper helper = new APKHelper();
				File file = helper.download(mApkurl, mDialog);
				helper.install(UpgradeActivity.this, file);
				mDialog.dismiss();
				UpgradeActivity.this.finish();
			} catch (Exception e) {
				mDialog.dismiss();
				runOnUiThread(new Runnable(){
					public void run(){
						showToast("下载失败");
						UpgradeActivity.this.finish();
					} 
				});
				e.printStackTrace();
			}
		}
	}
}
