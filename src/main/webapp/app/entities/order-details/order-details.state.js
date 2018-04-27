(function() {
    'use strict';

    angular
        .module('practiseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('order-details', {
            parent: 'entity',
            url: '/order-details',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'practiseApp.order_Details.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-details/order-details.html',
                    controller: 'Order_DetailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('order_Details');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('order-details-detail', {
            parent: 'order-details',
            url: '/order-details/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'practiseApp.order_Details.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-details/order-details-detail.html',
                    controller: 'Order_DetailsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('order_Details');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Order_Details', function($stateParams, Order_Details) {
                    return Order_Details.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'order-details',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('order-details-detail.edit', {
            parent: 'order-details-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-details/order-details-dialog.html',
                    controller: 'Order_DetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Order_Details', function(Order_Details) {
                            return Order_Details.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-details.new', {
            parent: 'order-details',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-details/order-details-dialog.html',
                    controller: 'Order_DetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                order_id: null,
                                order_Date: null,
                                company_id: null,
                                product_id: null,
                                quantity: null,
                                total: null,
                                vat: null,
                                order_value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('order-details', null, { reload: 'order-details' });
                }, function() {
                    $state.go('order-details');
                });
            }]
        })
        .state('order-details.edit', {
            parent: 'order-details',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-details/order-details-dialog.html',
                    controller: 'Order_DetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Order_Details', function(Order_Details) {
                            return Order_Details.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-details', null, { reload: 'order-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-details.delete', {
            parent: 'order-details',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-details/order-details-delete-dialog.html',
                    controller: 'Order_DetailsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Order_Details', function(Order_Details) {
                            return Order_Details.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-details', null, { reload: 'order-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
