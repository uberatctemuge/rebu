package repo;

import java.util.ArrayList;
import java.util.List;

import models.Geofence;
import models.GetUberTripsRequest;
import models.GetUberTripsResponse;
import models.LatLng;
import models.PopularPosition;
import models.UberTrip;
/**
 * A server that provides that related to uber trips
 * 
 * In distributed systems world,
 * it should be by RPC server
 * @author temuge
 *
 */
public class UberTripRepo {
	
	private static final int LIMIT_TOP = 10;
	
	public GetUberTripsResponse fetchUberTrips(
		GetUberTripsRequest request) 
	{
		List<UberTrip> tripsInBox = new UberTripsLoader()
			.byGeofences(request.getGeoFences())
			.load();
		List<UberTrip> trips = new ArrayList<>();
		for (UberTrip ut : tripsInBox) {
			for (Geofence gf : request.getGeoFences()) {
				boolean isEligible = false;
				if (request.shouldContainWholeTrip()) {
					isEligible = gf.contains(new LatLng(ut.pickup_lat, ut.pickup_lng)) &&
						gf.contains(new LatLng(ut.dropoff_lat, ut.dropoff_lng));
				} else {
					if (request.shouldContainPickups()) {
						isEligible =  isEligible || gf.contains(new LatLng(ut.pickup_lat, ut.pickup_lng));
					}
					if (request.shouldContainDropoffs()) {
						isEligible = isEligible ||  gf.contains(new LatLng(ut.dropoff_lat, ut.dropoff_lng));
					}
				}
				if (isEligible) {
					trips.add(ut);
					break;
				}
			}
		}
		GetUberTripsResponse res = new GetUberTripsResponse();
		res.addAllTrips(trips);
		List<PopularPosition> pps = new TopTripsRetriever()
			.sortByPickups(trips, LIMIT_TOP);
		res.addAllPopularPositions(pps);
		return res;
	}
}
