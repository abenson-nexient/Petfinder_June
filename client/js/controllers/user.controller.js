'use strict';

app.controller('UserController', function($scope, $http) {

	$scope.signInUser = function(user) {
		$http.post('/user/signIn', user).success(function(response) {
			$scope.currentUsername = response.username;
			$('#user-modal').modal('hide');
		}).error(function(response) {
			console.log(response);
		});
	};
});