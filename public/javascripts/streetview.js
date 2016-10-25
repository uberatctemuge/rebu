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
  var self = this;
  self.map = map;
  self.panorama = new google.maps.StreetViewPanorama(
    mapDom, {
	  position: position,
	});
  self.map.setStreetView(self.panorama);
}

rebu.StreetView.prototype.hide = function() {
  if (this.panorama) {
    this.panorama.setVisible(false);
  }
}