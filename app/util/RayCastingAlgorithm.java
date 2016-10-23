package util;

import models.LatLng;
import models.PolygonGeofence;

public class RayCastingAlgorithm {
	
	public static boolean contains(
			PolygonGeofence border, 
			LatLng latLng) {
		boolean contains = false;
		for (int i=0; i< border.vertices.size(); i++) {
			LatLng edge1 = border.vertices.get(i);
			LatLng edge2 = border.vertices.get((i+1)%border.vertices.size());
			if (rayIntersect(edge1, edge2, latLng)) {
				contains = !contains;
			}
		}
		
		return contains;
	}
	
	private static boolean rayIntersect(
			LatLng edgeA,
			LatLng edgeB,
			LatLng point) {
		LatLng edgeLower, edgeUpper;
		if (edgeA.lng > edgeB.lng) {
			edgeLower = edgeB;
			edgeUpper = edgeA;
		} else {
			edgeLower = edgeA;
			edgeUpper = edgeB;
		}
		
		if (point.lng > edgeUpper.lng || 
			point.lng < edgeLower.lng ||
			point.lat > Math.max(edgeUpper.lat, edgeLower.lat))
		{
			return false;
		}
		
		if (point.lat < Math.min(edgeUpper.lat, edgeLower.lat)) {
			return true;
		}
		
		if (Math.abs(edgeUpper.lat - edgeLower.lat) == 0) return true;
		
		if (Math.abs(point.lat - edgeLower.lat) == 0) return false;
		
		Double ratio1 = (edgeUpper.lng - edgeLower.lng) / (edgeUpper.lat - edgeLower.lat);
		Double ratio2 = (point.lng - edgeLower.lng) / (point.lat - edgeLower.lat);
		
		return ratio2 > ratio1;
	}
}
