
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		location
	 *	@date 		Saturday 23rd of July 2022 02:18:26 PM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

package ac.kr.dankook.broken_project3;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

	public class location_activity extends Activity {


	private View decorView;
	private int uiOption;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);

		decorView = getWindow().getDecorView();
		uiOption = getWindow().getDecorView().getSystemUiVisibility();
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
			uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
			uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
			uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;


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
	
	