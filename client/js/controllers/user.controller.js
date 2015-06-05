'use strict';

app.controller('UserController', function($scope, $http, StoreService) {

	$scope.signInUser = function(user) {
		$http.post('/user/signIn', user).success(function(response) {
			$('#user-modal').modal('hide');
		}).error(function(response) {
			console.log(response);
		});
	};
});