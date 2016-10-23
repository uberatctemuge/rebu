package repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.LatLng;
import models.PopularPosition;
import models.UberTrip;

public class TopTripsRetriever {
	
	public List<PopularPosition> sortByPickups(
		List<UberTrip> trips,
		Integer limit) 
	{
		List<PopularPosition> pps = computePopularPositions(
				trips.stream()
					.map(t -> new LatLng(t.pickup_lat, t.pickup_lng))
					.collect(Collectors.toList()));
		return sort(pps).subList(0, Math.min(limit, pps.size()));
	}
	
	private List<PopularPosition> sort(List<PopularPosition> positions) {
		Collections.sort(
			positions, 
			(p1, p2) -> (-1) * p1.count.compareTo(p2.count));
		
		return positions;
	}
	
	private List<PopularPosition> computePopularPositions(
		List<LatLng> latLngs)
	{
		Map<LatLng, Integer> counts = new HashMap<LatLng, Integer>();
		for (LatLng latLng : latLngs) {
			LatLng rounded = roundToNearest(latLng);
			if (!counts.containsKey(rounded)) {
				counts.put(rounded, 1);
			} else {
				counts.put(rounded, counts.get(rounded) + 1);
			}
		}
		
		List<PopularPosition> positions = new ArrayList<>();
		
		for (Map.Entry<LatLng, Integer> entry : counts.entrySet()) {
			positions.add(PopularPosition.from(
				entry.getKey(),
				entry.getValue()));
		}
		return positions;
	}
	
	private LatLng roundToNearest(LatLng latLng) {
		return new LatLng(round(latLng.lat), round(latLng.lng));
	}
	
	private Double round(Double d) {
		int a = (int) Math.round(d * 5000);
		return a / 5000.0;
	}
}
