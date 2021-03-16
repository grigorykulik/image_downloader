package scraper;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://wallhaven.cc/").get();
            System.out.printf("Title: %s\n", doc.title());

            Elements pictures=doc.getElementsByTag("img");
            System.out.println(pictures);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
