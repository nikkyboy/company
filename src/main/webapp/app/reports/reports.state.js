(function() {
    'use strict';

    angular
        .module('practiseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('reports', {
            parent: 'app',
            url: '/reports',
            data: {
                authorities: [],
                pageTitle: 'global.menu.reports'
            },
            views: {
                'content@': {
                    templateUrl: 'app/reports/reports.html',
                    controller: 'ReportsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reports');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
