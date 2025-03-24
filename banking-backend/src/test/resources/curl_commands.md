## Create new account
```shell
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "ACC123456",
    "ownerName": "John Doyle",
    "balance": 1000.00
  }'
```

## List all accounts
```shell
curl -X GET http://localhost:8080/api/accounts \
  -H "Accept: application/json"
```

## Get Account by ID
```shell
curl -X GET http://localhost:8080/api/accounts/1 \
  -H "Accept: application/json"
```

## Get Account by Account Number
```shell
curl -X GET http://localhost:8080/api/accounts/number/ACC123456 \
  -H "Accept: application/json"
```

## Update Account
```shell
curl -X PUT http://localhost:8080/api/accounts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "ACC123456",
    "ownerName": "John Doyle II",
    "balance": 1000.00
  }'
```

## Make a deposit
```shell
curl -X POST "http://localhost:8080/api/accounts/ACC123456/deposit?amount=500.00" \
  -H "Content-Type: application/json"
```

## Make a withdrawal
```shell
curl -X POST "http://localhost:8080/api/accounts/ACC123456/withdraw?amount=200.00" \
  -H "Content-Type: application/json"
```

## Attempted withdrawal with negative amount (error)
```shell
curl -X POST "http://localhost:8080/api/accounts/ACC123456/withdraw?amount=-50.00" \
  -H "Content-Type: application/json"
```

## Attempted withdrawal with an amount greater than the balance (error)
```shell
curl -X POST "http://localhost:8080/api/accounts/ACC123456/withdraw?amount=5000.00" \
  -H "Content-Type: application/json"
```

## Attempted deposit with negative amount (error)
```shell
curl -X POST "http://localhost:8080/api/accounts/ACC123456/deposit?amount=-100.00" \
  -H "Content-Type: application/json"
```

## Attempt to access a non-existent account
```shell
curl -X GET http://localhost:8080/api/accounts/9999 \
  -H "Accept: application/json"
```

## Delete Account
```shell
curl -X DELETE http://localhost:8080/api/accounts/1
```