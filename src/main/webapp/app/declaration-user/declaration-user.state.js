(function() {
    'use strict';

    angular
        .module('petiteAnnonceKmerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('declaration-user-edit', {
            url: '/declaration-user-edit/{id}',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/declaration-user/declaration-user-dialog.html',
                    controller: 'DeclarationUserDialogController',
                    controllerAs: 'vm',
                    size: 'lg',
                    resolve: {
                        entity: ['Declaration', function(Declaration) {
                            return Declaration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }],
            resolve:
                {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('declaration-user');
                        return $translate.refresh();
                    }]
                }
        })
        .state('declaration-user-new', {
            parent: 'home',
            url: '/declaration-user-new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/declaration-user/declaration-user-dialog.html',
                    controller: 'DeclarationUserDialogController',
                    controllerAs: 'vm',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                description: null,
                                creationDate: null,
                                lastModifiedDate: null,
                                isPublished: false,
                                price: null,
                                publishedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }],
            resolve:
                {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('declaration-user');
                        return $translate.refresh();
                    }]
                }
        });
    }

})();
