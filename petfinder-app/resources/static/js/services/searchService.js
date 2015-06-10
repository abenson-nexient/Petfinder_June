'use strict';

angular.module('PetFinder').factory('SearchService', function() {
	return {
		genSearchQuery: function(params) {
			var queryArray = [];
			for(var i in params)
				queryArray.push(i + '=' + params[i]);
			return queryArray.join('&');
		}
	}
});