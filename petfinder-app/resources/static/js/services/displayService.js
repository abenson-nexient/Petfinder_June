'use strict';

angular.module('PetFinder').factory('DisplayService', function() {

	var regexp = new RegExp(/\(([^)]+)\)/);

	return {
		beautifyPetName: function(array) {
			array.forEach(function(animal) {
				if (regexp.test(animal.name))
					animal.name = animal.name.replace(regexp, '');
			});
			return array;
		}
	}
});