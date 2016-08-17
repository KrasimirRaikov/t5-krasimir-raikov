/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */

angular.module('bank.history', [
  'ui.router',
  'angular-growl',
  'common.http',
  'common.endpoints'
])


  .config(function config($stateProvider) {
    $stateProvider.state('history', {
      url: '/history',
      views: {
        "main": {
          controller: 'HistoryCtrl',
          controllerAs: 'vm',
          templateUrl: 'history/history.tpl.html'
        }
      },
      data: {pageTitle: 'History'}
    });

  })

  .service('historyGateway', function (httpRequest, bankEndpoints) {
    return {
      getPage: function (pageNumber) {
        return httpRequest.get(bankEndpoints.HISTORY, {},  {page:pageNumber});
      }
    };
  })

  .controller('HistoryCtrl', function HistoryController(growl, historyGateway) {
    var vm = this;
    vm.currentPage = 1;
    vm.pageSize = 20;


    vm.initializeContent = function () {
      vm.tracks = vm.getPageContent(1);
    };

    vm.previous = function () {
      if (vm.currentPage > 1) {
        vm.currentPage = vm.currentPage - 1;
        vm.tracks = vm.getPageContent(vm.currentPage);
      } else {
        growl.error('there is no previous page');
      }
    };

    vm.next = function () {
      if (vm.tracks.length > vm.pageSize) {
        vm.currentPage = vm.currentPage + 1;
        vm.tracks = vm.getPageContent(vm.currentPage);
      } else {
        growl.error('there is no next page');
      }
    };

    vm.getPageContent = function (pageNumber) {
      historyGateway.getPage(pageNumber).then(function (response) {
        vm.tracks = response;
      }, function (errorResponse) {
        growl.error(errorResponse);
      });
    };

  });