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
 			scope.dropDownToggle = function(event) {
 				$(event.delegateTarget).children().eq(1).slideToggle(250);
 			};
		}
	}
});