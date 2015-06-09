'use strict';

angular.module('PetFinder').directive('dropdown', function() {
	return {
		restrict: 'AE',
		templateUrl: '../templates/dropdown.html',
		scope: {
			title: '@',
			data: '='
		},
		link: function(scope, element) {
 			scope.dropDown = function(event) {
 				$(event.delegateTarget).children().eq(1).slideDown(250);
 			};

 			scope.backUp = function(event) {
 				$(event.delegateTarget).children().eq(1).slideUp(250);
 			};
		}
	}
});