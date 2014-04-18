/*global angular */

/**
 * Services that persists and retrieves TODOs from database
 */
angular.module('todomvc')
	.factory('todoStorage', function ($http) {
		'use strict';

		return {
			get: function () {
				return $http.get('http://mepc.io:8080/');
			},

			put: function (todo) {
				console.log(todo);
				return $http.put('http://mepc.io:8080/', todo).success(function(data) {
					todo._id = data;
				});
			}
		};
	});
