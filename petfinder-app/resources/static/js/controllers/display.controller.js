'use strict';

angular.module('PetFinder').controller("DisplayController", function($scope, $http, StoreService, ServerUrl) {

	$http.get(ServerUrl + '/pet/search?location=MI').success(function(response){
		$scope.displayPets = response;
	});

	$scope.getCurrentPet = function(pet) {
		$http.get(ServerUrl + '/pet/' + pet.id).success(function(response){		
			StoreService.storeVal(response[0]);
		});
	};

});