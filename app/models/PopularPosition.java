package models;

import util.GoogleMapUtil;

import com.google.gson.JsonObject;

/**
 * a model represent  pickup/dropoff points with count
 * @author temuge
 *
 */
public class PopularPosition {
	public LatLng latLng;
	public Integer count;
	
	public static PopularPosition from(
		LatLng latLng, 
		Integer count) 
	{
		PopularPosition pp = new PopularPosition();
		pp.latLng = latLng;
		pp.count = count;
		return pp;
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("lat", latLng.lat);
		json.addProperty("lng", latLng.lng);
		json.addProperty("count", count);
		json.addProperty(
			"street_view_url", 
			GoogleMapUtil.buildStreetViewUrl(latLng.lat, latLng.lng));
		return json;
	}
	
	@Override
	public String toString() {
		return "{LatLng : " + latLng + ", count : " + count + "}";
	}
}
