'use strict';

app.controller('NavController', function($scope, StoreService, $rootScope) {
	$scope.currentUser = StoreService.getStoredVal;
});