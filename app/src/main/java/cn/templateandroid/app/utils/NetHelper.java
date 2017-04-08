package cn.templateandroid.app.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


/**
 * 判断网络的的工具类
 *
 */
public abstract class NetHelper {

    public static boolean isWifiNet(Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connManager){
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo.isAvailable() && ConnectivityManager.TYPE_WIFI==networkInfo.getType()){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isMobileNet(final Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connManager){
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo.isAvailable() && ConnectivityManager.TYPE_MOBILE==networkInfo.getType()){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isNetworkOK(Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null){
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

            if (networkInfo!=null && networkInfo.isAvailable()){
                return true;
            }
        }
        return false;
    }
    
    public static String getCurrentSsid(Context context){
        String ssid = null;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (null != wifiInfo){
            ssid = wifiInfo.getSSID();
        }
        return ssid;
    }

    static private AlertDialog.Builder mSetNetDialog = null;
    
    public static void showNotWifiNotice(final Context context){
        if (null == mSetNetDialog){
            mSetNetDialog = new AlertDialog.Builder(context);
            mSetNetDialog.setTitle("提示");
            mSetNetDialog.setMessage("您正在使用非WIFI网络，建议在wifi下使用");
            mSetNetDialog.setPositiveButton("设置",
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            try{
                                dialog.dismiss();
                                context.startActivity(new Intent(
                                        android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                            }catch (Exception e){
                                e.printStackTrace();
                            }finally{
                                mSetNetDialog = null;
                            }
                        }
                    });
            
            mSetNetDialog.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                        
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            try{
                                dialog.dismiss();
                            }catch (Exception e){
                                e.printStackTrace();
                            }finally{
                                mSetNetDialog = null;
                            }
                        }
                    });
            mSetNetDialog.create();
        }
        mSetNetDialog.show();
    }
  
}
