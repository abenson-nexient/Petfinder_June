'use strict';

angular.module('PetFinder').factory('SearchService', function() {
	return {
		genSearchQuery: function(params) {
			var queryArray = [];
			for(var i in params) {
				if(params[i] === null) continue;
				if(i === "location") {
					var locationParams = params[i].split(' ').join('%20');
					queryArray.push(i + '=' + locationParams);
				} else {
					queryArray.push(i + '=' + params[i]);
				}
			}
			return queryArray.join('&');
		}
	}
});