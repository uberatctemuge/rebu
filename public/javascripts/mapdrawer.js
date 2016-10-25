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
          drawingModes: ['polygon']
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

/**
 * Adds a marker to the map.
 */
rebu.MapDrawer.prototype.addMarker = function(location) {
  var self = this;
  self.marker = new google.maps.Marker({
    position: location,
    map: self.map
  });
}

/**
 * Deletes the marker from map
 */
rebu.MapDrawer.prototype.deleteMarker = function() {
  var self = this;
  self.marker.setMap(null);
}

/**
 * Binds handler for event of shape is drawn completely
 */
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

/**
 * Sends AJAX request to retrieve ubert trip data
 */
rebu.MapDrawer.prototype.sendOverlayCompleteUpdate = function(type, vertices, mapDrawer) {
  var self = this,
	  jsonVertices = JSON.stringify(vertices);
  
  $.post("/overlayComplete", {type: type, jsonVertices: jsonVertices}, function(data, status){
	self.tripInfo.hideSpinner();
    mapDrawer.drawHeatmap(data.trips);
	self.tripInfo.addPositions(data.popularPositions);
	self.tripInfo.setTripCounts(data.trips.length);
	self.tripInfo.hideUberLogo();
  });
}

/**
 * Draw street view given coordinate
 */
rebu.MapDrawer.prototype.drawStreetView = function(position) {
  var self = this;
  this.streetView = new rebu.StreetView(self.map, position, self.dom);
}

rebu.MapDrawer.prototype.hideStreetView = function() {
	var self = this;
	if (self.streetView) {
		self.streetView.hide();
	}
}
goog.exportSymbol('rebu.MapDrawer', rebu.MapDrawer);