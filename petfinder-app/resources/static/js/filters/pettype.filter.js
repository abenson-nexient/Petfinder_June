'use strict';

angular.module('PetFinder').filter('fixType', function() {
	return function(data) {
		var fixed = [];
		data = data.replace(/_/g, ' ');
		data.split(' ').forEach(function(element) {
			fixed.push(element.charAt(0).toUpperCase() + element.substring(1, element.length));
		});

		return fixed.join(' ');
	}
});