package com.cyr1en.mcutils.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageUtil {

    public static BufferedImage loadBufferedImage(String s) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(ImageUtil.class.getResourceAsStream(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
