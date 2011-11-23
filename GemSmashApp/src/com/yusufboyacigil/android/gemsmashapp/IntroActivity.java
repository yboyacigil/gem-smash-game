package com.yusufboyacigil.android.gemsmashapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 *
 * @author yboyacigil
 */
public class IntroActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.intro_layout);
		
        Button newGameButton = (Button) findViewById(R.id.new_game);
		newGameButton.setText(R.string.intro_new_game);
        newGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IntroActivity.this, GameActivity.class);
				IntroActivity.this.startActivity(intent);
			}
		});
	}
	
}
