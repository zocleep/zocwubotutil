import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProxyProvider {

    public static ArrayList<HashMap<String, String>> getProxies() {
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        try {
            String jsonString = Jsoup.connect("https://proxylist.geonode.com/api/proxy-list?limit=50&page=1&sort_by=lastChecked&sort_type=desc").ignoreContentType(true).execute().body();
            JSONObject json = new JSONObject(jsonString);
            JSONArray levelOne = (JSONArray) json.get("data");
            for (Object element : levelOne) {
                HashMap<String, String> proxy = new HashMap<>();
                proxy.put("ip", (String) (((JSONObject) element).get("ip")));
                proxy.put("port", (String) (((JSONObject) element).get("port")));
                data.add(proxy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
