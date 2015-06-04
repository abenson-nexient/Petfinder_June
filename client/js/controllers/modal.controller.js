'use strict';

app.controller('ModalController', function(StoreService, $scope) {
	$scope.currentPet = StoreService.getCurrentPet;
});