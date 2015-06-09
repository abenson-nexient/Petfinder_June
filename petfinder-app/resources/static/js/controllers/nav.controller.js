'use strict';

angular.module('PetFinder').controller('NavController', function($scope, StoreService, $rootScope) {
	$scope.currentUser = StoreService.getStoredVal;
});