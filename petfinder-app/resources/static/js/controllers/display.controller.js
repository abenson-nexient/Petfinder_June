'use strict';

angular.module('PetFinder').controller("DisplayController", function($scope, $http, StoreService, ServerUrl) {

	// $http.get('/pets').success(function(response){
	// 	$scope.displayPets = response;
	// });

	$scope.age = ['Baby', 'Young', 'Adult', 'Senior'];
	$scope.sex = ['Male', 'Female', 'Unknown'];

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

});