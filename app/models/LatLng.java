package models;

import java.util.Objects;
/**
 * A data structure representing a Lat,Lng pair
 * @author temuge
 *
 */
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
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LatLng)) return false;
		LatLng other = (LatLng)o;
		Double epsilon = 0.000001;
		return (Math.abs(lat - other.lat) < epsilon) &&
				(Math.abs(lng - other.lng) < epsilon);
	}
	
	@Override
	public int hashCode() {
		return lat.hashCode()*71 + lng.hashCode() + 31;
	}
}
