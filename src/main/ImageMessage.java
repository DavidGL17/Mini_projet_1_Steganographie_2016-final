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
    	if (value){
    		return 0x00ffffff;
    	} else {
    		return 0x00000000;
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
    	int largeur = bwImage[0].length;
    	//On fait appelle à la méthode de textMessage permettant de créer un tableau de boolean à partir d'un int (ici pour obtenir la hauteur puis la largeur)
    	//La valeur k permet d'itterer sur le tableau à une dimension
    	boolean[] hauteurBitArray = TextMessage.intToBitArray(hauteur, Integer.SIZE); 
    	int k = 0;
    	for (int i=0;i<hauteurBitArray.length;++i){
    		bitArray[k] = hauteurBitArray[i];
    		++k;
    	}
    	boolean[] largeurBitArray = TextMessage.intToBitArray(largeur, Integer.SIZE);
    	for (int i=0;i<largeurBitArray.length;++i){
    		bitArray[k] = largeurBitArray[i];
    		++k;
    	}
    	for (int i=0;i<bwImage.length;++i){
    		for (int j=0;j<bwImage[0].length;++j){
    			bitArray[k] = bwImage[i][j];
    			++k;
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
    	boolean[] hauteurBitArray = new boolean[Integer.SIZE];
    	boolean[] largeurBitArray = new boolean[Integer.SIZE];
    	//Cette boucle permet de récupérer la hauteur et la largeur du tableau à une dimension en une fois(auu lieu d'avoir une boucle pour la hauteur 
    	//et une autre pour la largeur
    	for (int i=0;i<32;++i){
    		hauteurBitArray[i] = bitArray[i];
    		largeurBitArray[i] = bitArray[i+32];
    	}
    	int hauteur = TextMessage.bitArrayToInt(hauteurBitArray), largeur = TextMessage.bitArrayToInt(largeurBitArray);
    	boolean[][] bwImage = new boolean[hauteur][largeur];
    	int k = 63;
    	//On initialise k à 63 pour ne pas reprendre les pixels contenant la hauteur et la largeur (nous aurions pu initilaiser k à 64 si nous avions mis le ++k 
    	//après "bwImage[i][j] = bitArray[k];")
    	for (int i=0;i<hauteur;++i){
    		for (int j=0;j<largeur;++j){
    			++k;
    			bwImage[i][j] = bitArray[k];
    		}
    	}
        return bwImage;
    }

}
