/*global angular */

/**
 * Services that persists and retrieves TODOs from database
 */
angular.module('todomvc')
	.factory('todoStorage', function ($http) {
		'use strict';

		return {
			get: function () {
				return $http.get('http://192.168.1.1:8080/');
			},

			put: function (todo) {
				return $http.put('http://192.168.1.1:8080/', todo).success(function(data) {
					todo._id = data;
				});
			}
		};
	});
