# transact-service

**to build application** : 
```
./gradlew build -i
```

**to run application** :

```
docker-compose up --build
```

**authentication : two users pre-loaded**

**bobsmith - admin
joeroot - user (cannot access view endpoints.)**

to get a token, authenticate as either bobsmith or joeroot


http://localhost:8080/login
```json
{
    "username" : "bobsmith",
    "password" : "password"
}
```
Attach the token received in the Authorization header to subsequent requests.

Exposed API's documentation available via swagger : 

http://localhost:8080/swagger-ui.html

sample withdrawal request : 

```json
{
    "investorId": "joesoap@mail.com",
    "fromAccount": "RETIREMENT",
    "fromAccountType": "INVESTMENT",
    "toAccount": "CREDIT",
    "toAccountType": "EXTERNAL",
    "amount": 9500
}
```


