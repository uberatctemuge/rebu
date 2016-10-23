package controllers;

import java.util.List;

import javax.persistence.Query;

import models.GetUberTripsRequest;
import models.PolygonGeofence;
import models.UberTrip;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import play.db.jpa.JPA;
import play.mvc.Controller;
import repo.UberTripRepo;
/**
 * A controller that handles various requests of Uber pickup/dropoff locations
 * @author temuge
 *
 */
public class UberController extends Controller{
	
	/**
	 * POST endpoint which handles an event of 
	 * complete shape is drawn on the map
	 * 
	 * @param type
	 * @param jsonVertices
	 */
	public static void overlayComplete(
			String type, 
			String jsonVertices) 
	{
		PolygonGeofence geoFence = PolygonGeofence
			.from(jsonVertices);
		List<UberTrip> trips = new UberTripRepo()
			.fetchUberTrips(new GetUberTripsRequest().addGeofence(geoFence))
			.uberTrips;
		JsonArray jsonArray = new JsonArray();
		for (UberTrip trip : trips) {
			jsonArray.add(trip.toJson());
		}
		renderJSON(jsonArray.toString());
	}
}
