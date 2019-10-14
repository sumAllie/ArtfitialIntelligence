import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/renyuan/Desktop/RLE/src/test.jpg");
        BufferedImage image = ImageIO.read(file);
        File newFile = new File("/Users/renyuan/Desktop/RLE/src/binaryTest.jpg");
        ImageIO.write(Compress.grayImage(image), "jpg", newFile);

    }
}
