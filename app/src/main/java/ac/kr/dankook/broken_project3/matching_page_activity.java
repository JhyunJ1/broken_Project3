
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		matching_page
	 *	@date 		Saturday 23rd of July 2022 01:14:54 PM
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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;



public class matching_page_activity extends Activity {

	private View decorView;
	private int uiOption;

	private ImageView yes;
	private ImageView no;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.matching_page);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		yes = findViewById(R.id.yesBtn);
		no = findViewById(R.id.noBtn);

		decorView = getWindow().getDecorView();
		uiOption = getWindow().getDecorView().getSystemUiVisibility();
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
			uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
			uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
			uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

		yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), location_activity.class);
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
	
	