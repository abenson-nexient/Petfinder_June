'use strict';

app.controller('NavController', function($scope, StoreService) {
	$scope.currentUser = StoreService.getStoredVal;
	$scope.isSignedIn = StoreService.getStoredVal.isSignedIn;
});