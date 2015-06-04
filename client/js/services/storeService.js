'use strict';

app.factory('StoreService', function() {

var currentPet = {};

	return {
		storePet: function(pet) {
			angular.copy(pet, currentPet);
		},
		getCurrentPet: currentPet
	};
});