'use strict';

angular.module('PetFinder').controller("DisplayController", function($scope, $http, StoreService, ServerUrl) {

	$http.get(ServerUrl + '/pet/search?location=MI').success(function(response){
		$scope.displayPets = response;
	});

	$scope.ageOptions = ['Baby', 'Young', 'Adult', 'Senior'];
	$scope.sexOptions = ['Male', 'Female', 'Unknown'];
 
	$http.get(ServerUrl + '/meta/animals').success(function(response) {
		$scope.animalTypes = response;
	});

	$scope.getCurrentPet = function(pet) {
		$http.get(ServerUrl + '/pet/' + pet.id).success(function(response){		
			StoreService.storeVal(response[0]);
		});
	};

	$scope.getRandomPet = function() {
		$http.get(ServerUrl + '/pet/random').success(function(response){		
			StoreService.storeVal(response[0]);
		});
	};

	$scope.searchPets = function(pet) {
		var queryArray = [], query;
		for(var i in pet)
			queryArray.push(i + '=' + pet[i]);
		query = queryArray.join('&');
		$http.get(ServerUrl + '/pet/search?' + query).success(function(response) {
			$scope.displayPets = response;
		}).error(function(response) {
			console.log(response);
		});
	};

});