package updater;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UpdateChecker {
    private static final String CURRENT_VERSION = "0.1.1";
    private static final String GITHUB_API_URL = "https://api.github.com/repos/Tsuzuito/LuckyStarGame/releases/latest";

    public static String getVersion(){ return CURRENT_VERSION; }

    //Get GitHub json response
    private static String fetchLatestReleaseJSON() throws Exception{
        //Client browser (kinda)
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder() // setup object
                .uri(URI.create(GITHUB_API_URL)) // set URL
                .header("User-Agent", "LuckyStarGame-Updater") // Signature of the app making the request
                .timeout(Duration.ofSeconds(5)) // timeout
                .GET() // we need to just read info (type of request)
                .build(); // finish. build object

        //send and get response. response is parsed to a string
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //code 200 is success.
        if(response.statusCode() == 200){
            return response.body();
        } else {
            throw new RuntimeException("GitHub request error. Response code: " + response.statusCode());
        }
    }

    //remove everything else, except tag_name
    public static String fetchLatestVersion() throws Exception{

        //get json response from GitHub as a String
        String rawJsonString = fetchLatestReleaseJSON();

        //Parse String back to JSON (Gson JsonObject)
        JsonObject jsonObject = JsonParser.parseString(rawJsonString).getAsJsonObject();

        //convert tag_name to a string
        String latestVersion = jsonObject.get("tag_name").getAsString();

        //Remove any unnecessary characters from the response (if any)
        return latestVersion.replaceAll("[^0-9.]", "");
    }

    public static boolean isUpdateAvailable(String currentVersion, String latestVersion){
        String[] currentParts = currentVersion.split("\\.");
        String[] latestParts = latestVersion.split("\\.");

        int maxLength = Math.max(currentParts.length, latestParts.length);

        for(int i = 0; i < maxLength; i++){
            int currentPart = i < currentParts.length ? Integer.parseInt(currentParts[i]) : 0;
            int latestPart = i < latestParts.length ? Integer.parseInt(latestParts[i]) : 0;

            if(latestPart > currentPart){
                System.out.println("Update is available");
                return true;
            } else if (latestPart < currentPart) {
                System.out.println("Update is not available(Current version is newer)");
                return false;

            }
        }
        System.out.println("You are using the latest version");
        return false;
    }
}
