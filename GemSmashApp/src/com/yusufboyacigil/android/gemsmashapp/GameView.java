package com.yusufboyacigil.android.gemsmashapp;

import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yusufboyacigil.gemsmashengine.AdjacentGemsInspector;
import com.yusufboyacigil.gemsmashengine.GemPicker;
import com.yusufboyacigil.gemsmashengine.GemPusher;
import com.yusufboyacigil.gemsmashengine.GemPusher.PushResult;
import com.yusufboyacigil.gemsmashengine.GemSmasher;
import com.yusufboyacigil.gemsmashengine.model.Basket;
import com.yusufboyacigil.gemsmashengine.model.Board;
import com.yusufboyacigil.gemsmashengine.model.Cell;
import com.yusufboyacigil.gemsmashengine.util.BoardPrinter;

/**
 *
 * @author yboyacigil
 */
public class GameView extends View {
	
	private static final String TAG = "GameView";
	
	private static final int RED_GEM    = 1;
	private static final int GREEN_GEM  = 2;
	private static final int BLUE_GEM   = 3;
	private static final int YELLOW_GEM = 4;
	private static final int GREY_GEM   = 5;
	
	private static final Random RANDOM = new Random();
	
	public static final int READY    = 0;
	public static final int RUNNING  = 1;
	public static final int PAUSED   = 2;
	public static final int LOST     = 3;
	public static final int NEW_GAME = 4;
	
	private Board board;
	private Basket basket;
	
	private int gemSize;
	private int xGemCount;
	private int yGemCount;
	private int xOffset;
	private int yOffset;
	
	private int basketSize;
	private int basketPositionIndex;
	private int xBasketOffset;
	private int yBasketOffset;
	
	private Bitmap[] gemBitmapArray;
	private Bitmap[][] basketBitmapArray;
	private Bitmap oddTargetIndicatorBitmap;
	private Bitmap evenTargetIndicatorBitmap;
	private final Paint paint = new Paint();

	final GemFillerThread gemFiller = new GemFillerThread();
	final RefreshHandler redrawHandler = new RefreshHandler();
	
	private int mode = READY;
	private TextView statusText;
	
	private int targetIndicatorMode = 0;
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GameView);
        gemSize = a.getInt(R.styleable.GameView_gemSize, 32);
        basketSize = a.getInt(R.styleable.GameView_basketSize, 64);
        a.recycle();
        
        initGameView();
    }
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GameView);
        gemSize = a.getInt(R.styleable.GameView_gemSize, 32);
        basketSize = a.getInt(R.styleable.GameView_basketSize, 64);
        a.recycle();
        
        initGameView();
    }
	
	@Override
    protected void onSizeChanged(int neww, int newh, int oldw, int oldh) {
		final int wpad = gemSize * 3;
		final int hpad = 100;
		
		int w = neww - (wpad * 2);
		int h = newh - hpad;
		
        xGemCount = (int) Math.floor(w / gemSize);
        yGemCount = (int) Math.floor(h / gemSize);

        xOffset = ((w - (gemSize * xGemCount)) / 2) + wpad;
        yOffset = ((h - (gemSize * yGemCount)) / 2);

        board = new Board(xGemCount, yGemCount);
        board.pushRow(newRandomRow());
        board.pushRow(newRandomRow());
        board.pushRow(newRandomRow());
        board.pushRow(newRandomRow());
        Log.d(TAG, BoardPrinter.build(board));
        
        basketPositionIndex = 0;
        xBasketOffset = xOffset - (gemSize / 2);
        yBasketOffset = yOffset + (yGemCount * gemSize) + (gemSize / 2);
        
        basket = new Basket();
        gemFiller.start();
        
        setMode(RUNNING);
        update();
    }

	@Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int row = 0; row < yGemCount; row += 1) {
            for (int col = 0; col < xGemCount; col += 1) {
                if (board.cell(row, col) > 0) {
                    canvas.drawBitmap(gemBitmapArray[board.cell(row, col)], 
                                xOffset + col * gemSize,
                                yOffset + row * gemSize,
                                paint);
                } else {
                	if (col == basketPositionIndex) {
                		canvas.drawBitmap(
                				(targetIndicatorMode == 0?evenTargetIndicatorBitmap:oddTargetIndicatorBitmap), 
                				xOffset + (col * gemSize), 
                				yOffset + (row * gemSize), 
                				paint);
                	}
                }
        		targetIndicatorMode = 1 - targetIndicatorMode;
            }
        }
        
        int basketBitmapIndex = basket.numGems() < 3?basket.numGems():3;
        Bitmap basketBitmap = basketBitmapArray[basket.gem()][basketBitmapIndex];
        if (basketBitmap != null)
        	canvas.drawBitmap(basketBitmap,
        		xBasketOffset + (basketPositionIndex * (basketSize / 2)),
        		yBasketOffset,
        		paint);
    }
	
	public void moveBasketRight() {
		if (mode != RUNNING) return;
		if ((basketPositionIndex + 1) < xGemCount) 
			basketPositionIndex++;
		else
			basketPositionIndex = 0;
		Log.d(TAG, "Move basket right (position index: " + basketPositionIndex + ")");
	}
	
	public void moveBasketLeft() {
		if (mode != RUNNING) return;
		if ((basketPositionIndex - 1) >= 0)
			basketPositionIndex--;
		else
			basketPositionIndex = xGemCount - 1;
		Log.d(TAG, "Move basket left (position index: " + basketPositionIndex + ")");
	}
	
	public void pickIntoBasket() {
		if (mode != RUNNING) return;
		int numGems = GemPicker.pick(basketPositionIndex, board, basket);
		Log.d(TAG, "Added " + numGems + " gem(s) to basket (position index: " + basketPositionIndex + ", basket: " + basket + ")");
	}
	
	public void pushFromBasket() {
		if (mode != RUNNING) return;
		final PushResult pr = GemPusher.push(basketPositionIndex, board, basket);
		Log.d(TAG, "Pushed " + pr.numGems() + " gem(s) to basket (position index: " + basketPositionIndex + ", basket: " + basket + ")");
		if (pr.numGems() > 0) {
			final Set<Cell> adjacentCells = AdjacentGemsInspector.inspect(pr.gem(), pr.inspectCell(), board);
			if (adjacentCells.size() > 0) {
				Log.d(TAG, "Found adjacent cells starting from cell: " + pr.inspectCell() + ": " + adjacentCells);
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						doSmashCells(pr.gem(), adjacentCells);
					}
					
					private void doSmashCells(int gem, Set<Cell> adjacentCells) {
						Log.d(GameView.TAG, "Smashing gem: " + pr.gem() + " at cells: " + adjacentCells);
						Set<Cell> potentialAdjacentCells = GemSmasher.smash(gem, board, adjacentCells);
						Log.d(GameView.TAG, "Potential adjacent cells after smash: " + potentialAdjacentCells);
						for(Cell c: potentialAdjacentCells) {
							Set<Cell> newAdjacentCells = AdjacentGemsInspector.inspect(c.gem(), c, board);
							doSmashCells(c.gem(), newAdjacentCells);
						}

					}
				});						
				t.start();
			}
		}
	}

	public void setTextView(TextView newView) {
		statusText = newView;
    }
	
	public void setMode(int newMode) {
		if (mode == LOST && newMode != NEW_GAME) {
			return;
		}
		int oldMode = mode;
        mode = newMode;
        if (oldMode != RUNNING && mode == RUNNING) {
        	if (gemFiller.isAlive()) 
        		gemFiller.unpause();
        }
        if (mode == PAUSED) {
        	gemFiller.pause();
        }
        if (mode == LOST) {
        	gemFiller.kill();
        }
	}
	
	public void update() {
		if (mode == RUNNING) {
			if (statusText.getVisibility() == View.VISIBLE) {
				statusText.setVisibility(View.INVISIBLE);
			}
            redrawHandler.sleep(250);			
		} else {
			CharSequence str = "";
			Resources res = getContext().getResources();
	        
	        if (mode == PAUSED) {
	            str = res.getText(R.string.mode_paused);
	        }
	        if (mode == LOST) {
	            str = res.getString(R.string.mode_lost);
	        }
	
	        statusText.setText(str);
	        statusText.setVisibility(View.VISIBLE);
		}
	}
	
	public Bundle saveState() {
		Bundle map = new Bundle();
		
		map.putInt("basketPositionIndex", basketPositionIndex);
		map.putSerializable("board", board);
		map.putSerializable("basket", basket);

        return map;
    }
	
	public void restoreState(Bundle map) {
		setMode(PAUSED);
		
		basketPositionIndex = map.getInt("basketPositionIndex");
		board = (Board) map.getSerializable("board");
		basket = (Basket) map.getSerializable("basket");
	}
	
	private void initGameView() {
		setFocusable(true);
		Resources r = this.getContext().getResources();
		
		gemBitmapArray = new Bitmap[6];
		loadGemBitmap(RED_GEM, r.getDrawable(R.drawable.red));
		loadGemBitmap(GREEN_GEM, r.getDrawable(R.drawable.green));
		loadGemBitmap(BLUE_GEM, r.getDrawable(R.drawable.blue));
		loadGemBitmap(YELLOW_GEM, r.getDrawable(R.drawable.yellow));
		loadGemBitmap(GREY_GEM, r.getDrawable(R.drawable.grey));
		
		basketBitmapArray = new Bitmap[6][4];
		// empty basket
		loadBasketBitmap(0, 0, r.getDrawable(R.drawable.b));
		
		loadBasketBitmap(RED_GEM, 0, r.getDrawable(R.drawable.b));
		loadBasketBitmap(RED_GEM, 1, r.getDrawable(R.drawable.b_r_1));
		loadBasketBitmap(RED_GEM, 2, r.getDrawable(R.drawable.b_r_2));
		loadBasketBitmap(RED_GEM, 3, r.getDrawable(R.drawable.b_r));
		
		loadBasketBitmap(GREEN_GEM, 0, r.getDrawable(R.drawable.b));
		loadBasketBitmap(GREEN_GEM, 1, r.getDrawable(R.drawable.b_g_1));
		loadBasketBitmap(GREEN_GEM, 2, r.getDrawable(R.drawable.b_g_2));
		loadBasketBitmap(GREEN_GEM, 3, r.getDrawable(R.drawable.b_g));

		loadBasketBitmap(BLUE_GEM, 0, r.getDrawable(R.drawable.b));
		loadBasketBitmap(BLUE_GEM, 1, r.getDrawable(R.drawable.b_b_1));
		loadBasketBitmap(BLUE_GEM, 2, r.getDrawable(R.drawable.b_b_2));
		loadBasketBitmap(BLUE_GEM, 3, r.getDrawable(R.drawable.b_b));

		loadBasketBitmap(YELLOW_GEM, 0, r.getDrawable(R.drawable.b));
		loadBasketBitmap(YELLOW_GEM, 1, r.getDrawable(R.drawable.b_y_1));
		loadBasketBitmap(YELLOW_GEM, 2, r.getDrawable(R.drawable.b_y_2));
		loadBasketBitmap(YELLOW_GEM, 3, r.getDrawable(R.drawable.b_y));

		loadBasketBitmap(GREY_GEM, 0, r.getDrawable(R.drawable.b));
		loadBasketBitmap(GREY_GEM, 1, r.getDrawable(R.drawable.b_e_1));
		loadBasketBitmap(GREY_GEM, 2, r.getDrawable(R.drawable.b_e_2));
		loadBasketBitmap(GREY_GEM, 3, r.getDrawable(R.drawable.b_e));

		loadTargetIndicatorBitmaps(r);
	}
	
	private void loadGemBitmap(int gem, Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(gemSize, gemSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, gemSize, gemSize);
        drawable.draw(canvas);

        gemBitmapArray[gem] = bitmap;		
	}
	
	private void loadBasketBitmap(int gem, int index, Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(basketSize, basketSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, basketSize, basketSize);
        drawable.draw(canvas);

        basketBitmapArray[gem][index] = bitmap;		
	}
	
	private void loadTargetIndicatorBitmaps(Resources r) {
		evenTargetIndicatorBitmap = Bitmap.createBitmap(gemSize, gemSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(evenTargetIndicatorBitmap);
        Drawable drawable = r.getDrawable(R.drawable.even);
        drawable.setBounds(0, 0, gemSize, gemSize);
        drawable.draw(canvas);
        
        oddTargetIndicatorBitmap = Bitmap.createBitmap(gemSize, gemSize, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(oddTargetIndicatorBitmap);
        drawable = r.getDrawable(R.drawable.odd);
        drawable.setBounds(0, 0, gemSize, gemSize);
        drawable.draw(canvas);
	}

	private int[] newRandomRow() {
		int[] b = new int[xGemCount];
		for(int i=0; i < b.length; i++) {
			int v = RANDOM.nextInt(6);
			b[i] =  v==0?1:v;
		}
		return b;
	}
	
	private long fillPeriodMillis() {
		return 10000;
	}

	class GemFillerThread extends Thread {
		
		private static final String TAG = "GameFillerThread";
		
		boolean killed = false;
		boolean paused = false;
		
		public GemFillerThread() {
			super("GemFillerThread");
		}
		
		public void kill() {
			killed = true;
		}
		
		public boolean wasKilled() {
			return killed;
		}
		
		public void pause() {
			this.paused = true;
		}
		
		public void unpause() {
			this.paused = false;
		}
		
		@Override
		public void run() {
			while(!killed) {
				if (paused) {
					try {
						Thread.sleep(1000);
						continue;
					} catch (InterruptedException e) {
						Log.d(TAG, "Interrupted while sleeping in pause. Ending loop");
						break;
					}
				}
				if (GameView.this.board.isFilledUp()) {
					Log.d(TAG, "Board is filled up. Game over");
					GameView.this.setMode(GameView.LOST);
					break;
				}
				GameView.this.board.pushRow(GameView.this.newRandomRow());
				try {
					Thread.sleep(GameView.this.fillPeriodMillis());
				} catch (InterruptedException e) {
					Log.d(TAG, "Interrupted while sleeping for the next fill. Ending loop");
					break;
				}
			}
		}
	}

	class RefreshHandler extends Handler {
		@Override
        public void handleMessage(Message msg) {
			GameView.this.update();
            GameView.this.invalidate();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }		
	}

}
