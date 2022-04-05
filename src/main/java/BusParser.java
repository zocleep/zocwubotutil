import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.HashMap;


public class BusParser {

    static public HashMap<String, String> getBuses() {
        HashMap<String, String> buses = new HashMap<>();
        buses.put("29", "https://yandex.ru/maps/213/moscow/stops/stop__9644435/?ll=37.570835%2C55.793944&tab=overview&z=17");
        // ...buses.put("...");

        return buses;
    }

    static public String getMinByURL(String url) {
        String result = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Element minTag = doc.getElementsByClass("masstransit-prognoses-view__title-text").get(0);
            result = minTag.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
