package com.socialbaba.games.pairmerge.utils;

public class GridConversionUtils {

	private static final int GRID_DIMENSION = 4;
	
	public static int[] getOneDArrayFromTwoD(int[][] arr) {
		int [] grid = new int[arr.length*arr.length];
		
		for(int row=0; row < arr.length ;row++) {
			for (int col=0 ;col < arr[row].length; col++) {
				grid[getOneDIndexFor(row, col)] = arr[row][col];
			}
		}
		return grid;
		
	}
	
	public static int[][] getTwoDArrayFromOneD(int []arr) {
		int dimension = (int) Math.sqrt(arr.length);
		
		int [][] twoDArr = new int[dimension][dimension];
		
		for (int i=0; i<arr.length;i++) {
			twoDArr[getRow(i)] [getCol(i)] = arr[i];
		}
		return twoDArr;
		
	}
	

	private static int getOneDIndexFor(int row, int col) {
		return row*GRID_DIMENSION+col;
	}

	
	/*
	private void addARandomValAtLocation(int row, int col) {
		addARandomValAtOneDIndex(getOneDIndexFor(row, col));
	}
	*/
	
	private void fillRowColValuesForLinearIndex (Integer oneDIndex, Integer row, Integer col) {
		row = getRow(oneDIndex);
		col = getCol(oneDIndex);
	}
	
	private static int getCol(int oneDIndex) {
		return oneDIndex%GRID_DIMENSION;
	}

	private static int getRow(int oneDIndex) {
		return oneDIndex/GRID_DIMENSION;
	}


}
