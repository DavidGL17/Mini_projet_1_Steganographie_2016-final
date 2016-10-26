package main;

public final class MainAntonio {
    
	public static void main(String[] args) {
		
	}
	    public static String bitArrayToString(boolean[] bitArray) {
	    	String binaryString = "";
	    	for (int i = 0; i< bitArray.length; ++i){
	    	binaryString += bitArray[i];
	    	System.out.println(binaryString);
	    	}
	    	return binaryString;
    }
}
