package models;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import util.RayCastingAlgorithm;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * A model represents a geofence of polygon shape
 * @author temuge
 *
 */
public class PolygonGeofence extends Geofence {
	private static final String LAT = "lat";
	private static final String LNG = "lng";
	public List<LatLng> vertices;
	
	public PolygonGeofence() {
		super();
		this.vertices = new ArrayList<LatLng>();
	}

	@Override
	public boolean contains(LatLng latLng) {
		return RayCastingAlgorithm.contains(this, latLng);
	}
	
	public static PolygonGeofence from(String serializedGeofence) {
		PolygonGeofence gf = new PolygonGeofence();
		JsonParser parser = new JsonParser();
		JsonArray latLngs = parser.parse(serializedGeofence).getAsJsonArray();
		for (int i=0; i<latLngs.size(); i++) {
			JsonObject latLng = latLngs.get(i).getAsJsonObject();
			Double lat = latLng.get(LAT).getAsDouble();
			Double lng = latLng.get(LNG).getAsDouble();
			gf.add(new LatLng(lat, lng));
		}
		return gf;
	}
	
	private void add(LatLng latLng) {
		this.vertices.add(latLng);
		if (this.maxLat == null || this.maxLat < latLng.lat) {
			this.maxLat = latLng.lat;
		}
		if (this.minLat == null || this.minLat > latLng.lat) {
			this.minLat = latLng.lat;
		}
		if (this.maxLng == null || this.maxLng < latLng.lng) {
			this.maxLng = latLng.lng;
		}
		if (this.minLng == null || this.minLng > latLng.lng) {
			this.minLng = latLng.lng;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (LatLng latLng : this.vertices) {
			sb.append("\n" + latLng);
		}
		return sb.toString();
	}
}
