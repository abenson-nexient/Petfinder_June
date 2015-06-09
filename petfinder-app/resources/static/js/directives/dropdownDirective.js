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

			scope.n = 0;

 			scope.dropDown = function(event) {
 				$(event.delegateTarget).children().eq(1).stop(true, true).slideDown(250);
 			};

 			scope.backUp = function(event) {
 				$(event.delegateTarget).children().eq(1).stop(true, true).slideUp(250);
 			};

 			scope.updateTitle = function(event) {
 				scope.title = scope.n === 0 ? event.target.value : 'Multiple';
 				scope.n += 1;
 			};
		}
	}
});