goog.provide('rebu.TripInfo');

goog.require('rebu.MapDrawer');
/**
 * @constructor
 */
rebu.TripInfo = function(mapDrawer) {
  this.init(mapDrawer);
};

/**
 * Initialize the TripInfo
 */
rebu.TripInfo.prototype.init = function(mapDrawer) {
  this.mapDrawer = mapDrawer;
}

/**
 * Add popular positions info to the UI
 */
rebu.TripInfo.prototype.addPositions = function(positions) {
  var self = this;
  $('#popular-positions-headers').show();
  for (var i=0; i<positions.length; i++) {
	var pos = positions[i];
	$('#popular-positions').append(
	    '<tr class="js-lat-lng-row lat-lng-row">' +
	    '<td> <img src="' + pos.street_view_url + '"></td>' + 
	    '<td class="lat-lng-element lat-popular">' + pos.lat + '</td>' + 
		'<td class="lat-lng-element lng-popular">' + pos.lng + '</td>' + 
		'<td>' + pos.count + '</td></tr>');
  }
  self.addbinderForLatLgnRow();
}

/**
 * Sets the trip count
 */
rebu.TripInfo.prototype.setTripCounts = function(count) {
  $('#trip-count-container').show();
  $('#trip-count').text(count);
  $('#announcement').hide();
}

/**
 * Ad binder for rows of popular positions
 */
rebu.TripInfo.prototype.addbinderForLatLgnRow = function() {
  var self = this;
  $('.js-lat-lng-row').hover(
  	function() {
  		var $this = $(this);
  		addMarkerFromRow(self, $this);
  	},
  	function() {
  		self.mapDrawer.deleteMarker();
  	}
  ).click(function() {
  	  $this = $(this);
  	  drawStreetView(self, $this);
  });
}

/**
 * remove popular positions from dom
 */
rebu.TripInfo.prototype.removePopularPositions = function() {
  $('.js-lat-lng-row').remove();
}

/**
 * Show spinner to indicate the progress
 */
rebu.TripInfo.prototype.showSpinner = function() {
  $('#spinner').show();
}

/**
 * Hide spinner to indicate the operation is done
 */
rebu.TripInfo.prototype.hideSpinner = function() {
  $('#spinner').hide();
}

/**
 * draw marker that is associated with particular row
 */
var addMarkerFromRow = function(tripInfo, $row) {
	var lat = $row.find('.lat-popular').text(),
	  lng = $row.find('.lng-popular').text(),
	  location = {};
	location["lat"] = Number(lat);
	location["lng"] = Number(lng);
	tripInfo.mapDrawer.addMarker(location);
}

/**
 * draw street view that is associated with particular row
 */
var drawStreetView = function(tripInfo, $row) {
	var lat = $row.find('.lat-popular').text(),
	  lng = $row.find('.lng-popular').text(),
	  location = {};
	location["lat"] = Number(lat);
	location["lng"] = Number(lng);
	tripInfo.mapDrawer.drawStreetView(location);
}


