(function() {
    'use strict';

    angular
        .module('nearDocsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('receipt', {
            parent: 'entity',
            url: '/receipt',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Receipts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/receipt/receipts.html',
                    controller: 'ReceiptController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('receipt-detail', {
            parent: 'entity',
            url: '/receipt/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Receipt'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/receipt/receipt-detail.html',
                    controller: 'ReceiptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Receipt', function($stateParams, Receipt) {
                    return Receipt.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('receipt.new', {
            parent: 'receipt',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/receipt/receipt-dialog.html',
                    controller: 'ReceiptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                path: null,
                                year: null,
                                month: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('receipt', null, { reload: true });
                }, function() {
                    $state.go('receipt');
                });
            }]
        })
        .state('receipt.edit', {
            parent: 'receipt',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/receipt/receipt-dialog.html',
                    controller: 'ReceiptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Receipt', function(Receipt) {
                            return Receipt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('receipt', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('receipt.delete', {
            parent: 'receipt',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/receipt/receipt-delete-dialog.html',
                    controller: 'ReceiptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Receipt', function(Receipt) {
                            return Receipt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('receipt', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
