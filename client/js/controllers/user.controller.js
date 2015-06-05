'use strict';

app.controller('UserController', function($scope, $http, StoreService) {

	$scope.signInUser = function(user) {
		$http.post('/user/signIn', user).success(function(response) {
			$('#user-modal').modal('hide');
			$('.navbar-right button').eq(0).hide();
			$('.navbar-right button').eq(1).show();
			$('.navbar-right button').eq(1).text('Hi, ' + response.username);
		}).error(function(response) {
			console.log(response);
		});
	};
});