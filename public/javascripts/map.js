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
      center: {lat: -34.397, lng: 150.644},
      zoom: 8
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
          drawingModes: ['marker', 'circle', 'polygon', 'polyline', 'rectangle']
        },
        markerOptions: {icon: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png'},
        circleOptions: {
          fillColor: '#ffff00',
          fillOpacity: 1,
          strokeWeight: 5,
          clickable: false,
          editable: true,
          zIndex: 1
        }
      });
    this.drawingManager.setMap(self.map);
};

rebu.MapDrawer.prototype.addOverlayListener = function() {
  var self = this;
  google.maps.event.addListener(self.drawingManager, 'overlaycomplete', function(event) {
    console.log(event.type);
  });
}

goog.exportSymbol('rebu.MapDrawer', rebu.MapDrawer);