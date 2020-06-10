/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilesforgooglemaps;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author erick
 */
public class ImageTools {

    public static void resize(String inputPath, String outputPath, int width, int height) throws IOException {
        File inputFile = new File(inputPath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        resize(inputImage, outputPath, width, height);
    }

    /**
     * Resizes an image by a percentage of original size (proportional).
     *
     * @param inputPath Path of the original image
     * @param outputPath Path to save the resized image
     * @param percent a double number specifies percentage of the output image
     * over the input image.
     * @throws IOException
     */
    public static void resize(String inputPath, String outputPath, double percent) throws IOException {
        File inputFile = new File(inputPath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputPath, outputPath, scaledWidth, scaledHeight);
    }
    
    public static void resize(BufferedImage buffer, String outputPath, int width, int height) throws IOException{
        BufferedImage outputImage = new BufferedImage(width, height, buffer.getType());

        Image imgSmall = buffer.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(imgSmall, 0, 0, width, height, null);
        g2d.dispose();

        String formatName = outputPath.substring(outputPath.lastIndexOf(".") + 1);

        File image = new File(outputPath);
        image.mkdirs();
        ImageIO.write(outputImage, formatName, image);
    }

    public static LinkedList<BufferedImage> split(String inputPath, int rows, int columns) {
        try {
            LinkedList<BufferedImage> tiles = new LinkedList<>();
            BufferedImage originalImgage = ImageIO.read(new File(inputPath));

            //total width and total height of an image
            int tWidth = originalImgage.getWidth();
            int tHeight = originalImgage.getHeight();

            //width and height of each piece
            int eWidth = tWidth / columns;
            int eHeight = tHeight / rows;

            int x = 0;
            int y = 0;

            for (int i = 0; i < rows; i++) {
                y = 0;
                for (int j = 0; j < columns; j++) {
                    try {
                        tiles.add(originalImgage.getSubimage(y, x, eWidth, eHeight));
                        //File outputfile = new File(outputPath);
                        //ImageIO.write(SubImgage, "png", outputfile);

                        y += eWidth;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                x += eHeight;
            }

            return tiles;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
