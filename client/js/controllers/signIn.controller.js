'use strict';

app.controller('SignInController', function($scope, $http) {
	$scope.signInUser = function(user) {
		$http.post('/user/signIn', user).success(function(response) {
			console.log(response);
		});
	};
});