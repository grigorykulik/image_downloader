import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

public class ConnectionTest {

    @Test
    public void testConnection() {
        try {
            Document doc = Jsoup.connect("https://wallhaven.cc/w/j5mz95").userAgent("Mozilla").get();
        } catch (IOException ioe) {
            ioe.printStackTrace();

        }
    }
}
