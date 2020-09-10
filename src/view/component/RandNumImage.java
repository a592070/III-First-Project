package view.component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class RandNumImage {
    private String imgNum;

    public Image init() throws IOException {
        BufferedImage image = new BufferedImage(150,35,BufferedImage.TYPE_INT_RGB);
        //
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        // 矩形背景
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0,0,150,35);

        // 給圖片寫數據
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 20));
        imgNum = makeNumber();

        int x = 10;
        int y = 30;
        for (int i = 0; i < imgNum.length(); i++) {
            char c = imgNum.charAt(i);
            AffineTransform old = graphics.getTransform();

            graphics.rotate(Math.toRadians(new Random().nextInt(90)-45), x, y);
            graphics.drawString(String.valueOf(c), x, y);
            x+=30;

            graphics.setTransform(old);
        }
        return image;
    }

    private String makeNumber(){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int tmp = new Random().nextInt(10);
            stringBuffer.append(tmp);
        }
        return stringBuffer.toString();
    }
    public String getImgNum(){
        return imgNum;
    }

    public RandNumImage() {
    }
}
