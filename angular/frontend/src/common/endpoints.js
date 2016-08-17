angular.module('common.endpoints', [])

  .constant("bankEndpoints", {
    "BALANCE": "r/account/balance",
    "DEPOSIT": "r/account/deposit"
  });
