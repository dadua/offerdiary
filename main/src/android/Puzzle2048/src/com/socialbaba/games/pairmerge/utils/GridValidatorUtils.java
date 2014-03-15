package com.socialbaba.games.pairmerge.utils;


public class GridValidatorUtils {
	
	static boolean isCellEmpty(int gridValue) {
		return gridValue == 0;
	}

	public static boolean isGridFull(int[][] grid) {
		int[] oneDArrayFromTwoD = GridConversionUtils.getOneDArrayFromTwoD(grid);
		for (int i = 0; i < oneDArrayFromTwoD.length; i++) {
			if (isCellEmpty(oneDArrayFromTwoD[i])) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isMatchesAvailable() {
		//TODO: Implement this 
		return true;
	}

}
