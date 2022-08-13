package ac.kr.dankook.broken_project3;

/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		log_in
	 *	@date 		Saturday 23rd of July 2022 02:12:15 PM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	



import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ImageView;


public class log_in_activity extends Activity {


	private View rectangle_5;
	private TextView signup;

	private View decorView;
	private int uiOption;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		decorView = getWindow().getDecorView();
		uiOption = getWindow().getDecorView().getSystemUiVisibility();
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
			uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
			uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
			uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

		rectangle_5 = (View) findViewById(R.id.rectangle_5);
		signup = (TextView) findViewById(R.id.don_t_have_an_account__sign_up);

		// enter 버튼
		rectangle_5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), matching_1_activity.class);
				startActivity(intent);
			}
		});

		// sign up 버튼
		signup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), sign_up_activity.class);
				startActivity(intent);
			}
		});


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
	
	