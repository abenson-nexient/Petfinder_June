'use strict';

angular.module('PetFinder').filter('beautify', function() {
	return function(data) {
		var regex = new RegExp(/(\W+|\d+)\w+/g), 
				nums = new RegExp(/\d+/g), 
				dot = new RegExp(/\./g), 
				noWord = new RegExp(/[^\w\s]/g);

		data = dot.test(data) ? data.replace(nums, '') : data.replace(regex, '');

		if(noWord.test(data))
			data = data.replace(noWord, '');

		return data;
	}
});