angular.module('bank.home', [
  'ui.router',
  'common.endpoints',
  'angular-growl'
])


  .config(function config($stateProvider) {
    $stateProvider.state('home', {
      url: '/home',
      views: {
        "main": {
          controller: 'HomeCtrl',
          controllerAs: 'vm',
          templateUrl: 'home/home.tpl.html'
        }
      },
      data: {pageTitle: 'Home'}
    });
  })

  .service('homeGateway', function (httpRequest, bankEndpoints) {
    return{
      countOnlineUsers: function () {
       return httpRequest.get(bankEndpoints.COUNT_USERS);
      }
    };
  })

  .controller('HomeCtrl', function HomeController(homeGateway, growl) {
    var vm = this;
    vm.loadNumberOfOnlineUsers = function () {
      homeGateway.countOnlineUsers().then(function (response) {
        vm.onlineUsers = response;
      }, function (errorMessage) {
        growl.error(errorMessage);
      });
    };
  })

;

