

	package ac.kr.dankook.broken_project3;

	import android.app.Activity;
	import android.content.Intent;
	import android.os.Build;
	import android.os.Bundle;


	import android.util.Log;
	import android.view.View;
	import android.widget.Button;
	import android.widget.EditText;

	import com.android.volley.Header;
	import com.android.volley.RequestQueue;
	import com.android.volley.Response;
	import com.android.volley.toolbox.StringRequest;
	import com.android.volley.toolbox.Volley;
	import com.google.gson.JsonParseException;
	import com.loopj.android.http.AsyncHttpClient;
	import com.loopj.android.http.AsyncHttpResponseHandler;



	import org.json.JSONArray;
	import org.json.JSONException;
	import org.json.JSONObject;

	import java.util.HashMap;
	import java.util.Map;

	import cz.msebera.android.httpclient.HttpEntity;
	import cz.msebera.android.httpclient.entity.StringEntity;


	public class sign_up_activity extends Activity {

		private EditText name;
		private EditText age;
		private EditText password;
		private EditText email;
		private Button continue1;

		private View decorView;
		private int uiOption;


		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setContentView(R.layout.sign_up);

			decorView = getWindow().getDecorView();
			uiOption = getWindow().getDecorView().getSystemUiVisibility();
			if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
				uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
			if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
				uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
			if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
				uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

			name = findViewById(R.id.name);
			age = findViewById(R.id.age);
			password = findViewById(R.id.password);
			email = findViewById(R.id.email);
			continue1 = findViewById(R.id.continue1);

			continue1.setOnClickListener(myClickListener);
		}

		JSONObject user = new JSONObject();

		View.OnClickListener myClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// json 파일 만들기
				try {
					user.put("user_name", name.getText());
					user.put("user_age", age.getText());
					user.put("user_pw", password.getText());

				} catch(JSONException e) {

				}
				getRequestHttpPOST_BODY_JSON("https://webhook.site/386cad30-6870-48b0-a194-cbbc8a93d2b0");
			}
		};


		public void getRequestHttpPOST_BODY_JSON(String url) {
			try {
				AsyncHttpClient client = new AsyncHttpClient();
				JSONObject params = new JSONObject();

				params.put("users", user);

				StringEntity entity = new StringEntity(String.valueOf(params.toString()));

				client.post(sign_up_activity.this, url, entity, "application/json", new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {

					}
					@Override
					public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
						String responseData = String.valueOf(new String(responseBody));
						Log.d("---","---");
						Log.w("//===========//","================================================");
						Log.d("","\n"+"[A_Http > getRequestHttpPOST_BODY_JSON() 메소드 : HTTP 통신 수행 POST BODY JSON 방식 요청 확인]");
						Log.d("","\n"+"["+"응답 전체 - "+String.valueOf(responseData)+"]");
						Log.w("//===========//","================================================");
						Log.d("---","---");
					}

					@Override
					public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
						String responseError = String.valueOf(new String(responseBody));
						Log.d("---","---");
						Log.e("//===========//","================================================");
						Log.d("","\n"+"[A_Http > getRequestHttpPOST_BODY_JSON() 메소드 : HTTP 통신 수행 POST BODY JSON 방식 요청 실패]");
						Log.d("","\n"+"["+"에러코드 - "+String.valueOf(statusCode + "/" + responseError +"]"));
						Log.e("//===========//","================================================");
						Log.d("---","---");
					}

				});
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}


		@Override
		public void onWindowFocusChanged(boolean hasFocus) {
			// TODO Auto-generated method stub
			// super.onWindowFocusChanged(hasFocus);

			if( hasFocus ) {
				decorView.setSystemUiVisibility( uiOption );
			}
		}
	}


