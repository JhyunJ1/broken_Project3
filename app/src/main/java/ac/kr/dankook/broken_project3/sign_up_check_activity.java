
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		sign_up_check
	 *	@date 		Saturday 23rd of July 2022 02:15:27 PM
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
import android.widget.TextView;
import android.widget.ImageView;

public class sign_up_check_activity extends Activity {

	private Button start;

	private View decorView;
	private int uiOption;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_check);

		decorView = getWindow().getDecorView();
		uiOption = getWindow().getDecorView().getSystemUiVisibility();
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
			uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
			uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
			uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

		start = (Button) findViewById(R.id.start);

		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(sign_up_check_activity.this,matching_1_activity.class);
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
	
	