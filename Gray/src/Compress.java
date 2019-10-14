import java.awt.image.BufferedImage;

public class Compress {

    /**
     * @param bufferedImage
     * @return
     * @throws Exception
     */


    public static BufferedImage grayImage(BufferedImage bufferedImage) throws Exception {

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        BufferedImage grayBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        int[][] RLEEcode = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final int color = bufferedImage.getRGB(x, y);
                final int r = (color >> 16) & 0xff;
                final int g = (color >> 8) & 0xff;
                final int b = color & 0xff;
                int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
                int newPixel = colorToRGB(255, gray, gray, gray);
                grayBufferedImage.setRGB(x, y, newPixel);
                RLEEcode [x][y] = newPixel;
            }
        }

        return grayBufferedImage;

    }
    /**
     * @param alpha
     * @param red
     * @param green
     * @param blue
     * @return
     */

    private static int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;

    }



}
