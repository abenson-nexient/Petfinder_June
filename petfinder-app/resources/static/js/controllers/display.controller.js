'use strict';

app.controller("DisplayController", function($scope, $http, StoreService) {

	// $http.get('/pets').success(function(response){
	// 	$scope.displayPets = response;
	// });

	$scope.getCurrentPet = function(pet) {
		$http.get('/pets/' + pet.pid).success(function(response){		
			StoreService.storeVal(response);
		});
	};

	$scope.getRandomPet = function() {
		$http.get('http://localhost:8080/pet/random').success(function(response){		
			StoreService.storeVal(response[0]);
		});
	};

});