(function() {
    'use strict';
    angular
        .module('practiseApp')
        .factory('Company_Master', Company_Master);

    Company_Master.$inject = ['$resource'];

    function Company_Master ($resource) {
        var resourceUrl =  'api/company-masters/:id';

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
