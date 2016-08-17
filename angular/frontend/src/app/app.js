angular.module('bank', [
  'templates-app',
  'templates-common',
  'bank.home',
  'bank.deposit',
  'ui.router',
  'angular-growl'
])

  .config(function myAppConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/home');
  })

  .controller('AppCtrl', function AppCtrl($scope) {
    $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
      if (angular.isDefined(toState.data.pageTitle)) {
        $scope.pageTitle = toState.data.pageTitle + ' | bank';
      }
    });
  })

;

