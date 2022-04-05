import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class BusParser {

    static public HashMap<String, String> getBuses() {
        HashMap<String, String> buses = new HashMap<>();
        buses.put("29", "https://yandex.ru/maps/213/moscow/stops/stop__9644435/?ll=37.570835%2C55.793944&tab=overview&z=17");
        // ...buses.put("...");

        return buses;
    }

    static public String getMinByURL(String url) {
        String result = null;
        ArrayList<HashMap<String, String>> data = ProxyProvider.getProxies();
        Iterator<HashMap<String, String>> dataIterator = data.iterator();
        int counter = 0;
        do {
            try {
                HashMap<String, String> proxy = dataIterator.next();
                System.setProperty("http.proxyHost", proxy.get("ip"));
                System.setProperty("http.proxyPort", proxy.get("ip"));
                System.out.println("Proxy: " + proxy.get("ip") + ":" + proxy.get("port")+". " + "Try: " + Integer.toString(counter++));
                Document doc = Jsoup.connect(url).timeout(5 * 1000).get();
                if(doc.toString().contains("masstransit-prognoses-view__title-text")) {
                    Element minTag = doc.getElementsByClass("masstransit-prognoses-view__title-text").get(0);
                    result = minTag.text();
                } else {
                    System.out.println("Current proxy is failed. Trying next.");
                    continue;
                }

            } catch (IOException e) {
                if(e.getMessage().equals("Connection timed out: connect") || e.getMessage().equals("connect timed out")){
                    System.out.println("Current proxy is failed. Trying next.");
                    continue;
                } else {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }


            }
        } while (result == null && dataIterator.hasNext());

        if(!(result == null)) {
            System.out.println("Proxy: Successful");
        } else {
            System.out.println("Proxy: Fucking Yandex WON");
        }

        return result;
    }
}
