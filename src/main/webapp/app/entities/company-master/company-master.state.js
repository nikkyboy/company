(function() {
    'use strict';

    angular
        .module('practiseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('company-master', {
            parent: 'entity',
            url: '/company-master',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'practiseApp.company_Master.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/company-master/company-masters.html',
                    controller: 'Company_MasterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('company_Master');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('company-master-detail', {
            parent: 'company-master',
            url: '/company-master/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'practiseApp.company_Master.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/company-master/company-master-detail.html',
                    controller: 'Company_MasterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('company_Master');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Company_Master', function($stateParams, Company_Master) {
                    return Company_Master.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'company-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('company-master-detail.edit', {
            parent: 'company-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-master/company-master-dialog.html',
                    controller: 'Company_MasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Company_Master', function(Company_Master) {
                            return Company_Master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('company-master.new', {
            parent: 'company-master',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-master/company-master-dialog.html',
                    controller: 'Company_MasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                company_id: null,
                                company_Name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('company-master', null, { reload: 'company-master' });
                }, function() {
                    $state.go('company-master');
                });
            }]
        })
        .state('company-master.edit', {
            parent: 'company-master',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-master/company-master-dialog.html',
                    controller: 'Company_MasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Company_Master', function(Company_Master) {
                            return Company_Master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('company-master', null, { reload: 'company-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('company-master.delete', {
            parent: 'company-master',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-master/company-master-delete-dialog.html',
                    controller: 'Company_MasterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Company_Master', function(Company_Master) {
                            return Company_Master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('company-master', null, { reload: 'company-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
