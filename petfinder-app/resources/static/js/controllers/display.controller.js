'use strict';

angular.module('PetFinder').controller("DisplayController", function($scope, $http, StoreService, ServerUrl) {

	// $http.get('/pets').success(function(response){
	// 	$scope.displayPets = response;
	// });

	$scope.getCurrentPet = function(pet) {
		$http.get('/pets/' + pet.pid).success(function(response){		
			StoreService.storeVal(response);
		});
	};

	$scope.getRandomPet = function() {
		$http.get(ServerUrl + '/pet/random').success(function(response){		
			StoreService.storeVal(response[0]);
		});
	};

	$scope.dropDown = function(event) {
		$(event.delegateTarget).children().eq(1).slideToggle(250);
	};

});