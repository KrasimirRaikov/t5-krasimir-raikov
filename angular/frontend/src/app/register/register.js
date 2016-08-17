/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
angular.module('bank.register', [
  'ui.router',
  'angular-growl',
  'common.http',
  'common.endpoints'
])


  .config(function config($stateProvider) {
    $stateProvider.state('register', {
      url: '/register',
      views: {
        "main": {
          controller: 'RegisterCtrl',
          controllerAs: 'vm',
          templateUrl: 'register/register.tpl.html'
        }
      },
      data: {pageTitle: 'Register'}
    });
  })

  .service('registerGateway', function (httpRequest, bankEndpoints) {
    return {
      registerUser: function (username, password, confirmPassword) {
        return httpRequest.post(bankEndpoints.REGISTER, {
          username: username,
          password: password,
          confirmPassword: confirmPassword
        });
      }
    };
  })

  .controller('RegisterCtrl', function RegisterController(registerGateway, growl, $state) {
    var vm = this;

    vm.register = function (username, password, confirmPassword) {

      if (password === confirmPassword) {
        registerGateway.registerUser(username, password, confirmPassword).then(function (response) {
          growl.success(response);
          $state.go('login');
        }, function (errorMessage) {
          growl.error(errorMessage);
        });
      } else {
        growl.error('passwords do not match');
      }
    };
  })

;