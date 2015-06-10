'use strict';

angular.module('PetFinder').controller('SearchController', function($scope, $http, StoreService, ServerUrl, SearchService) {

	$scope.ageOptions = ['Baby', 'Young', 'Adult', 'Senior'];
	$scope.sexOptions = ['m', 'f'];
	var globalPetSearchParams = {};
	var count = 0
	$http.get(ServerUrl + '/pet/search?location=MI').success(function(response){	
		$scope.displayPets = response;
	});

	$http.get(ServerUrl + '/meta/animals').success(function(response) {
		$scope.animalTypes = response;
	});

	$scope.searchPets = function(pet) {
		count += 50;
		pet.count = count;
		var query = SearchService.genSearchQuery(pet);
		globalPetSearchParams = pet;
		$http.get(ServerUrl + '/pet/search?' + query).success(function(response) {		
			$scope.displayPets = response;
		}).error(function(response) {
			console.log(response);
		});
	};

	$scope.getRandomPet = function() {
		$http.get(ServerUrl + '/pet/random').success(function(response){		
			StoreService.storeVal(response[0]);
		});
	};

	$scope.getBreeds = function(event) {
		if(event.target.value === '') return;
		var selectedAnimalType = $scope.animalTypes[event.target.value];
		$http.get(ServerUrl + '/meta/breeds?animal=' + selectedAnimalType).success(function(response) {
			$scope.selectedAnimalBreeds = response;
		});
	};

	window.onscroll = function(event) {
		if ((window.innerHeight + window.scrollY >= document.body.offsetHeight) && globalPetSearchParams.hasOwnProperty('location'))
			$scope.searchPets(globalPetSearchParams);
	}

});