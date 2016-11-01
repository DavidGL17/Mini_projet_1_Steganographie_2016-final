package main;
import java.util.Scanner;

/**
 * @author
 */
public final class Main {
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
//		// pour séparer chaque couleur
//		int image[][] = Helper.read("images/AdeleBlochBauer/AdeleBlochBauer.png");
////		Helper.show(image, "AdeleBlochBauer");
//		int[][] gray = ImageMessage.toGray(image);
//		boolean[][] bw = ImageMessage.toBW(gray, 127);
//		for (int i = 0; i < image.length; ++i) {
//			for (int j = 0; j < image[i].length; ++j) {
//				System.out.println(bw[i][j]);
//				System.out.println((byte)(bw[i][j]?1:0));
////				int alpha = ((image[i][j] & 0xff000000) >> 24);
////				int red = ((image[i][j] & 0x00ff0000) >> 16);
////				int green = ((image[i][j] & 0x0000ff00) >> 8);
////				int blue = ((image[i][j] & 0x000000ff));
////				System.out.printf("image[%d][%d]=(%d, %d, %d, %d)\n", i, j, alpha, red, green, blue);
////				int RGB = ImageMessage.getRGB(red, green, blue);
////				System.out.println(image[i][j]);
////				System.out.println(RGB);
//			}
//			System.out.println();
//		}
			testsLineaires("images/Image_HD_Test/b807c2282ab0a491bd5c5c1051c6d312_rP0kQjJ.png", "images/TheStarryNight/TheStarryNight.png");
	}
	public static void testsLineaires (String ImageOuCacher, String imageACacher){
		int cover[][] = Helper.read(ImageOuCacher);
		int[][] image = Helper.read(imageACacher);
//		int[][] imageACacherGrande = new int[image.length][image[0].length*2];
//		int l = 0;
//		for (int m = 0;m<2;++m){
//			for (int i = 0;i<image.length;++i){
//				for (int j = 0+l;j<image[0].length+l;++j){
//					imageACacherGrande[i][j] = image[i][j-l];
//				}
//			}
//			l = image[0].length;
//		}
		Helper.show(cover, "image");
		Helper.show(image, "image à cacher");
		boolean[][] imageBW = ImageMessage.fromRGBtoBW(image,127);
		int[][] coverHidden = Steganography.embedBWImage(cover, imageBW);
		Helper.show(coverHidden, "CoverHidden");
		boolean[][] imageReveleeBW = Steganography.revealBWImage(coverHidden);
		int[][] ImageRevelee = ImageMessage.toRGB(imageReveleeBW);
		Helper.show(ImageRevelee, "ImageRevelee");
	}
}