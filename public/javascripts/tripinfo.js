goog.provide('rebu.TripInfo');

goog.require('rebu.MapDrawer');
/**
 * @constructor
 */
rebu.TripInfo = function() {
  
};

rebu.TripInfo.prototype.addPositions = function(positions) {
	for (var i=0; i<positions.length; i++) {
		console.log(positions[i]);
		var pos = positions[i];
		$('#popularPositions').append('<li>' + pos.lat + ' ' + pos.lng + '  ' + pos.count + '</li>');
	}
}


