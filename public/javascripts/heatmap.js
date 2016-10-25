goog.provide('rebu.HeatMap');

goog.require('rebu.MapDrawer');
/**
 * @constructor
 */
rebu.HeatMap = function(map, points) {
  this.initHeatMap(map, points);
};

/**
 * Initialize the heatmap
 */
rebu.HeatMap.prototype.initHeatMap = function(map, points) {
  googleLatLngs = [];
  allpoints = points;
  for (var i=0; i<points.length; i++) {
	var point = points[i];
	if (point === undefined) {continue;}
	googleLatLngs.push(new google.maps.LatLng(point["lat"], point["lng"]));
  }
  self.heatmap = new google.maps.visualization.HeatmapLayer({
    data: googleLatLngs,
    map: map
  });
  
  heatmap.setMap(map);
}

/**
 * Remove heatmap from the map
 */
rebu.HeatMap.prototype.removeHeatmap = function() {
  self.heatmap.setMap(null);
}
