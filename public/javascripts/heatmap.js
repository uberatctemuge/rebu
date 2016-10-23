goog.provide('rebu.HeatMap');

goog.require('rebu.MapDrawer');
/**
 * @constructor
 */
rebu.HeatMap = function(map, points) {
  this.initHeatMap(map, points);
};

rebu.HeatMap.prototype.initHeatMap = function(map, points) {
  googleLatLngs = [];
  allpoints = points;
  for (var i=0; i<points.length; i++) {
	var point = points[i];
	if (point === undefined) {continue;}
	googleLatLngs.push(new google.maps.LatLng(point["lat"], point["lng"]));
  }
  var heatmap = new google.maps.visualization.HeatmapLayer({
    data: googleLatLngs,
    map: map
  });
  
  heatmap.setMap(map);
}

rebu.HeatMap.prototype.toggleHeatmap = function(map, heatmap) {
  heatmap.setMap(heatmap.getMap() ? null : map);
}