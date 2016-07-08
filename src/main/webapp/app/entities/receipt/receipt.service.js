(function() {
    'use strict';
    angular
        .module('nearDocsApp')
        .factory('Receipt', Receipt);

    Receipt.$inject = ['$resource'];

    function Receipt ($resource) {
        var resourceUrl =  'api/receipts/:id';

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
