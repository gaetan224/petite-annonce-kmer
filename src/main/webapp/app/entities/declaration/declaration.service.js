(function() {
    'use strict';
    angular
        .module('petiteAnnonceKmerApp')
        .factory('Declaration', Declaration);

    Declaration.$inject = ['$resource', 'DateUtils'];

    function Declaration ($resource, DateUtils) {
        var resourceUrl =  'api/declarations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                        data.publishedDate = DateUtils.convertDateTimeFromServer(data.publishedDate);
                    }
                    return data;
                }
            },
            'getAllDeclarationsByRegion': {
                url:'api/declarations-byregion',
                method: 'GET',
                isArray: true
            },
            'update': { method:'PUT' }
        });
    }
})();
