/*global angular */

/**
 * Services that persists and retrieves TODOs from database
 */
angular.module('todomvc')
	.factory('todoStorage', function ($http) {
		'use strict';

		return {
			get: function () {
				return $http.get('http://localhost:8080/');
			},

			put: function (todo) {
				return $http.put('http://localhost:8080/', todo);
			}
		};
	});
