'use strict';

app.controller("DisplayController", function($scope, $http) {
	$http.get('/pets').success(function(response){
		$scope.displayPets = response;
	});

	

	$scope.getCurrentPet = function(pet) {
		$http.get('/pets/' + pet.sid).success(function(response){		
			$scope.currentPet = response;
		});
	};

});