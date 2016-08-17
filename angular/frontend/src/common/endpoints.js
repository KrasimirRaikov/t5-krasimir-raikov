angular.module('common.endpoints', [])

  .constant("bankEndpoints", {
    "BALANCE": "r/account/balance",
    "DEPOSIT": "r/account/deposit",
    "WITHDRAW": "r/account/withdraw",
    "HISTORY": "r/account/history"
  });
