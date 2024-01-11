package it.unipi.lsmsdb.wefood.utility;

import java.io.File;

public class Cleaner {
    private static final String TEMP_IMAGES_PATH = "TempImages/";
    
    public static void cleanTempFolder(){
        // Ottiene il percorso della cartella 'resources'
        // Use the class loader to get the resources directory
        ClassLoader classLoader = Cleaner.class.getClassLoader();
        File resourcesDirectory = new File(classLoader.getResource("").getFile());
        
        File folder = new File(resourcesDirectory, TEMP_IMAGES_PATH);
        deleteFolderContents(folder);
    }

    private static void deleteFolderContents(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolderContents(f);
                    f.delete();
                } else {
                    f.delete();
                }
            }
        }
    }
    
}
