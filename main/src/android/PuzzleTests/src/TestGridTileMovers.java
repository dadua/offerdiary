import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.socialbaba.games.pairmerge.utils.GridTileMover;


public class TestGridTileMovers {

	@Test
	public void testMoveLeftPass() {
		
		int[][] grid = {

				{1, 0, 0, 0},
				{0, 1, 0, 2},
				{4, 2, 0, 6},
				{1, 0, 0, 4}
		},

		expectedOut = {

				{1, 0, 0, 0},
				{1, 2, 0, 0},
				{4, 2, 6, 0},
				{1, 4, 0, 0}
				
		};

		GridTileMover.moveLeftSinglePass(grid);
		
		assertArrayEquals(expectedOut, grid);

		
	}
	
	@Test
	public void testmoveRightPass() {
		
		int[][] grid = {

				{1, 0, 0, 0},
				{0, 1, 0, 2},
				{4, 2, 0, 6},
				{1, 0, 0, 4}

		},

		expectedOut = {

				{0, 0, 0, 1},
				{0, 0, 1, 2},
				{0, 4, 2, 6},
				{0, 0, 1, 4}
				
		};

		GridTileMover.moveRightSinglePass(grid);
		
		assertArrayEquals(expectedOut, grid);
		
		GridTileMover.moveRightSinglePass(expectedOut);

		assertArrayEquals(expectedOut, expectedOut);

		int[][] grid2= {

				{1, 0, 3, 0},
				{0, 1, 0, 2},
				{4, 2, 1, 6},
				{1, 0, 0, 4}

		},

		expectedOut2 = {

				{0, 0, 1, 3},
				{0, 0, 1, 2},
				{4, 2, 1, 6},
				{0, 0, 1, 4}
				
		};

		GridTileMover.moveRightSinglePass(grid2);
		assertArrayEquals(expectedOut2, grid2);

		
	}
	
	@Test
	public void testMoveUpSinglePass() {
		
		int[][] grid = {

				{1, 0, 0, 0},
				{0, 1, 0, 2},
				{4, 2, 1, 6},
				{1, 0, 0, 4}
		},

		expectedOut = {

				{1, 1, 1, 2},
				{4, 2, 0, 6},
				{1, 0, 0, 4},
				{0, 0, 0, 0}
				
		};

		GridTileMover.moveUpSinglePass(grid);
		
		assertArrayEquals(expectedOut, grid);

		
	}
	
	@Test
	public void testMoveDownSinglePass() {
		
		int[][] grid = {

				{1, 0, 0, 0},
				{0, 1, 0, 2},
				{4, 2, 0, 6},
				{1, 0, 0, 4}
		},

		expectedOut = {

				{0, 0, 0, 0},
				{1, 0, 0, 2},
				{4, 1, 0, 6},
				{1, 2, 0, 4}
				
		};

		GridTileMover.moveDownSinglePass(grid);
		
		assertArrayEquals(expectedOut, grid);
	}



	@Test
	public void testMoveLeft() {
		
		int[][] grid = {

				{2,  2, 2, 0},
				{0, 32, 4, 2},
				{2,  0, 2, 8},
				{4,  2, 0, 2}
		},

		expectedOut = {

				{4, 2, 0, 0},
				{32, 4, 2, 0},
				{4, 8, 0, 0},
				{4, 4, 0, 0}
				
		};

		GridTileMover.moveLeft(grid);
		
		assertArrayEquals(expectedOut, grid);
	}



}
