WEX backend role test application

## Dependencies:
    docker, docker-compose

## How to install: 
    run docker-compose up at the root of the project.

## Access though localhost:8080

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
   GET http://localhost:8080/transactions?id=12343&currency=afghani  
   ps: currency is case sensitive
   ```