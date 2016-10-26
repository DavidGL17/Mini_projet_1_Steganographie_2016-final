package main;

public final class MainAntonio {
    
	public static void main(String[] args) {
		
	
	boolean[] bitArray = new boolean[1];
	bitArray[1] = false;
    
		String binaryString = "";
    	int convert = 0;
    	char c = 0;
    	boolean[] binaryTab = new boolean [10];
    	for (int j = 0 ; j < bitArray.length; j += 8){		
    			for (int i = 0; i < 8; ++i){
    			binaryTab[i] = bitArray[j + i];
    			convert = TextMessage.bitArrayToInt(binaryTab);
    			c = (char)convert;
    			binaryString += c;
    			System.out.println(binaryString);
    			
    			}
    	
	}
	}
}
