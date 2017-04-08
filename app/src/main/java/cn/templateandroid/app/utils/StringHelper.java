package cn.templateandroid.app.utils;


import android.util.Base64;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class StringHelper {

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		for (int i = 0, l = str.length(); i < l; i++) {
			char c = str.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	public static int toInt(Object obj) {
		if (obj == null) return 0;
		return toInt(obj.toString(), 0);
	}

	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	public final static String md5(String s) {
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String preprocessUrl(String url) {
		String ret = url;
		try {
			String add = URLEncoder.encode("+", "UTF-8");
			String star = URLEncoder.encode("*", "UTF-8");

			ret = url.replace("+", add);
			ret = ret.replace("*", star);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	public static String formatTelNum(String tel) {
		StringBuffer formatedTel = new StringBuffer();
		if (tel.length() == 11) {
			char[] telArr = tel.toCharArray();
			for (int i = 0; i < 11; i++) {
				if (i == 3 || i == 7) {
					formatedTel.append(" ");
				}
				formatedTel.append(telArr[i]);
			}
		}
		return formatedTel.toString();
	}

	public static String formatDateTime(String dateTime) {
		final Calendar calendar = Calendar.getInstance();
		final Pattern ymdhmsPattern = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2})(:\\d{2})?$");
		final Pattern ymdPattern = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})$");
		Matcher matcher = ymdhmsPattern.matcher(dateTime);
		if (matcher.matches()) {
			calendar.setTime(new Date());
			if (calendar.get(Calendar.YEAR) == Integer.valueOf(matcher.group(1))) {
				if ((calendar.get(Calendar.MONTH) + 1) == Integer.valueOf(matcher.group(2)) && calendar.get(Calendar.DAY_OF_MONTH) == Integer.valueOf(matcher.group(3))) {
					return matcher.group(4) + ":" + matcher.group(5);
				} else {
					return matcher.group(2) + "-" + matcher.group(3);
				}
			} else {
				return matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3);
			}
		} else {
			matcher = ymdPattern.matcher(dateTime);
			if (matcher.matches()) {
				calendar.setTime(new Date());
				if (calendar.get(Calendar.YEAR) == Integer.valueOf(matcher.group(1))) {
					if ((calendar.get(Calendar.MONTH) + 1) == Integer.valueOf(matcher.group(2)) && calendar.get(Calendar.DAY_OF_MONTH) == Integer.valueOf(matcher.group(3))) {
						return "TODAY";
					} else {
						return matcher.group(2) + "-" + matcher.group(3);
					}
				}
			}
		}
		return dateTime;
	}

	public static String getTodayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	public static String encyptMessage(String message, String key) {
		String resultStr = null;
		try {
			byte[] keyBytes = key.getBytes();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"), new IvParameterSpec(keyBytes));
			byte[] resultBytes = cipher.doFinal(message.getBytes());
			resultStr = Base64.encodeToString(resultBytes, Base64.DEFAULT);
			resultStr = resultStr.replaceAll("\n", "");
		} catch (Exception e) {
			resultStr = message;
		}
		return resultStr;
	}

	public static String decpytMessage(String message, String key) {
		String resultStr = null;
		try {
			byte[] keyBytes = key.getBytes();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"), new IvParameterSpec(keyBytes));
			byte[] resultBytes = cipher.doFinal(Base64.decode(message, Base64.DEFAULT));
			resultStr = new String(resultBytes);
		} catch (Exception e) {
			resultStr = message;
		}
		return resultStr;
	}

	public static Map<String, Boolean> sortMapByKey(Map<String, Boolean> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, Boolean> sortMap = new TreeMap<String, Boolean>(new MapKeyComparator());

		sortMap.putAll(map);

		return sortMap;
	}

	public static String formatDataSize(long size) {
		DecimalFormat formater = new DecimalFormat("####.00");
		if (size < 1024) {
			return size + "bytes";
		} else if (size < 1024 * 1024) {
			float kbsize = size / 1024f;
			return formater.format(kbsize) + "KB";
		} else if (size < 1024 * 1024 * 1024) {
			float mbsize = size / 1024f / 1024f;
			return formater.format(mbsize) + "MB";
		} else if (size < 1024 * 1024 * 1024 * 1024) {
			float gbsize = size / 1024f / 1024f / 1024f;
			return formater.format(gbsize) + "GB";
		} else {
			return "size: error";
		}

	}
}

class MapKeyComparator implements Comparator<String> {

	@Override
	public int compare(String str1, String str2) {

		return Integer.valueOf(str1).compareTo(Integer.valueOf(str2));
	}
}
