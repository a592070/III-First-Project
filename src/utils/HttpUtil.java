package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class HttpUtil {
    public static final String reqType = "reqType";
    public static final String username = "username";
    public static final String password = "password";
    public static final String stockno = "stockno";
    public static final String date = "date";


    private static HttpURLConnection conn;

    public static String getInfo1(String sUrl) throws IOException {
        conn = (HttpURLConnection) new URL(sUrl).openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Charset", "UTF-8");

        conn.setRequestMethod("GET");
        conn.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String s;
        while((s=br.readLine()) != null){
            sb.append(s);
        }
        return sb.toString();
    }

    public static String getInfo2(String sUrl, String paramKey, String paramValue) throws IOException {
        conn = (HttpURLConnection) new URL(sUrl).openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Charset", "UTF-8");

        conn.setRequestMethod("GET");
        conn.setRequestProperty(paramKey, paramValue);
        conn.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String s;
        while((s=br.readLine()) != null){
            sb.append(s);
        }
        return sb.toString();
    }

    public static String getInfo3(String sUrl, String paramKey1, String paramValue1, String paramKey2, String paramValue2) throws IOException{
        conn = (HttpURLConnection) new URL(sUrl).openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Charset", "UTF-8");

        conn.setRequestMethod("GET");
        conn.setRequestProperty(paramKey1, paramValue1);
        conn.setRequestProperty(paramKey2, paramValue2);
        conn.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String s;
        while((s=br.readLine()) != null){
            sb.append(s);
        }
        return sb.toString();
    }
    // Login
    public static String getInfo4(String sUrl, String paramKey1, String paramValue1, String paramKey2, String paramValue2, String paramKey3, String paramValue3)throws IOException{
        conn = (HttpURLConnection) new URL(sUrl).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setDoOutput(true);

//        conn.setRequestProperty(paramKey1, paramValue1);
//        conn.setRequestProperty(paramKey2, paramValue2);
//        conn.setRequestProperty(paramKey3, paramValue3);
        conn.connect();

        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        String param = paramKey1+"="+paramValue1+"&"+paramKey2+"="+paramValue2+"&"+paramKey3+"="+paramValue3;
        dos.writeBytes(param);
        dos.flush();
        dos.close();


        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String s;
        while((s=br.readLine()) != null){
            sb.append(s);
        }
        return sb.toString();
    }
}
