package ac.kr.dankook.broken_project3;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnector extends Thread{

    @Override
    public void run() {
        try {
            URL url = new URL("https://webhook.site/8d505641-5ac2-41c6-82c5-53538c591433");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            if(conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");

                int resCode = conn.getResponseCode();
                int HTTP_OK = HttpURLConnection.HTTP_OK;

                Log.d("JsonParsing", "resCode : " + resCode);
                Log.d("JsonParsing", "HTTP_OK : " + HTTP_OK);
            }
        }catch (Exception e) {

        }
    }
}
