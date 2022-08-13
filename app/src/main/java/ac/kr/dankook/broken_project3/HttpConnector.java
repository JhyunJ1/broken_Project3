package ac.kr.dankook.broken_project3;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnector extends Thread{

    @Override
    public void run() {
        try {
            URL url = new URL("https://webhook.site/386cad30-6870-48b0-a194-cbbc8a93d2b0");
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
