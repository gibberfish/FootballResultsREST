# Football Results REST API

## To build
```
mvn clean spring-boot:run
```

## JSON-API Resources

All requests should use ContenType = application/vnd.api+json

### Seasons
```
http://localhost:1980/api/seasons/<season id>
```
Other Hyperlinks available from the response:
```
http://localhost:1972/api/seasons/2003/seasonDivisions
```

### Divisions
```
http://localhost:1980/api/divisions/<division id>
```

### Teams
```
http://localhost:1980/api/teams/<team id>
```

### Season Divisions
```
http://localhost:1980/api/seasons/<seasonid>/seasonDivisions
```

Other Hyperlinks available from the response:
```
http://localhost:1972/api/seasonDivisions/<seasondivision>/division
http://localhost:1972/api/seasonDivisions/<seasondivision>/season
http://localhost:1972/api/seasonDivisions/2012-1/teams
http://localhost:1972/api/seasonDivisions/2012-1/fixtures
http://localhost:1972/api/seasonDivisions/2012-1/fixtureDates
```

### Season Division Teams
```
http://localhost:1972/api/seasonDivisions/2012-1/teams
```

### Season Division Fixture Dates
```
http://localhost:1972/api/seasonDivisions/2003-1/fixtureDates
```
Other Hyperlinks available from the response:
```
http://localhost:1972/api/fixtureDate/2003-1_2004-02-21/fixtures
```
(this one doesn't currently work)

### Season Division Fixtures
```
http://localhost:1972/api/seasonDivisions/2003-1/fixtures
```

