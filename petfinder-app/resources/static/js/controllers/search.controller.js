'use strict';

angular.module('PetFinder').controller('SearchController', ['$scope', '$http', 'StoreService', 'ServerUrl', 'SearchService', function($scope, $http, StoreService, ServerUrl, SearchService) {

	$scope.ageOptions = ['Baby', 'Young', 'Adult', 'Senior'];
	$scope.sexOptions = ['Male', 'Female', 'Unknown'];

	$http.get(ServerUrl + '/pet/search?location=MI').success(function(response) {	
		$scope.displayPets = response;
	});

	$http.get(ServerUrl + '/meta/animals').success(function(response) {
		$scope.animalTypes = response;
	});

	$scope.searchPets = function(pet) {
		pet.count = 200;
		var query = SearchService.genSearchQuery(pet);
		$http.get(ServerUrl + '/pet/search?' + query).success(function(response) {		
			$scope.displayPets = response;
		}).error(function(response) {
			console.log(response);
		});
	};

	$scope.getRandomPet = function() {
		$http.get(ServerUrl + '/pet/random').success(function(response) {		
			StoreService.storeVal(response[0]);
		});
	};

	$scope.getBreeds = function(event) {
		if(event.target.value === '') return;
		var selectedAnimalType = event.target.value.split(':')[1];
		$http.get(ServerUrl + '/meta/breeds?animal=' + selectedAnimalType).success(function(response) {
			$scope.selectedAnimalBreeds = response;
		});
	};
}]);