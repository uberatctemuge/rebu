package models;

import java.util.ArrayList;
import java.util.Collection;
/**
 * A data structure represents a request/query for get uber trips operation
 * 
 * In the distributed systems world, it could be replaced by
 * more message friendly objects
 * @author temuge
 *
 */
public class GetUberTripsRequest {
	private Collection<Geofence> geoFences;
	private boolean shouldContainPickups;
	private boolean shouldContainDropoffs;
	private boolean shouldContainWholeTrip;
	
	public GetUberTripsRequest() {
		this.geoFences = new ArrayList<>();
		shouldContainPickups = true; // default to true
		shouldContainDropoffs = false;
		shouldContainWholeTrip = false;
	}

	public Collection<Geofence> getGeoFences() {
		return geoFences;
	}

	public GetUberTripsRequest addGeofence(
		Geofence geofence) 
	{
		this.geoFences.add(geofence);
		return this;
	}
	
	public GetUberTripsRequest byPickups() {
		this.shouldContainPickups = true;
		return this;
	}
	
	public GetUberTripsRequest byDropoffs() {
		this.shouldContainDropoffs = true;
		return this;
	}
	
	public GetUberTripsRequest byWholeTrip() {
		this.shouldContainWholeTrip = true;
		return this;
	}

	public boolean shouldContainWholeTrip() {
		return shouldContainWholeTrip;
	}
	
	public boolean shouldContainPickups() {
		return shouldContainPickups;
	}
	
	public boolean shouldContainDropoffs() {
		return shouldContainDropoffs;
	}
}
