'use strict';

app.factory('StoreService', function() {

var storedVal = {};

	return {
		storeVal: function(val) {
			angular.copy(val, storedVal);
		},
		getStoredVal: storedVal
	};
});