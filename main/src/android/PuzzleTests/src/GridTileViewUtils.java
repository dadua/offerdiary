

public class GridTileViewUtils {
	
	
	private static String getStringValueForGridIntValue (int gridValue) {
		if (gridValue == 0) {
			return "";
		} else {
			return Integer.toString(gridValue);
		}
	}
	
	public static String[] getReadableValuesFromTwoD (int[][] arr) {
		String[] gridStrs = new String[arr[0].length * arr.length];
		
		for(int row=0 ; row < arr.length ;row++) {
			int[] rowArray = arr[row];
			for (int col=0 ;col < rowArray.length; col++) {
				System.out.println(rowArray[col]);
			}
		}
		return gridStrs;
	}
	
	
	public static void main(String[] args) {
		int arr[][] =  {
				{0,1,2},
				{3,4,5}
		};
		getReadableValuesFromTwoD(arr);
		
	}
	
	

}
