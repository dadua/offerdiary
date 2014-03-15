package com.socialbaba.games.pairmerge.ui;

import java.util.Arrays;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.socialbaba.games.R;
import com.socialbaba.games.pairmerge.Grid;
import com.socialbaba.games.pairmerge.ui.common.OnSwipeTouchListener;
import com.socialbaba.games.pairmerge.utils.GridConversionUtils;
import com.socialbaba.games.pairmerge.utils.GridTileMover;
import com.socialbaba.games.pairmerge.utils.GridTileViewUtils;

public class MainActivity extends ActionBarActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements OnClickListener{

    	Grid grid = new Grid();
    	
    	int currentScore = 0;

        public PlaceholderFragment() {
        }
        

		private void setGridAdapter(int[][] twoDArrayFromOneD) {
			grid.setGridValues(GridConversionUtils.getOneDArrayFromTwoD(twoDArrayFromOneD));
			grid.addARandomValAtAnyAvailableIndex();
			String[] readableValuesFromTwoD = GridTileViewUtils.getReadableValuesFromGrid(grid);
			ListAdapter adapter = new GridTileAdapter(Arrays.asList(readableValuesFromTwoD));
			gridView.setAdapter(adapter);
		}
		
			
		public void moveRightHandler(View v) {
				int[][] twoDArrayFromOneD = GridConversionUtils.getTwoDArrayFromOneD(grid.getGridValues());
				int updatedScore = GridTileMover.moveRight(twoDArrayFromOneD);
				updateScoreView(updatedScore);
				setGridAdapter(twoDArrayFromOneD);

		}

		public void moveUpHandler(View v) {
			int[][] twoDArrayFromOneD = GridConversionUtils.getTwoDArrayFromOneD(grid.getGridValues());
			int updatedScore = GridTileMover.moveUp(twoDArrayFromOneD);
			updateScoreView(updatedScore);
			setGridAdapter(twoDArrayFromOneD);
		}

		private void updateScoreView(int updatedScore) {
			currentScore += updatedScore;
			scoreView.setText(Integer.toString(currentScore));
		}

		public void moveDownHandler(View v) {
			int[][] twoDArrayFromOneD = GridConversionUtils.getTwoDArrayFromOneD(grid.getGridValues());
			int updatedScore = GridTileMover.moveDown(twoDArrayFromOneD);
			updateScoreView(updatedScore);
			setGridAdapter(twoDArrayFromOneD);
		}
		
			
		public void moveLeftHandler(View v) {
			int[][] twoDArrayFromOneD = GridConversionUtils.getTwoDArrayFromOneD(grid.getGridValues());
			int updatedScore = GridTileMover.moveLeft(twoDArrayFromOneD);
			updateScoreView(updatedScore);
			setGridAdapter(twoDArrayFromOneD);
		}
        
		private GridView gridView;
		private TextView scoreView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            gridView = (GridView) rootView.findViewById(R.id.gridView1);
            
            scoreView = (TextView) rootView.findViewById(R.id.currentScore);
            updateScoreView(0);

			setupSwipe(gridView);

            Button moveLeftButton = (Button) rootView.findViewById(R.id.moveLeft);
			moveLeftButton.setOnClickListener(this);
			

            Button moveRightBtn= (Button) rootView.findViewById(R.id.moveRight);

			moveRightBtn.setOnClickListener(this);

            Button moveUpBtn = (Button) rootView.findViewById(R.id.moveUp);
			moveUpBtn.setOnClickListener(this);

            Button moveDownBtn = (Button) rootView.findViewById(R.id.moveDown);
			moveDownBtn.setOnClickListener(this);
 
            String[] gridValues = GridTileViewUtils.getReadableValuesFromGrid(grid);
            ListAdapter adapter = new GridTileAdapter(Arrays.asList(gridValues));
			gridView.setAdapter(adapter);

            return rootView;
        }


		private void setupSwipe(GridView gridView2) {
			gridView2.setOnTouchListener(new OnSwipeTouchListener(this.getActivity()) {
				
				@Override
				public void onSwipeBottom() {
					Log.i("ALOK", "Swipe down");
//					moveDownHandler(null);
					super.onSwipeBottom();
				}
				
				@Override
				public void onSwipeLeft() {
					Log.i("ALOK", "Swipe left");
//					moveLeftHandler(null);
					super.onSwipeLeft();
				}
				
				@Override
				public void onSwipeRight() {
					Log.i("ALOK", "Swipe right");
//					moveRightHandler(null);
					super.onSwipeRight();
				}
				
				@Override
				public void onSwipeTop() {
					Log.i("ALOK", "Swipe Top");
//					moveUpHandler(null);
					super.onSwipeTop();
				}
				
			});
			
		}


		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.moveLeft:
				moveLeftHandler(v);
				break;

			case R.id.moveRight:
				moveRightHandler(v);
				break;

			case R.id.moveUp:
				moveUpHandler(v);
				break;
			case R.id.moveDown:
				moveDownHandler(v);
				break;

			default:
				break;
			}
			
		}

    }

}
