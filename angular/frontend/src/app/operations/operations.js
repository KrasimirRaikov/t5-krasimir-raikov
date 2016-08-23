/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */

angular.module('bank.operations', [
  'ui.router',
  'angular-growl',
  'common.http',
  'common.endpoints'
])


  .config(function config($stateProvider) {
    $stateProvider.state('operations', {
      url: '/operations',
      views: {
        "main": {
          controller: 'OperationsCtrl',
          controllerAs: 'vm',
          templateUrl: 'operations/operations.tpl.html'
        }
      },
      data: {pageTitle: 'Operations'}
    });

  })

  .service('accountGateway', function (httpRequest, bankEndpoints) {
    return {
      getCurrentBalance: function () {
        return httpRequest.get(bankEndpoints.BALANCE);
      },
      depositFunds: function (depositAmount) {
        return httpRequest.post(bankEndpoints.DEPOSIT, {}, {amount: depositAmount});
      },
      withdrawFunds: function (withdrawAmount) {
        return httpRequest.post(bankEndpoints.WITHDRAW, {}, {amount: withdrawAmount});
      }
    };
  })


  .controller('OperationsCtrl', function OperationsController(accountGateway, growl) {

    var vm = this;

    vm.deposit = function (amount) {
      accountGateway.depositFunds(amount).then(function (depositResult) {
        growl.success(depositResult.message);
        vm.balance = depositResult.currentBalance;

      }, function (depositErrorResult) {
        growl.error('Error: We were not able to deposit the funds. ' + depositErrorResult);
      });
    };

    vm.withdraw = function (amount) {
      accountGateway.withdrawFunds(amount).then(
        function (withdrawResult) {
          growl.success(withdrawResult.message);
          vm.balance = withdrawResult.currentBalance;
        }, function (withdrawErrorResult) {
          growl.error('Error: We were not able to withdraw the funds. ' + withdrawErrorResult);
        });
    };

    vm.loadCurrentBalance = function () {
      accountGateway.getCurrentBalance().then(function (balanceResult) {
        vm.balance = balanceResult;
      });
    };
  });
