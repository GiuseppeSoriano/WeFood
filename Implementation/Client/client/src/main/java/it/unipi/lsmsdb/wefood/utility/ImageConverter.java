package it.unipi.lsmsdb.wefood.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.util.Base64;


public class ImageConverter {

    private static final String RECIPES_IMAGES_PATH = "RecipesImages/";
    private static final String TEMP_IMAGES_PATH = "TempImages/";

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

    public static void fromStringToImage(String image, String imagePath){
        // image is a Base64 string

        try {
            // Use the class loader to get the resources directory
            ClassLoader classLoader = ImageConverter.class.getClassLoader();
            File resourcesDirectory = new File(classLoader.getResource("").getFile());

            // Create the output file within the resources directory
            File outputFile = new File(resourcesDirectory, TEMP_IMAGES_PATH + imagePath);

            // We need to convert the image from Base64 to bytes
            byte[] imageBytes = Base64.getDecoder().decode(image);

            // Save the image to the file
            FileUtils.writeByteArrayToFile(outputFile, imageBytes);
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

    
