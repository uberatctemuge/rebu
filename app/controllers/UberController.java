package controllers;

import java.util.List;

import javax.persistence.Query;

import models.GetUberTripsRequest;
import models.GetUberTripsResponse;
import models.PolygonGeofence;
import models.PopularPosition;
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
		GetUberTripsResponse res = new UberTripRepo().fetchUberTrips(
				new GetUberTripsRequest().addGeofence(geoFence));
		List<UberTrip> trips = res.uberTrips;
		List<PopularPosition> popularPositions = res.popularPositions;
		JsonArray tripsJson = new JsonArray();
		for (UberTrip trip : trips) {
			tripsJson.add(trip.toJson());
		}
		JsonArray popularPositionsJson = new JsonArray();
		for (PopularPosition pp : popularPositions) {
			popularPositionsJson.add(pp.toJson());
		}
		JsonObject response = new JsonObject();
		response.add("trips", tripsJson);
		response.add("popularPositions", popularPositionsJson);
		renderJSON(response.toString());
	}
}
