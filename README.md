# Football Results REST API

## To build
```
mvn clean spring-boot:run
```

## To access the swagger documentation
```
http://localhost:8090/swagger-ui.html
```

## Test using Postman
e.g.
```
GET http://localhost:8090/division?id=1
```

Should return:
```
{
  "id": "1",
  "divisionName": "Championship"
}
``` 