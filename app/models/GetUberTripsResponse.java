package models;

import java.util.ArrayList;
import java.util.List;

public class GetUberTripsResponse {
	public List<UberTrip> uberTrips;
	
	public GetUberTripsResponse() {
		this.uberTrips = new ArrayList<>();
	}
	
	public void addAllTrips(List<UberTrip> uberTrips) {
		this.uberTrips.addAll(uberTrips);
	}
}
