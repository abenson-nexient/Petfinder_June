'use strict';

app.controller('UserController', function($scope, UserService, StoreService, $rootScope, $q) {

	$scope.isSignInInfoCorrect = true;

	$scope.signInUser = function(user) {
		UserService.signInUser(user).then(function(response) {
			$rootScope.isSignedIn = true;
			StoreService.storeVal(response);
			$('#user-modal').modal('hide');
		}, function(response) {
			$scope.isSignInInfoCorrect = false;
		});
	};
});