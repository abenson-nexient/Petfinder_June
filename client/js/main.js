'use strict';

var app = angular.module('PetFinder', ['ngMockE2E']);

app.controller("DisplayController", function($scope, $http){
	$scope.update = function(){
		$http.get('/pets').success(function(data){
			$scope.displayPets = data;
		});
	};
});

// define our fake backend
app.run(function($httpBackend) {
  var pets = [
  	{
	  	sid: 24,
	  	pid: 45,
	  	name: 'Sophie',
	  	sex: 'female',
	  	animal: 'dog',
	  	size: 'small',
	  	breed: 'Yorkshire Terrior',
	  	mix: 'None',
	  	description: 'Sophie is a fun loving ball of cuteness',
	  	age: 'adult',
	  	status: 'available',
	  	contact: '123-456-7890',
	  	images: ['/img/placeholderImage.svg']
  	},
  	{
	  	sid: 62,
	  	pid: 28,
	  	name: 'Chester',
	  	sex: 'male',
	  	animal: 'cat',
	  	size: 'large',
	  	breed: 'Tabby',
	  	mix: 'None',
	  	description: 'Chester is crazy',
	  	age: 'adult',
	  	status: 'available',
	  	contact: '123-456-7890',
	  	images: ['/img/placeholderImage.svg']
  	},
  	{
		sid: 34,
		id: 78,
		name: 'Lion',
		animal: 'cat',
		age: "senior",
		sex: 'male',
		status: 'available',
		contact: '7684679809',
		size: 20,
		breed: 'moutain',
		mix: 'none',
		description: 'very loyal, but very dangerous too. Feeds with human',
		images: ['/img/placeholderImage.svg']
	}
  ]; 
  
  $httpBackend.whenPOST('/pets').respond(function(method, url, data, headers){
    console.log('Received these data:', method, url, data, headers);
    phones.push(angular.fromJson(data));
    return [20, {}, {}];
  });
  
  $httpBackend.whenGET('/pets').respond(function(method,url,data) {
    console.log("Getting pets");
    return [20, pets, {}];
  });
});