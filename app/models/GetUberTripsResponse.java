package models;

import java.util.ArrayList;
import java.util.List;
/**
 * A data structure represents a response of getUberTrips request
 * @author temuge
 *
 */
public class GetUberTripsResponse {
	public List<UberTrip> uberTrips;
	public List<PopularPosition> popularPositions;
	
	public GetUberTripsResponse() {
		this.uberTrips = new ArrayList<>();
		this.popularPositions = new ArrayList<>();
	}
	
	public void addAllTrips(List<UberTrip> uberTrips) {
		this.uberTrips.addAll(uberTrips);
	}
	
	public void addAllPopularPositions(List<PopularPosition> pps) {
		this.popularPositions.addAll(pps);
	}
}
