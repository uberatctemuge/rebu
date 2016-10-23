package repo;

import java.util.ArrayList;
import java.util.List;

import models.Geofence;
import models.GetUberTripsRequest;
import models.GetUberTripsResponse;
import models.LatLng;
import models.UberTrip;

public class UberTripRepo {
	
	public GetUberTripsResponse fetchUberTrips(
		GetUberTripsRequest request) 
	{
		List<UberTrip> tripsInBox = new UberTripsLoader()
			.byGeofences(request.getGeoFences())
			.load();
		
		List<UberTrip> trips = new ArrayList<>();
		for (UberTrip ut : tripsInBox) {
			for (Geofence gf : request.getGeoFences()) {
				if (gf.contains(new LatLng(ut.pickup_lat, ut.pickup_lng)) ||
					gf.contains(new LatLng(ut.dropoff_lat, ut.dropoff_lng))) {
					trips.add(ut);
					break;
				}
			}
		}
		GetUberTripsResponse res = new GetUberTripsResponse();
		res.addAllTrips(trips);
		return res;
	}
}
