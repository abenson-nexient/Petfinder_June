'use strict';

angular.module('PetFinder').directive('lazyImg', function($timeout) {
	return {
		restrict: 'A',
		link: function(scope, element) {
			$timeout(function() {
				$(element).lazyload({
				effect: "fadeIn"
				});
			}, 300);
		}
	}
});