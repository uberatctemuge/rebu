goog.provide('rebu.StreetView');
/**
 * @constructor
 */
rebu.StreetView = function(map, position, mapDom) {
  this.init(map, position, mapDom);
};

/**
 * initialize street view
 */
rebu.StreetView.prototype.init = function(map, position, mapDom) {
  this.map = map;
  var panorama = new google.maps.StreetViewPanorama(
    mapDom, {
	  position: position,
	});
  map.setStreetView(panorama);
}