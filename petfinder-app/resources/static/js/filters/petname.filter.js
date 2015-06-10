'use strict';

angular.module('PetFinder').filter('beautify', function() {
	return function(data) {
		var regexp = new RegExp(/\W+\D+/);

		if(regexp.test(data))
			data = data.replace(regexp, '');

		return data;
	}
});