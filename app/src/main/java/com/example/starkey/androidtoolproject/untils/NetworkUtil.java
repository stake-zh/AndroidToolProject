package com.example.starkey.androidtoolproject.untils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetworkUtil {
    private static NetworkInfo networkInfo;

    /**
     * 判断网络是否可用
     */
    public static boolean isNetAvailable(Context context) {
        if (networkInfo == null) {
            return false;
        }

        return networkInfo.isAvailable();
    }

    /**
     * 判断是否在移动网络下
     */
    public static boolean isNetTypeMobile(Context context) {
        if (networkInfo == null) {
            return false;
        }

        return ConnectivityManager.TYPE_MOBILE == networkInfo.getType();
    }

    /**
     * 判断是否在Wifi网络下
     */
    public static boolean isNetTypeWifi(Context context) {
        if (networkInfo == null) {
            return false;
        }

        return ConnectivityManager.TYPE_WIFI == networkInfo.getType();
    }

    public static String getNetworkTypeName(Context context) {
        String strNetworkType = "NO_NETWORK";
        try {
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    strNetworkType = "WIFI";
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    String _strSubTypeName = networkInfo.getSubtypeName();
                    int networkType = networkInfo.getSubtype();
                    switch (networkType) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                            strNetworkType = "2G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                        case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                        case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                            strNetworkType = "3G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                            strNetworkType = "4G";
                            break;
                        default:
                            // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                            if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                strNetworkType = "3G";
                            } else {
                                strNetworkType = "MOBILE";
                            }
                            break;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return strNetworkType;
    }

    /**
     * 是否开启移动数据网络
     */
    public static boolean isMobileAvailable(Context appContext) {
        ConnectivityManager manager = (ConnectivityManager) appContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobileInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mMobileInfo != null && mMobileInfo.isAvailable()) {
            return mMobileInfo.isConnected();
        }
        return false;
    }
}
