angular.module('bank.login', [
  'ui.router',
  'angular-growl',
  'common.http',
  'common.endpoints',
  'ngCookies'
])


  .config(function config($stateProvider) {
    $stateProvider.state('login', {
      url: '/login',
      views: {
        "main": {
          controller: 'LoginCtrl',
          controllerAs: 'vm',
          templateUrl: 'login/login.tpl.html'
        }
      },
      data: {pageTitle: 'Login'}
    });
  })

  .service('loginGateway', function (httpRequest, bankEndpoints) {
    return {
      login: function (username, password) {
        return httpRequest.post(bankEndpoints.LOGIN, {username: username, password: password});
      }
    };
  })

  .service('currentUserProvider', function () {
    var currentUser;
    return {
      setCurrentUser: function (username) {
        currentUser = username;
      },
      getCurrentUser: function () {
        return currentUser;
      }
    };
  })

  .controller('LoginCtrl', function LoginController(loginGateway, growl, currentUserProvider, $state, $cookies) {
    var vm = this;
    vm.login = function (username, password) {
      loginGateway.login(username, password).then(function (response) {
        $cookies.put('sessionId', response);
        currentUserProvider.setCurrentUser(username);
        growl.success("you have logged in successfully");
        $state.go("home");
      }, function (error) {
        growl.error(error);
      });
    };
  });