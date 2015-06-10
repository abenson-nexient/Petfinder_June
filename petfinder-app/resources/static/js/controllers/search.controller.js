'use strict';

angular.module('PetFinder').controller('SearchController', function($scope, $http, StoreService, ServerUrl, DisplayService, SearchService) {

	$scope.ageOptions = ['Baby', 'Young', 'Adult', 'Senior'];
	$scope.sexOptions = ['m', 'f'];

	$http.get(ServerUrl + '/pet/search?location=MI').success(function(response){	
		$scope.displayPets = DisplayService.beautifyPetName(response);
	});

	$http.get(ServerUrl + '/meta/animals').success(function(response) {
		$scope.animalTypes = response;
	});

	$scope.searchPets = function(pet) {
		var query = SearchService.genSearchQuery(pet);
		$http.get(ServerUrl + '/pet/search?' + query).success(function(response) {		
			$scope.displayPets = DisplayService.beautifyPetName(response);
		}).error(function(response) {
			console.log(response);
		});
	};

	$scope.getRandomPet = function() {
		$http.get(ServerUrl + '/pet/random').success(function(response){		
			StoreService.storeVal(response[0]);
		});
	};
});