'use strict';

app.controller('ModalController', function(StoreService, $scope) {
	$scope.currentPet = StoreService.getStoredVal;
	$scope.myInterval = 5000;
});