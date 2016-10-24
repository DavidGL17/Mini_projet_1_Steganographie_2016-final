package main;

/**
 * @author
 */
public final class Main {

	public static void main(String[] args) {
		// pour séparer chaque couleur
		int image[][] = Helper.read("images/AdeleBlochBauer/AdeleBlochBauer.png");
//		Helper.show(image, "AdeleBlochBauer");

		for (int i = 0; i < image.length; ++i) {
			for (int j = 0; j < image[i].length; ++j) {
				int alpha = ((image[i][j] & 0xff000000) >> 24);
				int red = ((image[i][j] & 0x00ff0000) >> 16);
				int green = ((image[i][j] & 0x0000ff00) >> 8);
				int blue = ((image[i][j] & 0x000000ff));
				System.out.printf("image[%d][%d]=(%d, %d, %d, %d)\n", i, j, alpha, red, green, blue);
				int RGB = ImageMessage.getRGB(red, green, blue);
				System.out.println(image[i][j]);
				System.out.println(RGB);
			}
			System.out.println();
		}
	}

}