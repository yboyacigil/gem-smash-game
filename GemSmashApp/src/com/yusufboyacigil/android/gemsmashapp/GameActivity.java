package com.yusufboyacigil.android.gemsmashapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class GameActivity extends Activity implements OnTouchListener {
	
	private static final String TAG = "GameActivity";
	private static final String GAME_STATE_KEY = "game-state";

	private GameView gameView;
	
	private float x;
	private float y;
	private float dx;
	private float dy;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        
        gameView = (GameView) findViewById(R.id.game_view);
        gameView.setOnTouchListener(this);
        
        gameView.setTextView((TextView) findViewById(R.id.status_text));

        if (savedInstanceState == null) {
            // We were just launched -- set up a new game
        	Log.d(TAG, "No saved instance");
            gameView.setMode(GameView.READY);
        } else {
 			// We are being restored
        	Log.d(TAG, "Restoring game state");
            Bundle map = savedInstanceState.getBundle(GAME_STATE_KEY);
            if (map != null) {
                gameView.restoreState(map);
            } else {
                gameView.setMode(GameView.PAUSED);
            }
        }
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		gameView.setMode(GameView.PAUSED);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		gameView.setMode(GameView.RUNNING);
		gameView.update();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "Saving game state");
		outState.putBundle(GAME_STATE_KEY, gameView.saveState());
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (event) {
			try {
				//Waits 16ms.
				event.wait(16);

				//when user touches the screen
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					//reset deltaX and deltaY
					dx = dy = 0;

					//get initial positions
					x = event.getRawX();
					y = event.getRawY();
				}

				//when screen is released
				if(event.getAction() == MotionEvent.ACTION_UP) {
					dx = event.getRawX() - x;
					dy = event.getRawY() - y;

					if(Math.abs(dy) >= Math.abs(dx)) {
						if (dy < 0) {
							//swipped up
							gameView.pushFromBasket();
						} else {
							//swipped down
							gameView.pickIntoBasket();
						}
					} else {
						if(dx > 0) {
							//swipped right
							gameView.moveBasketRight();
						}
						else {
							//swipped left
							gameView.moveBasketLeft();
						}
					}
				}
			} catch (InterruptedException e) {
				return true;
			}
		}
		return true;
	}
	
}