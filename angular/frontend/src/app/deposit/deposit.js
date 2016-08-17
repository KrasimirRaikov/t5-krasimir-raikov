/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */

angular.module('bank.deposit', [
  'ui.router',
  'angular-growl',
  'common.http',
  'common.endpoints'
])


  .config(function config($stateProvider) {
    $stateProvider.state('deposit', {
      url: '/deposit',
      views: {
        "main": {
          controller: 'DepositCtrl',
          controllerAs: 'vm',
          templateUrl: 'deposit/deposit.tpl.html'
        }
      },
      data: {pageTitle: 'Deposit'}
    });

  })

  .service('accountGateway', function (httpRequest, bankEndpoints) {
    return {
      getCurrentBalance: function () {
        return httpRequest.get(bankEndpoints.BALANCE);
      },
      depositFunds: function (depositAmount) {
        return httpRequest.post(bankEndpoints.DEPOSIT, {}, {amount: depositAmount});
      }
    };
  })


  .controller('DepositCtrl', function DepositController(accountGateway, growl) {

    var vm = this;

    vm.deposit = function (amount) {
      accountGateway.depositFunds(amount).then(function (depositResult) {
        growl.success(depositResult.message);
        vm.balance = depositResult.currentBalance;

      }, function (depositErrorResult) {
        growl.error('Error: We were not able to deposit the funds. ' + depositErrorResult);
      });
    };

    vm.loadCurrentBalance = function () {
      accountGateway.getCurrentBalance().then(function (balanceResult) {
        vm.balance = balanceResult;
      });
    };
  });
