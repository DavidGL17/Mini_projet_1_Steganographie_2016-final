package main;

import java.util.Arrays;

import javax.rmi.CORBA.Util;

import static org.junit.Assert.*;

public class Steganography {

    /*
     * ********************************************
     * Part 1b: embed/reveal BW
     * image ********************************************
     */

    /*
     * Methods to deal with the LSB
     */
    /**
     * Inserts a boolean into the Least Significant Bit of an int
     * @param value The int in which to insert a boolean value
     * @param m The boolean value to be inserted into the int
     * @return An int corresponding to {@code value} with {@code m} inserted into its LSB
     */
    public static int embedInLSB(int value, boolean m) {
    	int newValue = 0;
    	if (m){
    		newValue = (value | 0b00000000_00000000_00000000_00000001);
    	} else {
    		newValue = (value & 0b11111111_11111111_11111111_11111110);
    	}
        return newValue;
    }

    /**
     * Extracts the Least Significant Bit of an integer
     * @param value The integer from which to extract the LSB value
     * @return A boolean corresponding to the value of {@code value}'s LSB
     */
    public static boolean getLSB(int value) {
    	value = (value & 0b00000000_00000000_00000000_00000001);
    	if (value == 0b00000000_00000000_00000000_00000001){
    		return true;
    	} else {
            return false;
    	}
    }

    /*
     * Linear embedding
     */
    /**
     * Embeds a black and white image into a color image's LSB layer using linear embedding
     * @param cover The image in which to embed {@code message}
     * @param message The image to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s pixel values embedded in a linear fashion in the LSB layer
     */
    public static int[][] embedBWImage(int[][] cover, boolean[][] message) {
    	assertTrue(Utils.isCoverLargeEnough(cover, message));
    	int[][] coverHidden = new int [cover.length][cover[0].length];
    	for (int i=0;i<coverHidden.length;++i){
    			for(int j=0;j<coverHidden[0].length;++j){
    				if (j<message[0].length&&(i<message.length)){
    					coverHidden[i][j] = embedInLSB(cover[i][j], message[i][j]);
    				} else {
    					coverHidden[i][j] = cover[i][j];
    				}
    			}
    	}
        return coverHidden;
    }

    /**
     * Reveals a black and white image which was embedded in the LSB layer of another
     * @param cover A color image containing an image embedded in its LSB layer
     * @return The image extracted from the LSB layer of {@code cover}
     */
    public static boolean[][] revealBWImage(int[][] cover) {
    	boolean[][] messageHidden = new boolean [cover.length][cover[0].length];
    	for(int i=0;i<cover.length;++i){
    		for (int j=0;j<cover[i].length;++j){
    			messageHidden[i][j]=getLSB(cover[i][j]);
    		}
    	}
        return messageHidden;
    }

    /*
     * ********************************************
     * Part 2b: embed/reveal
     * BitArray (Text)
     ********************************************
     */

    /**
     * Embeds a boolean array into the LSB layer of a color image, in a linear fashion
     * @param cover The image in which to embed the bit array
     * @param message The boolean array to be embedded
     * @return A <b>copy</b> of {@code cover} with {@code message}'s values embedded in a linear fashion in the LSB layer
     */
    public static int[][] embedBitArray(int[][] cover, boolean[] message) {
    	int m = 0;
    	int[][] coverHidden = new int [cover.length][cover[0].length];
    	for (int i = 0; i < cover.length; ++i){
    		for (int j = 0; j < cover[0].length; ++j){
    			if (m < message.length){
    				coverHidden[i][j]  = embedInLSB(cover[i][j], message[m]);
    				++m;
    			} else {
					coverHidden[i][j] = cover[i][j];
    			}
    		}	
    	}
    	return coverHidden;
    }

    /**
     * Reveals a boolean array which was embedded in the LSB layer of an image
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The bit array extracted from the LSB layer of {@code cover}
     */
    public static boolean[] revealBitArray(int[][] cover) {
    	int m = 0;
    	boolean[] reveal =  new boolean [cover.length*cover[0].length];  
    	for (int i = 0; i < cover.length; ++i){
    		for (int j = 0; j < cover[0].length; ++j){
    			reveal[m] = getLSB(cover [i][j]);
    			++m;
    		}
    	}
    	return reveal;
    }

    /**
     * Embeds a String into the LSB layer of a color image, in a linear fashion
     * @param cover The image in which to embed the bit array
     * @param message The String to be embedded
     * @return A <b>copy</b> of {@code cover} with {@code message}'s binary representation embedded in a linear fashion in the LSB layer
     * @see TextMessage#stringToBitArray(String)
     * @see Steganography#embedBitArray(int[][], boolean[])
     */
    public static int[][] embedText(int[][] cover, String message) {
    	boolean[] bitMessage = TextMessage.stringToBitArray(message);
    	assertTrue(Utils.isCoverLargeEnough(cover, bitMessage));
    	int[][] hiddenMessage = embedBitArray(cover, bitMessage);			
    	return hiddenMessage;
    }

    /**
     * Reveals a String which was embedded in the LSB layer of an image
     * @param cover A color image containing a String embedded in its LSB layer
     * @return The String extracted from the LSB layer of {@code cover}
     * @see TextMessage#bitArrayToString(boolean[])
     */
    public static String revealText(int[][] cover) {
    	boolean[] toreveal = revealBitArray(cover);
    	String revealed = TextMessage.bitArrayToString(toreveal);
    	return revealed;
    }

    /*
     * ********************************************
     * Part 3: embed/reveal bit
     * array with spiral embedding
     ********************************************
     */

    /**
     * Embeds a black and white image into a color image's LSB layer using spiral embedding
     * @param cover The image in which to embed {@code message}
     * @param message The image to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s pixel values embedded in a spiral fashion in the LSB layer
     * @see ImageMessage#bwImageToBitArray(boolean[][])
     * @see Steganography#embedSpiralBitArray(int[][], boolean[])
     */
    public static int[][] embedSpiralImage(int[][] cover, boolean[][] bwImage) {
    	assertTrue(Utils.isCoverLargeEnough(cover, bwImage));
    	boolean[] message = ImageMessage.bwImageToBitArray(bwImage);
    	int[][] coverHidden = embedSpiralBitArray(cover, message);
        return coverHidden;
    }

    /**
     * Reveals an image which was embedded in the LSB layer of an image in a spiral fashion
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The image extracted from the LSB layer of {@code cover}
     * @see ImageMessage#bitArrayToImage(boolean[])
     * @see Steganography#revealSpiralBitArray(int[][])
     */
    public static boolean[][] revealSpiralImage(int[][] cover) {
    	boolean[] message = revealSpiralBitArray(cover);
    	boolean[][] imageBW = ImageMessage.bitArrayToImage(message);
        return imageBW;
    }

    /**
     * Embeds a bit array into a color image's LSB layer using linear embedding
     * @param cover The image in which to embed {@code message}
     * @param message The boolean array to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s values embedded in a spiral fashion in the LSB layer
     */
    public static int[][] embedSpiralBitArray(int[][] cover, boolean[] message) {
		assert(Utils.isCoverLargeEnough(cover, message)); // example of how to use assertions
		int[][] coverHidden = new int [cover.length][cover[0].length];
		int m = 0;
		for (int reduction =0;reduction<cover.length/2;++reduction){
			if (m<message.length){
				for (int i =0+reduction;i<cover[0].length-reduction;++i){
					if (m<message.length){
						coverHidden[reduction][i] = embedInLSB(cover[reduction][i], message[m]);
						++m;
					}
				}
				for (int j = 1+reduction;j<cover.length-reduction;++j){
					if (m<message.length){
						coverHidden[j][cover[0].length-1-reduction] = embedInLSB(cover[j][cover[0].length-1-reduction], message[m]);
						++m;
					}
				}
				for (int k = cover[0].length-2-reduction;k>=0+reduction;--k){
					if (m<message.length){
						coverHidden[cover.length-1-reduction][k] = embedInLSB(cover[cover.length-1-reduction][k], message[m]);
						++m;
					}
				}
				for (int l = cover.length-2-reduction;l>0+reduction;--l){
					if (m<message.length){
						coverHidden[l][reduction] = embedInLSB(cover[l][reduction], message[m]);
						++m;
					}
				}
			}
		}
        return coverHidden;
    }

    /**
     * Reveals a boolean array which was embedded in the LSB layer of an image in a spiral fashion
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The bit array extracted from the LSB layer of {@code cover}
     */
    public static boolean[] revealSpiralBitArray(int[][] hidden) {
    	boolean[] bitArray = new boolean[hidden.length*hidden[0].length];
    	int m = 0;
		for (int reduction =0;reduction<hidden.length/2;++reduction){
			for (int i =0+reduction;i<hidden[0].length-reduction;++i){
				if (m<bitArray.length){
					bitArray[m]= getLSB(hidden[reduction][i]);
					++m;
				}
			}
			for (int j = 1+reduction;j<hidden.length-reduction;++j){
				if (m<bitArray.length){
					bitArray[m] = getLSB(hidden[j][hidden[0].length-1-reduction]);
					++m;
				}
			}
			for (int k = hidden[0].length-2-reduction;k>=0+reduction;--k){
				if (m<bitArray.length){
					bitArray[m] = getLSB(hidden[hidden.length-1-reduction][k]);
					++m;
				}
			}
			for (int l = hidden.length-2-reduction;l>0+reduction;--l){
				if (m<bitArray.length){
					bitArray[m] = getLSB(hidden[l][reduction]);
					++m;
				}
			}
		}
        return bitArray;
    }

}

