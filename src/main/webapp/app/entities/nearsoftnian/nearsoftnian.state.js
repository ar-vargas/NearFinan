(function() {
    'use strict';

    angular
        .module('nearDocsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('nearsoftnian', {
            parent: 'entity',
            url: '/nearsoftnian',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Nearsoftnians'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/nearsoftnian/nearsoftnians.html',
                    controller: 'NearsoftnianController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('nearsoftnian-detail', {
            parent: 'entity',
            url: '/nearsoftnian/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Nearsoftnian'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/nearsoftnian/nearsoftnian-detail.html',
                    controller: 'NearsoftnianDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Nearsoftnian', function($stateParams, Nearsoftnian) {
                    return Nearsoftnian.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('nearsoftnian.new', {
            parent: 'nearsoftnian',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nearsoftnian/nearsoftnian-dialog.html',
                    controller: 'NearsoftnianDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                email: null,
                                name: null,
                                name2: null,
                                lastName: null,
                                lastName2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('nearsoftnian', null, { reload: true });
                }, function() {
                    $state.go('nearsoftnian');
                });
            }]
        })
        .state('nearsoftnian.edit', {
            parent: 'nearsoftnian',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nearsoftnian/nearsoftnian-dialog.html',
                    controller: 'NearsoftnianDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Nearsoftnian', function(Nearsoftnian) {
                            return Nearsoftnian.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('nearsoftnian', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('nearsoftnian.delete', {
            parent: 'nearsoftnian',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nearsoftnian/nearsoftnian-delete-dialog.html',
                    controller: 'NearsoftnianDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Nearsoftnian', function(Nearsoftnian) {
                            return Nearsoftnian.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('nearsoftnian', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
