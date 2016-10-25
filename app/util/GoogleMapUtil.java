package util;

public class GoogleMapUtil {
	public static String API_KEY = "AIzaSyAK1DISIHiXfJh31YclVn-CVxbowIA7oHE";
	public static String JAVASCRIPT_API_KEY = "AIzaSyCg9OnL_M87R_4nPGf4QJSI_t-BEkUJIMY";
	private static final String STREET_VIEW_URL_BASE = 
			"https://maps.googleapis.com/maps/api/streetview";
	
	public static String buildStreetViewUrl(
		double lat, 
		double lng) 
	{
		return new StringBuilder()
			.append(STREET_VIEW_URL_BASE)
			.append("?size=75x75&location=")
			.append(lat).append(",").append(lng)
			.append("&key=")
			.append(JAVASCRIPT_API_KEY)
			.toString();
	}
}
