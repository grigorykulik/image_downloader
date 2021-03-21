import org.jsoup.nodes.Document;
import org.junit.Test;
import scraper.App;
import scraper.ChildFinderGeneral;
import scraper.DocObtainer;
import scraper.ImageDownloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionTest {


    @Test
    public void shouldReadCorrectlyConfigFile() {
        App.init();

        String expectedStartPage="https://en.wikipedia.org/wiki/Folklore_(Taylor_Swift_album)";
        String actualStartPage=App.getStartPageUrl();
        assertTrue(expectedStartPage.equals(actualStartPage));

        int expectedSize=100;
        int actualSize=App.getImageSizeKilobytes();
        assertEquals(expectedSize, actualSize);

        String expectedDestination="D:/images";
        String actualDestination=App.getPathToFolder();
        assertTrue(expectedDestination.equals(actualDestination));
    }


    @Test
    public void shouldReturnDocumentWhenGivenValidUrl() {
        DocObtainer docObtainer = new DocObtainer();
        try {
            Document d = docObtainer.obtainDoc("https://www.google.com/");
            assertNotEquals(d, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldThrowExceptionWhenGivenInvalidUrl() {
        DocObtainer docObtainer = new DocObtainer();
        Exception e = assertThrows(IllegalArgumentException.class, ()-> docObtainer.obtainDoc("oogle.com/"));
        String expectedMessage = "Malformed URL";
        assertTrue(e.getMessage().contains(expectedMessage));
    }

    @Test
    public void linksSetShallNotBeEmptyWhenGivenValidUrl() {
        DocObtainer docObtainer = new DocObtainer();

        try {
            Document doc = docObtainer.obtainDoc("https://wallhaven.cc/w/j5mz95");
            ChildFinderGeneral cfg = new ChildFinderGeneral();
            Set<String> links=cfg.findChildren(doc);
            assertFalse(links.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shallNotDownloadImageLessThanSpecified() {
        try {
            DocObtainer docObtainer=new DocObtainer();
            Document d=docObtainer.obtainDoc("http://grigory-kulik.com/2020/07/cybersploit1-walkthrough/");
            ImageDownloader id = new ImageDownloader("D:/images");
            id.downloadImages(d, 100);

            Path directory = new File("D:/images").toPath().toAbsolutePath();
            try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
                assertTrue(!dirStream.iterator().hasNext());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
