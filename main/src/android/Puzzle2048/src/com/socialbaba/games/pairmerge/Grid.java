package com.socialbaba.games.pairmerge;

import java.util.Random;

public class Grid {
	
	private static final int GRID_DIMENSION = 4;

	private static final int GRID_CAPACITY = GRID_DIMENSION*GRID_DIMENSION;
	
	private int[] gridValues = new int[GRID_CAPACITY];
	
	private final Random random = new Random();
	
	public Grid() {
		this.addARandomValAtAnyAvailableIndex();
		this.addARandomValAtAnyAvailableIndex();
	}

	private boolean isIndexEmpty (int oneDIndex) {
		return gridValues[oneDIndex] == 0;
	}
	
	public void addARandomValAtAnyAvailableIndex() {
		if(isGridFull()) {
			return;
		}
		while(true) {
			int randomAvailableOneDIndex = getRandomOneDGridIndex();
			if (isIndexEmpty(randomAvailableOneDIndex)) {
				addARandomValAtOneDIndex(randomAvailableOneDIndex);
				break;
			}
		}
	}
	
	public boolean isGridFull() {
		for (int i=0;i < gridValues.length;i++) {
			if (isIndexEmpty(i)) {
				return false;
			}
		}
		return true;
	}

	private void addARandomValAtOneDIndex(int oneDIndexLocation) {
		gridValues[oneDIndexLocation] = getRandomBootstrapInt();
	}
	
	
	private int getRandomBootstrapInt() {
		return random.nextFloat() > .8? 4: 2;
	}

	private int getRandomOneDGridIndex() {
		return random.nextInt(GRID_CAPACITY-1);
	}
	
	public int[] getGridValues () {
		return gridValues;
	}
	
	public void setGridValues(int[] gridValues) {
		this.gridValues = gridValues;
	}

}
