package main;
/**
 * @author
 */
public final class ImageMessage {


    /*
     * ********************************************
     * Part 1a: prepare image message (RGB image <-> BW image)
     * ********************************************
     */
    /**
     * Returns red component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
	public static int getRed(int rgb) {
    	int red = ((rgb & 0x00ff0000 ) >> 16);
    	return red;
    }

    /**
     * Returns green component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getGreen(int rgb) {
    	int green = ((rgb & 0x0000ff00 ) >> 8); 
    	return green;
    }

    /**
     * Returns blue component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getRGB(int, int, int)
     */
    public static int getBlue(int rgb) {
    	int blue = ((rgb & 0x000000ff ));
    	return blue;
    }

    /**
     * Returns the average of red, green and blue components from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int)
     */
    public static int getGray(int rgb) {
    	int gray = (getBlue(rgb)+getGreen(rgb)+getRed(rgb))/3;
        return gray;
    }

    /**
     * @param gray an integer between 0 and 255
     * @param threshold
     * @return true if gray is greater or equal to threshold, false otherwise
     */
    public static boolean getBW(int gray, int threshold) {
    	if (gray<threshold){
    		return false;
    	} else {
    		return true;
    	}
    }

    /**
     * Returns packed RGB components from given red, green and blue components.
     * @param red an integer between 0 and 255
     * @param green an integer between 0 and 255
     * @param blue an integer between 0 and 255
     * @return 32-bits RGB color
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     */
    public static int getRGB(int red, int green, int blue) {
    	int RGB = (red<<16)+(green<<8)+(blue);
        return RGB;
    }

    /**
     * Returns packed RGB components from given grayscale value.
     * @param gray an integer between 0 and 255
     * @return 32-bits RGB color
     * @see #getGray
     */
    public static int getRGB(int gray) {
    	int RGB = ((0x000000ff&gray)<<16)+((0x000000ff & gray)<<8)+(0x000000ff&gray);
        return RGB;
    }


    /**
    * Returns packed RGB components from a boolean value.
    * @param value a boolean
    * @return 32-bits RGB encoding of black if value is false
    * and encoding of white otherwise
    */
    public static int getRGB(boolean value) {
    	if (value == true){
    		return 255;
    	} else {
    		return 0;
    	}
    }


    /**
     * Converts packed RGB image to grayscale image.
     * @param image a HxW int array
     * @return a HxW int array
     * @see #encode
     * @see #getGray
     */
    public static int[][] toGray(int[][] image) {
    	int imageGray[][] = new int [image.length][image[0].length];
    	for (int i=0;i<image.length;++i){
    		for (int j=0;j<image[i].length;++j){
    			imageGray[i][j]=getGray(image[i][j]);
    		}
    	}
    	return imageGray;
    }

    /**
     * Converts grayscale image to packed RGB image.
     * @param channels a HxW float array
     * @return a HxW int array
     * @see #decode
     * @see #getRGB(float)
     */
    public static int[][] toRGB(int[][] gray) {
    	int[][] imageRGB = new int [gray.length][gray[0].length];
    	for (int i=0;i<gray.length;++i){
    		for(int j=0;j<gray[i].length;++j){
    		imageRGB[i][j]=getRGB(gray[i][j]);	
    		}
    	}
        return imageRGB;
    }

    /**
     * Converts grayscale image to a black and white image using a given threshold
     * @param gray a HxW int array
     * @param threshold an integer threshold
     * @return a HxW int array 
     */
    public static boolean[][] toBW(int[][] gray, int threshold) {
    	boolean[][] imageBW = new boolean [gray.length][gray[0].length];
    	for (int i=0;i<gray.length;++i){
    		for (int j=0;j<gray[i].length;++j){
    			imageBW[i][j] = getBW(gray[i][j],threshold);
    		}
    	}
        return imageBW;
    }

    /**
     * Converts a black and white image to packed RGB image
     * @param image a HxW boolean array (false stands for black)
     * @return a HxW int array
     */
    public static int[][] toRGB(boolean[][] image) {
    	int[][] imageRGB = new int [image.length][image[0].length];
    	for(int i=0;i<image.length;++i){
    		for(int j=0;j<image[i].length;++j){
    			int gray = getRGB(image[i][j]);
    			imageRGB[i][j]=getRGB(gray);
    		}
    	}
        return imageRGB;
    }
    
    //Methode permettant de passer d'une image en couleur à une image en noir blanc
    public static boolean[][] FromRGBtoBW(int[][] image, int threshold){
    	int[][] gray = toGray(image);
    	boolean[][] imageBW = toBW(gray,threshold);
    	return imageBW;
    }

    /*
     * ********************************************
     * Part 3: prepare image message for spiral encoding (image <-> bit array)
     * ********************************************
     */
    /**
     * Converts a black-and-white image to a bit array
     * @param bwImage A black and white (boolean) image
     * @return A boolean array containing the binary representation of the image's height and width (32 bits each), followed by the image's pixel values
     * @see ImageMessage#bitArrayToImage(boolean[])
     */
    public static boolean[] bwImageToBitArray(boolean[][] bwImage) {
    	boolean[] bitArray = new boolean [(bwImage.length*bwImage[0].length)+32*2];
    	int hauteur = bwImage.length;
    	int largeur = bwImage.length;
    	for (int i=0;i<32;++i){
    		int hauteurLastBit = (hauteur & 0x00000001);
    		if (hauteurLastBit == 1){
    			bitArray[i] = true;
    		} else {
    			bitArray[i] = false;
    		}
    		hauteur<<=hauteur;
    	}
    	for (int i=32;i<64;++i){
    		int largeurLastBit = (largeur & 0x00000001);
    		if (largeurLastBit == 1){
    			bitArray[i] = true;
    		} else {
    			bitArray[i] = false;
    		}
    		largeur<<=largeur;
    	}
    	int k = 63;
    	for (int i=0;i<bwImage.length;++i){
    		for (int j=0;j<bwImage[0].length;++j){
    			++k;
    			bitArray[k] = bwImage[i][j];
    		}
    	}
        return bitArray;
    }

    /**
     * Converts a bit array back to a black and white image
     * @param bitArray A boolean array containing the binary representation of the image's height and width (32 bits each), followed by the image's pixel values
     * @return The reconstructed image
     * @see ImageMessage#bwImageToBitArray(boolean[][])
     */
    public static boolean[][] bitArrayToImage(boolean[] bitArray) {
    	int hauteur = 0x00000000, largeur = 0x00000000;
    	for (int i =0;i<32;++i){
    		hauteur <<= hauteur;
    		if (bitArray[i]){
    			hauteur |= 0x00000001;
    		} else {
    			hauteur &= 0x11111110;
    		}
    	}
    	for (int i =32;i<64;++i){
    		largeur <<= largeur;
    		if (bitArray[i]){
    			largeur |= 0x00000001;
    		} else {
    			largeur &= 0x11111110;
    		}
    	}
    	boolean[][] bwImage = new boolean[hauteur][largeur];
    	int k = 63;
    	for (int i=0;i<hauteur;++i){
    		for (int j=0;j<largeur;++j){
    			++k;
    			bwImage[i][j] = bitArray[k];
    		}
    	}
        return bwImage;
    }

}
