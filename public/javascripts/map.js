goog.provide('rebu.MapDrawer');
/**
 * @constructor
 */
rebu.MapDrawer = function() {
  this.initMap();
  this.initDrawingManager();
  this.addOverlayListener();
  this.tripInfo = new rebu.TripInfo(this);
};

/**
 * Initialize the map
 */
rebu.MapDrawer.prototype.initMap = function() {
  this.map = new google.maps.Map($("#map")[0], {
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
          drawingModes: ['marker', 'polygon']
        },
        markerOptions: {icon: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png'},
        circleOptions: {
          fillColor: '#D3D3D3',
          fillOpacity: 100,
          strokeWeight: 5,
          clickable: false,
          editable: true,
          zIndex: 1
        }
      });
    this.drawingManager.setMap(self.map);
};

/**
 * draw markers for uber trips
 */
rebu.MapDrawer.prototype.drawMarkersFromTrip = function(ubertrips) {
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
  
  //for (var i = 0; i < markers.length; i++) {
  //	  self.addMarker(markers[i]);
  //}
  new rebu.HeatMap(self.map, markers);
}

//Adds a marker to the map.
rebu.MapDrawer.prototype.addMarker = function(location) {
  var self = this,
      marker = new google.maps.Marker({
        position: location,
        map: self.map
      });
}

rebu.MapDrawer.prototype.addOverlayListener = function() {
  var self = this;
  google.maps.event.addListener(self.drawingManager, 'overlaycomplete', function(event) {
    var vertices = event.overlay.getPath().getArray();
    self.sendOverlayCompleteUpdate(event.type, vertices, self);
  });
}

rebu.MapDrawer.prototype.sendOverlayCompleteUpdate = function(type, vertices, mapDrawer) {
	var self = this,
	    jsonVertices = JSON.stringify(vertices);
	
	$.post("/overlayComplete", {type: type, jsonVertices: jsonVertices}, function(data, status){
		mapDrawer.drawMarkersFromTrip(data.trips);
		self.tripInfo.addPositions(data.popularPositions);
		self.tripInfo.setTripCounts(data.trips.length);
    });
}

goog.exportSymbol('rebu.MapDrawer', rebu.MapDrawer);