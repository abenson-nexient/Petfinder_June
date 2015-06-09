'use strict';

angular.module('PetFinder').factory('UserService', function($http, $q) {
	return {
		signInUser: function(user) {
			return $q(function(resolve, reject) {
				$http.post('/user/signIn', user).success(function(response) {
					resolve(response);
				}).error(function(response) {
					reject(response);
				});
			});
		}
	}
});