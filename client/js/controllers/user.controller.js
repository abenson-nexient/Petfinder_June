'use strict';

app.controller('UserController', function($scope, $http, UserService, StoreService) {

	$scope.signInUser = function(user) {
		UserService.signInUser(user).then(function(response) {
			response.isSignedIn = true;
			StoreService.storeVal(response);
		});
	};
});