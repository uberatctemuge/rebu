package repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Geofence;
import models.UberTrip;
import play.db.jpa.JPA;
/**
 * A db utility loader that loads UberTrip from db
 * with various filter
 * @author temuge
 *
 */
public class UberTripsLoader {
	private List<String> whereClauses;
	
	public UberTripsLoader() {
		this.whereClauses = new ArrayList<>();
	}
	
	public UberTripsLoader byGeofences(
		Collection<Geofence> geofences) 
	{
		for (Geofence geofence : geofences) {
			byGeofence(geofence);
		}
		return this;
	}
	
	public UberTripsLoader byGeofence(Geofence geofence) {
		this.whereClauses.add(
			getGeofenceWhereClause(geofence));
		return this;
	}
	
	public List<UberTrip> load() {
		String rawQuery = "SELECT * from uber_trips where ";
		rawQuery += String.join(" AND ", whereClauses);
		Query query = JPA.em().createNativeQuery(rawQuery, UberTrip.class);
		List<UberTrip> trips = query.getResultList();
		return trips;
	}
	
	private static String getGeofenceWhereClause(
		Geofence geofence) 
	{
		String whereClause = "";
		whereClause += "((pickup_lat > " + geofence.getMinLat() + 
				" AND pickup_lat < " + geofence.getMaxLat() + ") AND " + 
				" (pickup_lng > " + geofence.getMinLng() + " AND pickup_lng < " + 
				geofence.getMaxLng() + ")) ";
		return whereClause;
	}
}
