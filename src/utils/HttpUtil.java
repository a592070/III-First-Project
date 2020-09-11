package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUtil {
    public static final String reqType = "reqType";
    public static final String username = "username";
    public static final String password = "password";


    private static HttpURLConnection conn;

    public static String getInfo1(String sUrl) throws IOException {
        conn = (HttpURLConnection) new URL(sUrl).openConnection();
        conn.setRequestProperty("Content-Type", "application/json");

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
    public static String getInfo4(String sUrl, String paramKey1, String paramValue1, String paramKey2, String paramValue2, String paramKey3, String paramValue3)throws IOException{
        conn = (HttpURLConnection) new URL(sUrl).openConnection();
        conn.setRequestProperty("Content-Type", "application/json");

        conn.setRequestMethod("GET");
        conn.setRequestProperty(paramKey1, paramValue1);
        conn.setRequestProperty(paramKey2, paramValue2);
        conn.setRequestProperty(paramKey3, paramValue3);
        conn.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String s;
        while((s=br.readLine()) != null){
            sb.append(s);
        }
        return sb.toString();
    }
}
