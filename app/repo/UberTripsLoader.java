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
		String rawQuery = "from UberTrip where ";
		rawQuery += String.join(" AND ", whereClauses);
		Query query = JPA.em().createQuery(rawQuery);
		List<UberTrip> trips = query.getResultList();
		return trips;
	}
	
	private static String getGeofenceWhereClause(
		Geofence geofence) 
	{
		String whereClause = "";
		whereClause += "((pickup_lat BETWEEN " + geofence.getMinLat() + 
				" AND " + geofence.getMaxLat() + ") AND " + 
				" (pickup_lng BETWEEN " + geofence.getMinLng() + " AND " + 
				geofence.getMaxLng() + ")) ";
		return whereClause;
	}
}
