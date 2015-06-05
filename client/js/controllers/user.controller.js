'use strict';

app.controller('UserController', function($scope, $http, UserService, StoreService, $rootScope) {

	$scope.signInUser = function(user) {
		UserService.signInUser(user).then(function(response) {
			$rootScope.isSignedIn = true;
			StoreService.storeVal(response);
			$('#user-modal').modal('hide');
		});
	};
});