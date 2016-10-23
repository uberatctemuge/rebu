package models;

import java.util.Objects;

public class LatLng {
	public Double lat;
	public Double lng;
	
	public LatLng(Double lat, Double lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("LatLng : {")
			.append("lat : " + lat)
			.append(", ")
			.append("lng : " + lng)
			.append("}")
			.toString();
	}
}
