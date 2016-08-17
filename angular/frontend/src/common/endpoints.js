angular.module('common.endpoints', [])

  .constant("bankEndpoints", {
    "BALANCE": "r/account/balance",
    "DEPOSIT": "r/account/deposit",
    "WITHDRAW": "r/account/withdraw",
    "LOGIN": "r/account/login",
    "CURRENT_USER": "r/account/name",
    "COUNT_USERS": "r/accounts/count"
  });
