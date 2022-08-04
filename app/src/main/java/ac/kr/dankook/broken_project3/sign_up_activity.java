
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		sign_up
	 *	@date 		Saturday 23rd of July 2022 02:13:52 PM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

package ac.kr.dankook.broken_project3;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonParseException;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class RegisterRequest extends StringRequest {

	final static private String URL = "https://webhook.site/8d505641-5ac2-41c6-82c5-53538c591433";
	private Map<String, String> map;

	public RegisterRequest(String user_email, String user_name, String user_pw, Response.Listener<String> listener) {
		super(Method.POST, URL, listener, null);

		map = new HashMap<>();
		map.put("user_email", user_email);
		map.put("user_name", user_name);
		map.put("user_pw", user_pw);


	}

	@Override
	protected Map<String, String> getParams() {
		return map;
	}


}

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



	View.OnClickListener myClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String user_email = email.getText().toString();
			String user_name = name.getText().toString();
			String user_pw = password.getText().toString();

			Response.Listener<String> responseListener = new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Intent intent = new Intent(sign_up_activity.this, sign_up_check_activity.class);
					startActivity(intent);
				}
			};

			RegisterRequest registerRequest = new RegisterRequest(user_email, user_name, user_pw,responseListener);
			RequestQueue queue = Volley.newRequestQueue(sign_up_activity.this);
			queue.add(registerRequest);

		}
	};


	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		// super.onWindowFocusChanged(hasFocus);

		if( hasFocus ) {
			decorView.setSystemUiVisibility( uiOption );
		}
	}
}

	
	