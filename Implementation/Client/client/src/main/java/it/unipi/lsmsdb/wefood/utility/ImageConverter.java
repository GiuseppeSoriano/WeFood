package it.unipi.lsmsdb.wefood.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.util.Base64;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;


public class ImageConverter {
    private static final String RECIPES_IMAGES_PATH = "RecipesImages/";
    public static final String TEMP_IMAGES_PATH = "TempImages/";
    public static final String IMAGE_EXTENSION = ".jpg";
    // Image Preview
    private static final int targetWidth = 60; // Adjust the target width for resizing

    public static String fromImagePathToString(String path){
        // path is relative to RECIPES_IMAGES_PATH

        try{
            String imagePath = RECIPES_IMAGES_PATH + path;
            InputStream inputStream = ImageConverter.class.getClassLoader().getResourceAsStream(imagePath);

            if (inputStream == null) {
                // Image not found
                System.out.println("Image not found. Returning null.");
                return null;
            }

            byte[] imageBytes = IOUtils.toByteArray(inputStream);

            // Encode the image bytes to Base64
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            
            return encodedImage;
        }
        catch(IOException e){
            System.out.println("IOException in fromImagePathToString: " + e);
        }

        return null;
    
    }

    public static String getPathOfTempImage(String imageName){
        // Use the class loader to get the resources directory
        ClassLoader classLoader = ImageConverter.class.getClassLoader();
        File resourcesDirectory = new File(classLoader.getResource("").getFile());
        // imageName is the name of the image without the extension
        String path = resourcesDirectory.getAbsolutePath() + "\\" + TEMP_IMAGES_PATH + imageName + IMAGE_EXTENSION;
        return path;
    }

    public static void fromStringToImage(String image, String imageName){
        // image is a Base64 string

        try {
            // Use the class loader to get the resources directory
            ClassLoader classLoader = ImageConverter.class.getClassLoader();
            File resourcesDirectory = new File(classLoader.getResource("").getFile());

            // Create the output file within the resources directory
            File outputFile = new File(resourcesDirectory, TEMP_IMAGES_PATH + imageName + IMAGE_EXTENSION);

            // We need to convert the image from Base64 to bytes
            byte[] imageBytes = Base64.getDecoder().decode(image);

            // Save the image to the file
            FileUtils.writeByteArrayToFile(outputFile, imageBytes);
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



//    public static void main(String[] args) {
//        System.out.println(convertImageToAsciiArt("image"));
//    }

    public static String convertImageToAsciiArt(String imagePath) {
        try {
            BufferedImage image = readImageFromResources(TEMP_IMAGES_PATH + imagePath + IMAGE_EXTENSION);
            String asciiArt = convertToAsciiArt(image, targetWidth);
            return asciiArt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage readImageFromResources(String imagePath) throws IOException {
        try (InputStream inputStream = ImageConverter.class.getClassLoader().getResourceAsStream(imagePath)) {
            if (inputStream != null) {
                return ImageIO.read(inputStream);
            } else {
                throw new IOException("Image not found: " + imagePath);
            }
        }
    }

    private static String convertToAsciiArt(BufferedImage image, int targetWidth) {
        // Adjust dimensions based on the target width
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        int targetHeight = (int) Math.ceil((double) targetWidth / originalWidth * originalHeight);

        StringBuilder asciiArt = new StringBuilder();

        // Use a set of characters that represent different intensity levels
        char[] asciiChars = {'@', '#', '8', '&', 'o', ':', '*', '.', ' '};

        for (int y = 0; y < targetHeight; y += 2) {
            for (int x = 0; x < targetWidth; x++) {
                int imageX = x * originalWidth / targetWidth;
                int imageY = y * originalHeight / targetHeight;

                int pixel = image.getRGB(imageX, imageY);
                int grayscale = (int) (((pixel >> 16) & 0xFF) * 0.299 + ((pixel >> 8) & 0xFF) * 0.587
                        + (pixel & 0xFF) * 0.114);
                char asciiChar = getAsciiChar(grayscale, asciiChars);
                asciiArt.append(asciiChar);
            }
            asciiArt.append("\n");
        }

        return asciiArt.toString();
    }

    private static char getAsciiChar(int grayscale, char[] asciiChars) {
        // Map grayscale value to ASCII character
        int index = grayscale * (asciiChars.length - 1) / 255;
        return asciiChars[index];
    }

}

    
