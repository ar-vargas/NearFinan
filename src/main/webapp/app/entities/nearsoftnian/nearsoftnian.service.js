(function() {
    'use strict';
    angular
        .module('nearDocsApp')
        .factory('Nearsoftnian', Nearsoftnian);

    Nearsoftnian.$inject = ['$resource'];

    function Nearsoftnian ($resource) {
        var resourceUrl =  'api/nearsoftnians/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
