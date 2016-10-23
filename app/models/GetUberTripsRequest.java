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
	
	public GetUberTripsRequest() {
		this.geoFences = new ArrayList<>();
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
}
