'use strict';

angular.module('PetFinder').controller('ModalController', function(StoreService, $scope) {
	$scope.currentPet = StoreService.getStoredVal;
	$scope.myInterval = 5000;

	$scope.displayBreedInfo = function(breeds) {
		if (breeds === undefined) return '';
		return breeds.join(', ');
	};
});