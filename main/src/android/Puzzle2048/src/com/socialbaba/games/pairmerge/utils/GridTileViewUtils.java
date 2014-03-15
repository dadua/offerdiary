package com.socialbaba.games.pairmerge.utils;

import com.socialbaba.games.pairmerge.Grid;

public class GridTileViewUtils {

	
	public static String[] getReadableValuesFromGrid (Grid grid) {
		
		int[] gridValues = grid.getGridValues();
		return getReadableValues(gridValues);
	}

	private static String[] getReadableValues(int[] gridValues) {
		String[] gridStrs = new String[gridValues.length];

		for (int i = 0; i < gridValues.length; i++) {
			int gridValue = gridValues[i];
			gridStrs[i] = getStringValueForGridIntValue(gridValue);
		}
		return gridStrs;
	}
	
	private static String getStringValueForGridIntValue (int gridValue) {
		if (gridValue == 0) {
			return "";//Implies empty
		} else {
			return Integer.toString(gridValue);
		}
	}
	
	public static String[] getReadableValuesFromTwoD (int[][] arr) {
		String[] gridStrs = new String[arr.length*arr.length];
		
		int[] oneDArrayFromTwoD = GridConversionUtils.getOneDArrayFromTwoD(arr);
		
		int i = 0;
		for (int arrVal : oneDArrayFromTwoD) {
			gridStrs[i] = getStringValueForGridIntValue(arrVal);
			i++;
		}
		
		return gridStrs;
	}

}
