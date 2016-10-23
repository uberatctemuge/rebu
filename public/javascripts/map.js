goog.provide('rebu.MapDrawer');
/**
 * @constructor
 */
rebu.MapDrawer = function() {
  this.initMap();
  this.initDrawingManager();
  this.addOverlayListener();
};

/**
 * Initialize the map
 */
rebu.MapDrawer.prototype.initMap = function() {
  this.map = new google.maps.Map(document.getElementById('map'), {
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
          drawingModes: ['marker', 'circle', 'polygon', 'rectangle']
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
  
  for (var i = 0; i < markers.length; i++) {
	  self.addMarker(markers[i]);
  }
}

//Adds a marker to the map.
rebu.MapDrawer.prototype.addMarker = function(location) {
  var self = this;
  var marker = new google.maps.Marker({
    position: location,
    map: self.map
  });
}

rebu.MapDrawer.prototype.addOverlayListener = function() {
  var self = this;
  google.maps.event.addListener(self.drawingManager, 'overlaycomplete', function(event) {
    var vertices = event.overlay.getPath().getArray();
    sendOverlayCompleteUpdate(event.type, vertices, self);
  });
}

var sendOverlayCompleteUpdate = function(type, vertices, mapDrawer) {
	var jsonVertices = JSON.stringify(vertices);
	
	$.post("/overlayComplete", {type: type, jsonVertices: jsonVertices}, function(data, status){
		mapDrawer.drawMarkersFromTrip(data);
    });
}

goog.exportSymbol('rebu.MapDrawer', rebu.MapDrawer);