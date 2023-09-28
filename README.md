WEX backend role test application

## Dependencies:
    docker, docker-compose

## How to install: 
    run 
    'docker-compose up' 
    at the root of the project.

## Endpoints:
```   
    POST http://localhost:8080/transactions
    example of body:
    {
    "description" : "test transaction",
    "transactionDate" : "2023-09-23",
    "purchaseAmount" : "1.25"
    } 
```

``` 
    GET localhost:8080/transactions
    return all transactions
```

``` 
   GET http://localhost:8080/{id}/{currency}
   example: localhost:8080/transactions/6515d55b84b2456b1cdc1f53/Afghani
   return a transaction with the amount 
   converted by the currency given  
   ps: currency is case sensitive
```
