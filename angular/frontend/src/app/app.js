angular.module('bank', [
  'templates-app',
  'templates-common',
  'bank.home',
  'bank.operations',
  'ui.router',
  'angular-growl',
  'bank.login',
  'ngCookies'
])

  .config(function myAppConfig($stateProvider, $urlRouterProvider, $httpProvider, growlProvider) {
    $urlRouterProvider.otherwise('/home');
    $httpProvider.defaults.withCredentials = true;
    growlProvider.globalTimeToLive(3000);
  })

  .service('userdataGateway', function (httpRequest, bankEndpoints) {
    return {
      loadCurrentUser: function () {
        return httpRequest.get(bankEndpoints.CURRENT_USER);
      }
    };
  })

  .service('logoutGateway', function (httpRequest, bankEndpoints) {
    return {
      logout: function () {
        return httpRequest.get(bankEndpoints.LOGOUT);
      }
    };
  })

  .controller('AppCtrl', function AppCtrl($scope, userdataGateway, currentUserProvider, logoutGateway, growl, $cookies, $state) {
    var vm = this;
    vm.fetchCurrentUser = function () {
      userdataGateway.loadCurrentUser().then(function (username) {
        currentUserProvider.setCurrentUser(username);
      });
    };

    vm.getCurrentUser = function () {
      return currentUserProvider.getCurrentUser();
    };

    vm.logoutUser = function () {
      logoutGateway.logout().then(function (response) {
        $cookies.remove(response);
        currentUserProvider.setCurrentUser(undefined);
        $state.go("login");
        growl.success('successful logout');
      }, function (errorMessage) {
        growl.error(errorMessage);
      });
    };

    $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
      if (angular.isDefined(toState.data.pageTitle)) {
        $scope.pageTitle = toState.data.pageTitle + ' | bank';
      }
    });
  })

;

