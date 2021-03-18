package scraper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.Properties;
import org.jsoup.nodes.Document;

public class App {
    private static String startPageUrl;
    private static int imageSizeKilobytes;

    public static void main(String[] args) throws IOException {
        init();

        DocObtainer io = new DocObtainer();
        ImageDownloader id = new ImageDownloader();
        ChildFinderGeneral cfg = new ChildFinderGeneral();
        Set<String> childrenPages;

        try {
            Document doc = io.obtainDoc(startPageUrl);
            id.downloadImages(doc, imageSizeKilobytes);
            childrenPages = cfg.findChildren(doc);

            String childUrl;
            for (String s : childrenPages) {
                System.out.println("Current child: " + s);

                if (s.startsWith("/wiki/") || s.startsWith("/w/")) {
                    childUrl = "https://en.wikipedia.org" + s;
                    System.out.println(childUrl);
                } else {
                    childUrl=s;
                }

                try {
                   Document newDoc = io.obtainDoc(childUrl);
                    id.downloadImages(newDoc, imageSizeKilobytes);
                } catch (Exception e) {
                    System.out.println("Malformed url. URL handling needs to be improved. Skipping this page.");
                }

            }

        } catch (IOException e) {
            System.out.println("Could not connect to the specified page");
        }
    }

    public static void init() {
        try (InputStream input = new FileInputStream("src\\main\\resources\\config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            startPageUrl=prop.getProperty("url");
            imageSizeKilobytes=Integer.parseInt(prop.getProperty("size"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
