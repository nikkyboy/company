(function() {
    'use strict';

    angular
        .module('practiseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('product-master', {
            parent: 'entity',
            url: '/product-master',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'practiseApp.product_Master.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-master/product-masters.html',
                    controller: 'Product_MasterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('product_Master');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('product-master-detail', {
            parent: 'product-master',
            url: '/product-master/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'practiseApp.product_Master.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-master/product-master-detail.html',
                    controller: 'Product_MasterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('product_Master');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Product_Master', function($stateParams, Product_Master) {
                    return Product_Master.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'product-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('product-master-detail.edit', {
            parent: 'product-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-master/product-master-dialog.html',
                    controller: 'Product_MasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Product_Master', function(Product_Master) {
                            return Product_Master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-master.new', {
            parent: 'product-master',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-master/product-master-dialog.html',
                    controller: 'Product_MasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                product_id: null,
                                product_Name: null,
                                company_id: null,
                                unit_Price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('product-master', null, { reload: 'product-master' });
                }, function() {
                    $state.go('product-master');
                });
            }]
        })
        .state('product-master.edit', {
            parent: 'product-master',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-master/product-master-dialog.html',
                    controller: 'Product_MasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Product_Master', function(Product_Master) {
                            return Product_Master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-master', null, { reload: 'product-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-master.delete', {
            parent: 'product-master',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-master/product-master-delete-dialog.html',
                    controller: 'Product_MasterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Product_Master', function(Product_Master) {
                            return Product_Master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-master', null, { reload: 'product-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
