'use strict';

describe('Petfinder App', function() {
	

	describe('SearchController', function(){
		module('PetFinder');
		var ctrl;

	  inject(function($controller) {
	  	var $scope = {};
			ctrl = $controller('SearchController', {$scope: $scope});
		});
			
			var pet = {location: "MI"};
			$scope.searchPets(pet);
			expect($scope.displayPets.length).toBeGreaterThan(0);

	});
});
