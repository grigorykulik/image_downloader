package scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

/**
 * The sole purpose of this class is to connect to a web-page and return the obtained Document object
 */
public class DocObtainer {
    public Document obtainDoc(String url) throws IOException {

        Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(10000).get();

        System.out.printf("Title: %s\n", doc.title());

        return doc;
    }
}
