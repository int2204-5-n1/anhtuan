/**
 * @author Maksym Pecheniuk
 * source: https://stackoverflow.com/questions/8147284/how-to-use-google-translate-api-in-my-java-application/16325094
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Translator {
//
//    public static void main(String[] args) throws IOException {
//        String text = "Hello world!";
//        //Translated text: Hallo Welt!
//        System.out.println("Translated text: " + translate("en", "vi", text));
//    }

    public static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbzCCNDnyfnbCnzX8xGa01H8hNrzS2Z494ib4OGXP_LE88ZsJ-O_/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}