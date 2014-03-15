package com.socialbaba.games.pairmerge.utils;



public class GridTileMover {
	
	public static int mergeCellsOnLeftMove(int[][] grid) {
		int scoreGainedOnLeftMove = 0;
		for (int row = 0; row < grid.length; row++) {
			for(int col = 0, lastVal = -1; col <grid[row].length;col++) {
				if (!GridValidatorUtils.isCellEmpty(grid[row][col])) {
					if (lastVal == grid[row][col]) {
						int updatedVal = 2*(grid[row][col]);
						grid[row][col-1] = updatedVal;
						grid[row][col] = 0;
						scoreGainedOnLeftMove += updatedVal;
						lastVal = -1;
					} else {
						lastVal = grid[row][col];
					}

				}
			}
		}
		return scoreGainedOnLeftMove;
	}
	
	public static int moveLeft(int[][] grid) {
		moveLeftSinglePass(grid);
		int scoresIncreased = mergeCellsOnLeftMove(grid);
		if(scoresIncreased > 0) {
			moveLeftSinglePass(grid);
		}
		return scoresIncreased;
	}

	public static void moveLeftSinglePass(int[][] grid) {
		for (int row = 0; row < grid.length; row++) {
			for(int col = 0, cellsEmptyToLeft = 0; col <grid[row].length;col++) {
				if (GridValidatorUtils.isCellEmpty(grid[row][col])) {
					cellsEmptyToLeft++;
				}  else {
					shiftLeft(grid, row, col, cellsEmptyToLeft);
				}
			}
		}
	}
	
	private static void shiftLeft(int[][] grid, int row, int col, int cellsEmptyToLeft) {
		if (cellsEmptyToLeft != 0) {
			grid[row][col - cellsEmptyToLeft] = grid[row][col];
			grid[row][col] = 0;
		}
	}
	
	
	public static int mergeCellsOnRightMove(int[][] grid) {
		int scoreGainedOnRightMove = 0;
		for (int row = 0; row < grid.length; row++) {
			for(int col = grid[row].length -1, lastVal = -1; col >= 0;col--) {
				if (!GridValidatorUtils.isCellEmpty(grid[row][col])) {
					if (lastVal == grid[row][col]) {
						int updatedValue = 2*(grid[row][col]);
						grid[row][col+1] = updatedValue;
						grid[row][col] = 0;
						scoreGainedOnRightMove += updatedValue;
						lastVal = -1;
					} else {
						lastVal = grid[row][col];
					}

				}
			}
		}
		return scoreGainedOnRightMove;
	}

	public static int moveRight(int[][] grid) {
		moveRightSinglePass(grid);
		int scoresIncreased = mergeCellsOnRightMove(grid);
		if (scoresIncreased != 0) {
			moveRightSinglePass(grid);
		}
		return scoresIncreased;

	}

	public static void moveRightSinglePass(int[][] grid) {
		for (int row = 0; row < grid.length; row++) {
			for(int col = grid[row].length -1, emptyCellsToRight = 0; col >= 0;col--) {
				if (GridValidatorUtils.isCellEmpty(grid[row][col])) {
					emptyCellsToRight++;
				}  else {
					shiftEmptyCellsToRight(grid, row, col, emptyCellsToRight);
				}
			}
		}
	}

	private static void shiftEmptyCellsToRight(int[][] grid, int row, int col, int emptyCellsToRight) {
		if (emptyCellsToRight!=0) {
			grid[row][col+emptyCellsToRight] = grid[row][col];
			grid[row][col] = 0;
		}
	}
	
	public static int mergeCellsOnMovingDown(int[][] grid) {
		int scoreGainedOnMovingDown = 0;
		for (int col = 0; col < grid.length ;col++) {
			for(int row = grid.length -1, lastVal = -1; row >=0; row--) {
				if (!GridValidatorUtils.isCellEmpty(grid[row][col])) {
					if (lastVal == grid[row][col]) {
						int updatedVal = 2*(grid[row][col]);
						grid[row+1][col] = updatedVal;
						grid[row][col] = 0;
						lastVal = -1;
						scoreGainedOnMovingDown += updatedVal;
					} else {
						lastVal = grid[row][col];
					}
				}
			}
		}
		return scoreGainedOnMovingDown;
	}

	public static int moveDown(int[][] grid) {
		moveDownSinglePass(grid);
		int scoresIncreased = mergeCellsOnMovingDown(grid);
		if (scoresIncreased !=0) {
			moveDownSinglePass(grid);
		}
		return scoresIncreased;
	}

	public static void moveDownSinglePass(int[][] grid) {
		for (int col = 0; col < grid.length ;col++) {
			for(int row = grid.length -1, emptyCellsAtBottom = 0; row >=0; row--) {
				if (GridValidatorUtils.isCellEmpty(grid[row][col])) {
					emptyCellsAtBottom++;
				}  else {
					shiftDown(grid, col, row, emptyCellsAtBottom);
				}
			}
		}
	}

	private static void shiftDown(int[][] grid, int col, int row, int emptyCellsAtBottom) {
		if (emptyCellsAtBottom!=0) {
			grid[row+emptyCellsAtBottom][col] = grid[row][col];
			grid[row][col] = 0;
		}
	}

	public static int moveUp(int[][] grid) {
		moveUpSinglePass(grid);
		int scoresIncreased = mergeCellsOnMovingUp(grid);
		if (scoresIncreased>0) {
			moveUpSinglePass(grid);
		}
		return scoresIncreased;
	}

	public static void moveUpSinglePass(int[][] grid) {
		for (int col = 0; col < grid.length; col++) {
			for(int row = 0, emptyCellsAtTop = 0; row <grid[col].length;row++) {
				if (GridValidatorUtils.isCellEmpty(grid[row][col])) {
					emptyCellsAtTop++;
				}  else {
					shiftUp(grid, col, row, emptyCellsAtTop);
				}
			}
		}
		
	}

	private static void shiftUp(int[][] grid, int col, int row,
			int emptyCellsAtTop) {
		if (emptyCellsAtTop!=0) {
			grid[row - emptyCellsAtTop][col] = grid[row][col];
			grid[row][col] = 0;
		}
	}
	
	public static int mergeCellsOnMovingUp(int[][] grid) {
		int scoreGainedOnMovingUp = 0;
		for (int col = 0; col < grid.length; col++) {
			for(int row = 0, lastVal = -1; row <grid[col].length;row++) {
				if (!GridValidatorUtils.isCellEmpty(grid[row][col])) {
					if (lastVal == grid[row][col]) {
						int updatedVal = 2*(grid[row][col]);
						grid[row-1][col] = updatedVal;
						scoreGainedOnMovingUp += updatedVal;
						grid[row][col] = 0;
						lastVal = -1;
					} else {
						lastVal = grid[row][col];
					}
				}
			}
		}
		return scoreGainedOnMovingUp;
	}

}
