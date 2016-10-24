goog.provide('rebu.TripInfo');

goog.require('rebu.MapDrawer');
/**
 * @constructor
 */
rebu.TripInfo = function(mapDrawer) {
  this.mapDrawer = mapDrawer;
};

rebu.TripInfo.prototype.addPositions = function(positions) {
  var self = this;
  $('#popular-positions-headers').show();
    for (var i=0; i<positions.length; i++) {
	  var pos = positions[i];
	  $('#popular-positions').append(
	    '<tr class="js-lat-lng-row lat-lng-row">' +
	    '<td class="lat-popular">' + pos.lat + '</td>' + 
		'<td class="lng-popular">' + pos.lng + '</td>' + 
		'<td>' + pos.count + '</td></tr>');
  }
  self.addbinderForLatLgnRow();
}

rebu.TripInfo.prototype.setTripCounts = function(count) {
  $('#trip-count-container').show();
  $('#trip-count').text(count);
}

rebu.TripInfo.prototype.addbinderForLatLgnRow = function() {
	var tripInfoSelf = this;
	$('.js-lat-lng-row').click(function(){
		var self = this,
		    $this = $(this),
		    lat = $this.find('.lat-popular').text(),
		    lng = $this.find('.lng-popular').text(),
		    location = {};
		location["lat"] = Number(lat);
		location["lng"] = Number(lng);
		tripInfoSelf.mapDrawer.addMarker(location);
	});
}


