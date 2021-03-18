package scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

public class ChildFinderGeneral {
    public Set<String> findChildren(Document doc) {
        Set<String> links = new HashSet<>();

        Elements elements=doc.select("a[href]");

        for (Element element : elements) {
            links.add(element.attr("href"));
        }

        System.out.println(links);

        return links;
    }
}
