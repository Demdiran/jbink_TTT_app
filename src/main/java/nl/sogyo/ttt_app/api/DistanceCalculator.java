package nl.sogyo.ttt_app.api;

import LocationIq.ApiClient;
import LocationIq.ApiException;
import LocationIq.Configuration;
import LocationIq.auth.*;
// import LocationIq.models.*;

import java.util.List;

import com.locationiq.client.api.SearchApi;
import com.locationiq.client.model.Location;

public class DistanceCalculator{
    ApiClient defaultClient;

    String q; // String | Address to geocode
    String format = "json"; // String | Format to geocode. Only JSON supported for SDKs
    Integer normalizecity = 1; // Integer | For responses with no city value in the address section, the next available element in this order - city_district, locality, town, borough, municipality, village, hamlet, quarter, neighbourhood - from the address section will be normalized to city. Defaults to 1 for SDKs.
    Integer addressdetails = 1; // Integer | Include a breakdown of the address into elements. Defaults to 0.
    String viewbox = null;//"-132.84908,47.69382,-70.44674,30.82531"; // String | The preferred area to find search results.  To restrict results to those within the viewbox, use along with the bounded option. Tuple of 4 floats. Any two corner points of the box - `max_lon,max_lat,min_lon,min_lat` or `min_lon,min_lat,max_lon,max_lat` - are accepted in any order as long as they span a real box. 
    Integer bounded = null;//1; // Integer | Restrict the results to only items contained with the viewbox
    Integer limit = 10; // Integer | Limit the number of returned results. Default is 10.
    String acceptLanguage = "en"; // String | Preferred language order for showing search results, overrides the value specified in the Accept-Language HTTP header. Defaults to en. To use native language for the response when available, use accept-language=native
    String countrycodes = "nl"; // String | Limit search to a list of countries.
    Integer namedetails = 1; // Integer | Include a list of alternative names in the results. These may include language variants, references, operator and brand.
    Integer dedupe = 1; // Integer | Sometimes you have several objects in OSM identifying the same place or object in reality. The simplest case is a street being split in many different OSM ways due to different characteristics. Nominatim will attempt to detect such duplicates and only return one match; this is controlled by the dedupe parameter which defaults to 1. Since the limit is, for reasons of efficiency, enforced before and not after de-duplicating, it is possible that de-duplicating leaves you with less results than requested.
    Integer extratags = 0; // Integer | Include additional information in the result if available, e.g. wikipedia link, opening hours.
    Integer statecode = 0; // Integer | Adds state or province code when available to the statecode key inside the address element. Currently supported for addresses in the USA, Canada and Australia. Defaults to 0
    Integer matchquality = 0; // Integer | Returns additional information about quality of the result in a matchquality object. Read more Defaults to 0 [0,1]
    Integer postaladdress = 0; // Integer | Returns address inside the postaladdress key, that is specifically formatted for each country. Currently supported for addresses in Germany. Defaults to 0 [0,1]

    public static void main(String[] args) {
    }

    public DistanceCalculator(){
        defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://eu1.locationiq.com/v1");
        
        // Configure API key authorization: key
        ApiKeyAuth key = (ApiKeyAuth) defaultClient.getAuthentication("key");
        key.setApiKey("73f83eaf658234");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //key.setApiKeyPrefix("Token");
    }

    public double calculateDistance(String adress1, String adress2){
        double lon1 = 0;
        double lat1 = 0;
        double lon2 = 0;
        double lat2 = 0;
        List<Location> result;
        SearchApi apiInstance = new SearchApi(defaultClient);
        
        q = adress1;
        try {
          result = apiInstance.search(q, format, normalizecity, addressdetails, viewbox, bounded, limit, acceptLanguage, countrycodes, namedetails, dedupe, extratags, statecode, matchquality, postaladdress);
          lon1 = Double.parseDouble(result.get(0).getLon());
          lat1 = Double.parseDouble(result.get(0).getLat());
          System.out.println(result.get(0));
        } catch (ApiException e) {
          System.err.println("Exception when calling SearchApi#search");
          System.err.println("Status code: " + e.getCode());
          System.err.println("Reason: " + e.getResponseBody());
          System.err.println("Response headers: " + e.getResponseHeaders());
          e.printStackTrace();
        }

        q = adress2;
        try {
          result = apiInstance.search(q, format, normalizecity, addressdetails, viewbox, bounded, limit, acceptLanguage, countrycodes, namedetails, dedupe, extratags, statecode, matchquality, postaladdress);
          lon2 = Double.parseDouble(result.get(0).getLon());
          lat2 = Double.parseDouble(result.get(0).getLat());
          System.out.println(result.get(0));
        } catch (ApiException e) {
          System.err.println("Exception when calling SearchApi#search");
          System.err.println("Status code: " + e.getCode());
          System.err.println("Reason: " + e.getResponseBody());
          System.err.println("Response headers: " + e.getResponseHeaders());
          e.printStackTrace();
        }
        return calculateDistanceFromDegrees(lon1, lat1, lon2, lat2);
    }

    private double calculateDistanceFromDegrees(double lon1, double lat1, double lon2, double lat2){
      double deltaLon = degreesToRadians(lon1 - lon2);
      double deltaLat = degreesToRadians(lat1 - lat2);
      lat1 = degreesToRadians(lat1);
      lat2 = degreesToRadians(lat2);
      double a = Math.pow(Math.sin(deltaLat/2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLon/2), 2);
      double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
      double distance = 6371000 * c;
      return distance;
    }

    private double degreesToRadians(double degrees){
      return degrees * Math.PI / 180;
    }

}