package scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownloader {
    private static int imageCounter=0;
    private static String pathToFolder;

    public ImageDownloader(String pathToFolder) {
        ImageDownloader.pathToFolder=pathToFolder;
    }


    /**
     * This method receives a document obtained with the DocObtainer and searches for images.
     * Only image links ending with .jpg are considered valid.
     * @param doc The doc obtained by the DocObtainer.
     */
    public void downloadImages(Document doc, int size) {
        Elements images = doc.select("img");

        if (images.size()==0) {
            System.out.println("Sorry, no images have been found on this page");
        } else {
            for (Element e : images) {
                String imageURL = e.attr("abs:src");

                if (imageURL.endsWith("jpg")) {
                    downloadImage(imageURL, size);
                }
            }
        }
    }

    /**
     * This method receives an image URL and downloads the image.
     * To get the image size, the getContentLength() method is used.
     * If the image size is less than the user-defined value, this image is skipped.
      * @param imageURL The image url to be downloaded.
     */
    private static void downloadImage(String imageURL, int size) {

        System.out.println("Trying to download:  from: " + imageURL);

        try {
            URL urlImage = new URL(imageURL);
            URLConnection conn = urlImage.openConnection();

            int imageSizeBytes = conn.getContentLength();
            System.out.println("Size:" + imageSizeBytes);

            double imageSizeKiloBytes = (double) imageSizeBytes / 1024;
            System.out.println("Size:" + imageSizeKiloBytes + " KB");

            if (imageSizeKiloBytes < size) {
                System.out.println("Image too small. Skipping");
                return;
            } else {

                InputStream in = urlImage.openStream();

                byte[] buffer = new byte[4096];
                int n = -1;

                ImageDownloader.imageCounter++;
                String imageName = imageCounter + ".jpg";
                OutputStream os =
                        new FileOutputStream(ImageDownloader.pathToFolder + "/" + imageName);

                while ((n = in.read(buffer)) != -1) {
                    os.write(buffer, 0, n);
                }

                //close the stream
                os.close();

                System.out.println("Image saved");
            }
        } catch(IOException e){
                e.printStackTrace();
            }

    }
}
