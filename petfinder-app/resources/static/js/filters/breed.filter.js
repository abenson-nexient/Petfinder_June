'use strict';

angular.module('PetFinder').filter('beautifyBreeds', function() {
	return function(breeds) {
		if (breeds === undefined) return '';
		return breeds.join(', ');
	};
});