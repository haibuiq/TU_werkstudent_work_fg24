package tud.bp.group32.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class ImageLoader {
    /**
     * Method that fetches a file from the resources folder, namely
     * java/tud/bp/group32/resources and creates an InputStream out of it.
     * This is ideal for fetching resources in a compiled form of the
     * program.
     * @param resourceName The path of the file relative to the resources folder
     * @return The image as an InputStream.
     */
    public InputStream getResourceAsStream(final String resourceName) {
        // Use ClassLoader to load image from resources
        return getClass()
                .getClassLoader()
                .getResourceAsStream(resourceName);
    }

    /**
     * Method that fetches a file from the resources folder, namely
     * java/tud/bp/group32/resources and gives the absolute path of it.
     * This is ideal for fetching resources in a compiled form of the
     * program.
     * @param path Relative path of the resource from resources folder.
     * @return Absolute path of the resource to be used in other places.
     */
    public URL getUrl(final String path) {
        return getClass().getClassLoader().getResource(path);
    }

    /**
     * Method that fetches a file from the resources folder, namely
     * java/tud/bp/group32/resources and creates a FileInputStream out of it.
     * This is ideal for fetching resources in a compiled form of the
     * program.
     * @param resourceName The path of the file relative to the resources folder
     * @return The file as a FileInputStream.
     * @throws IOException
     */
    public FileInputStream loadResourceAsFileInputStream(
            final String resourceName) throws IOException {
        // Use ClassLoader to load resource URL
        ClassLoader classLoader = getClass().getClassLoader();
        var resourceURL = classLoader.getResource(resourceName);

        if (resourceURL != null) {
            // Convert URL to File and open a FileInputStream
            File file = new File(resourceURL.getFile());
            return new FileInputStream(file);
        } else {
            throw new IOException("Resource not found: " + resourceName);
        }
    }

}
