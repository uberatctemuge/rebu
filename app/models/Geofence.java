package models;

public abstract class Geofence {

	protected Double maxLat, minLat, maxLng, minLng;
	
	public Geofence() {
		this.maxLat = null;
		this.minLat = null;
		this.maxLng = null;
		this.minLng = null;
	}
	
	public abstract boolean contains(LatLng latLng);

	public Double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(Double maxLat) {
		this.maxLat = maxLat;
	}

	public Double getMinLat() {
		return minLat;
	}

	public void setMinLat(Double minLat) {
		this.minLat = minLat;
	}

	public Double getMaxLng() {
		return maxLng;
	}

	public void setMaxLng(Double maxLng) {
		this.maxLng = maxLng;
	}

	public Double getMinLng() {
		return minLng;
	}

	public void setMinLng(Double minLng) {
		this.minLng = minLng;
	}
}
