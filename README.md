# Football Results REST API

## To build
```
mvn clean spring-boot:run
```

## To access the swagger documentation
```
http://localhost:1980/swagger-ui.html
```

## Test using Postman
e.g.
```
GET http://localhost:1980/division?id=1
```

Should return:
```
{
  "id": "1",
  "divisionName": "Championship"
}
```
