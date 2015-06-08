'use strict';

app.controller('NavController', function($scope, StoreService, $rootScope) {
	$scope.currentUser = StoreService.getStoredVal;
	$rootScope.$watch('isSignedIn', function(oldVal, newVal) {
		console.log(oldVal, newVal);
	});
});