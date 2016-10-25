goog.provide('rebu.MapDrawer');
/**
 * @constructor
 */
rebu.MapDrawer = function() {
  this.init();
  this.initMap();
  this.initDrawingManager();
  this.addOverlayListener();
  this.tripInfo = new rebu.TripInfo(this);
};

rebu.MapDrawer.prototype.init = function() {
  this.dom = $("#map")[0];
}

/**
 * Initialize the map
 */
rebu.MapDrawer.prototype.initMap = function() {
  var self = this;
  self.map = new google.maps.Map(self.dom, {
      center: {lat: 40.7128, lng: -74.0059},
      zoom: 16
    });
};

/**
 * Initialize the map
 */
rebu.MapDrawer.prototype.initDrawingManager = function() {
	var self = this;
	self.drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: google.maps.drawing.OverlayType.MARKER,
        drawingControl: true,
        drawingControlOptions: {
          position: google.maps.ControlPosition.TOP_CENTER,
          drawingModes: ['marker', 'polygon', 'circle']
        },
        markerOptions: {icon: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png'}
      });
  this.drawingManager.setMap(self.map);
  this.shapes = [];
};

/**
 * draw markers for uber trips
 */
rebu.MapDrawer.prototype.drawHeatmap = function(ubertrips) {
  var self = this,
  	  markers = new Array(ubertrips.length * 2);
  for (var i = 0; i < ubertrips.length; i++) { 
	  var markerPickup = {},
	    markerDropoff = {};
	  markerPickup["lat"] = ubertrips[i].pickup_lat;
	  markerPickup["lng"] = ubertrips[i].pickup_lng;
	  markerDropoff["lat"] = ubertrips[i].dropoff_lat;
	  markerDropoff["lng"] = ubertrips[i].dropoff_lng;
	  markers.push(markerPickup);
	  //markers.push(markerDropoff);
  }

  self.heatMap = new rebu.HeatMap(self.map, markers);
}

//Adds a marker to the map.
rebu.MapDrawer.prototype.addMarker = function(location) {
  var self = this;
  self.marker = new google.maps.Marker({
    position: location,
    map: self.map
  });
}

rebu.MapDrawer.prototype.deleteMarker = function() {
  var self = this;
  self.marker.setMap(null);
}

rebu.MapDrawer.prototype.addOverlayListener = function() {
  var self = this;
  google.maps.event.addListener(self.drawingManager, 'overlaycomplete', function(event) {
    var vertices = event.overlay.getPath().getArray();
    if (self.shape) {
      self.shape.setMap(null);
    }
    self.shape = event.overlay;
    self.tripInfo.removePopularPositions();
    if (self.heatMap) {
      self.heatMap.removeHeatmap();
    }
    self.tripInfo.showSpinner();
    self.sendOverlayCompleteUpdate(event.type, vertices, self);
  });
}

rebu.MapDrawer.prototype.sendOverlayCompleteUpdate = function(type, vertices, mapDrawer) {
  var self = this,
	  jsonVertices = JSON.stringify(vertices);
  
  $.post("/overlayComplete", {type: type, jsonVertices: jsonVertices}, function(data, status){
	self.tripInfo.hideSpinner();
    mapDrawer.drawHeatmap(data.trips);
	self.tripInfo.addPositions(data.popularPositions);
	self.tripInfo.setTripCounts(data.trips.length);
  });
}

rebu.MapDrawer.prototype.drawStreetView = function(position) {
  var self = this;
  self.streetView = new rebu.StreetView(self.map, position, self.dom);
}

goog.exportSymbol('rebu.MapDrawer', rebu.MapDrawer);