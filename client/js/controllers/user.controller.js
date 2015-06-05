'use strict';

app.controller('UserController', function($scope, $http) {
	$scope.signInUser = function(user) {
		$http.post('/user/signIn', user).success(function(response) {
			console.log(response);
		}).error(function(response) {
			console.log(response);
		});
	};
});