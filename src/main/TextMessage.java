package main;


public class TextMessage {

    /*
     * ********************************************
     * Part 2a: prepare text message (text <-> bit array)
     * ********************************************
     */
    /**
     * Converts an integer to its binary representation
     * @param value The integer to be converted
     * @param bits The number of bits for {@code value}'s binary representation
     * @return A boolean array corresponding to {@code value}'s {@code bits}-bit binary representation
     */
    public static boolean[] intToBitArray(int value, int bits) {
		boolean[] binary = new boolean [bits];
    	int m = value;
	    String s = Integer.toBinaryString(m);
    	for (int i = bits - 1; i >= 0; --i){
    		if (s.charAt(i) == 1){
    			binary[i] = true;
    		} else {
    			binary[i] = false;
    		}		
		}
    	return binary;
    }

    /**
     * Converts a bit array to it's integer value
     * @param bitArray A boolean array corresponding to an integer's binary representation
     * @return The integer that the array represented
     */
    public static int bitArrayToInt(boolean[] bitArray) {
    	final int bolTrue = 0x00000001, bolFalse = 0xFFFFFFFE;
    	int value = 0;
    	for (int i = bitArray.length -1 ; i >= 0; --i){
    		value <<= 1;
    		if (bitArray[i]){
    			value |= bolTrue;
    		} else {
    			value &= bolFalse ;
    		}
    	}
    	return value;
    }

    /**
     * Converts a String to its binary representation, i.e. the sequence of the 16-bit binary representations of its chars' integer values
     * @param message The String to be converted
     * @return A boolean array corresponding to the String's binary representation
     */
    public static boolean[] stringToBitArray(String message) {
		boolean[] binaryTabMessage = new boolean [Character.SIZE * message.length()];
		int bites = 0;
    	for (int i = 0; i < message.length(); ++i){
    		char c = message.charAt(i);
    		int CharInt = (int)c;
    		boolean[] CharI = intToBitArray(CharInt, Character.SIZE);
    		for (int j = 0; j < CharI.length; ++j){
    			binaryTabMessage[bites] = CharI[j];
    			++bites;
    		}
    	}	
    	return binaryTabMessage;
    }

    /**
     * Converts a boolean array to the String of which it is the representation
     * @param bitArray A boolean array representing a String
     * @return The String that the array represented
     * @see TextMessage#stringToBitArray(String)
     */
    public static String bitArrayToString(boolean[] bitArray) {
        //TODO: implement me!
        return null;
    }

}
