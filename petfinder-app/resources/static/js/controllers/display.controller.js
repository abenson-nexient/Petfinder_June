'use strict';

angular.module('PetFinder').controller("DisplayController", function($scope, $http, StoreService, ServerUrl) {

	$scope.getCurrentPet = function(pet) {
		$http.get(ServerUrl + '/pet/' + pet.id).success(function(response){		
			StoreService.storeVal(response[0]);
		});
	};


});