'use strict';

angular.module('PetFinder').factory('StoreService', function() {

	var storedVal = {};
	var storedArray = [];

	return {
		storeVal: function(val) {
			angular.copy(val, storedVal);
		},
		getStoredVal: storedVal,
		storeValues: function(array) {
			array.forEach(function(element) {
				storedArray.push(element);
			});
		},
		getValues: storedArray
	};
});