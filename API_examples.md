
1. Get all seasons in descending order

  http://localhost:1980/api/seasons?sort=-id

  1. Result

{
  "data": [
      {
          "id": "2017",
          "type": "seasons",
          "attributes": {
              "seasonNumber": 2017
          },
          "relationships": {
              "divisions": {
                  "links": {
                      "self": "http://localhost:1980/api/seasons/2017/relationships/divisions",
                      "related": "http://localhost:1980/api/seasons/2017/divisions"
                  },
                  "data": [
                      {
                        "type: "divisions",
                        "id": "1",
                        "meta": {
                          "position": 1
                        }
                      },
                      {
                        "type: "divisions",
                        "id": "3",
                        "meta": {
                          "position": 2
                        }
                      }
                  ]
              }
          },
          "links": {
              "self": "http://localhost:1980/api/seasons/2017"
          }
      },
      ...repeat...
  ],
  "meta": {
      "totalResourceCount": null
  }
}

1. Get a specific season

  http://localhost:1980/api/seasons/2017

  1. Result
  As above, but just one

1. Get a list of all divisions

  http://localhost:1980/api/divisions

  1. Result

  {
    "data": [
        {
            "id": "3",
            "type": "divisions",
            "attributes": {
                "divisionName": "English Premier"
            },
            "links": {
                "self": "http://localhost:1980/api/divisions/3"
            }
        },
        ...repeat...
    ],
    "meta": {
        "totalResourceCount": null
    }
  }

1. Get a list of all teams

  http://localhost:1980/api/divisions

1. Get divisions for a season

  http://localhost:1980/api/seasons/2017/divisions

  1. Result

  {
    "data": [
        {
            "id": "2017-3",
            "type": "divisionsInSeason",
            "attributes": {
                "position": 1
            },
            "links": {
                "self": "http://localhost:1980/api/divisionsInSeason/2017-3"
            },
            "relationships": {
                "divisions": {
                    "links": {
                        "self": "http://localhost:1980/api/divisionsInSeason/2017-3/relationships/divisions",
                        "related": "http://localhost:1980/api/divisionsInSeason/2017-3/divisions"
                    },
                },                
                "teams": {
                    "links": {
                        "self": "http://localhost:1980/api/divisionsInSeason/2017-3/relationships/teams",
                        "related": "http://localhost:1980/api/divisionsInSeason/2017-3/teams"
                    },
                }
            }
        },
        ...repeat...
    ],
    "meta": {
        "totalResourceCount": null
    }
  }


1. Get teams for a division in a season

http://localhost:1980/api/seasons/2017/divisions/2/teams
