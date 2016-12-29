(function() {
    'use strict';

    angular
        .module('petiteAnnonceKmerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('region-declaration', {
            parent: 'app',
            url: '/region-declaration/:regionName/:regionId',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'petiteAnnonceKmerApp.localisation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/region-declaration/region-declaration.html',
                    controller: 'RegionDeclarationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                region: function ($stateParams) {

                    return {
                        regionId: $stateParams.regionId,
                        regionName: $stateParams.regionName
                    };
                },
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });

    }

})();
