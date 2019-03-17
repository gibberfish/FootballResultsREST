package mindbadger.football.api.bulk;

import com.google.gson.*;
import mindbadger.football.api.model.CrnkFixture;
import mindbadger.football.api.util.DateFormat;
import mindbadger.football.domain.*;
import mindbadger.football.repository.FixtureRepository;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("dataapi/bulksave")
public class BulkSaveController {
    Logger logger = Logger.getLogger(BulkSaveController.class);

    @Autowired
    private DomainObjectFactory domainObjectFactory;

    @Autowired
    private FixtureRepository fixtureRepository;

    @PutMapping("/fixtures")
//    public void saveFixtures (@RequestBody List<CrnkFixture> crnkFixtures) {

    public void saveFixtures (@RequestBody String fixturesJson) {
        logger.info("Bulk Update Fixtures");
        logger.debug(fixturesJson);
        List<Fixture> fixtureList = new ArrayList<>();


        JSONObject jsonObject = new JSONObject(fixturesJson);


//        Gson gson = new Gson();
//
//
//        JsonElement jelement = gson.toJsonTree(fixturesJson);
//        logger.debug(jelement);

//        logger.debug("Is JSON Array = " + jelement.isJsonArray());
//        logger.debug("Is JSON Null = " + jelement.isJsonNull());
//        logger.debug("Is JSON Object = " + jelement.isJsonObject());
//        logger.debug("Is JSON Primitive" + jelement.isJsonPrimitive());
//
//        JsonPrimitive jsonPrimitive = jelement.getAsJsonPrimitive();
//        jsonPrimitive.

//        JsonObject jsonObject = jelement.getAsJsonObject();

        JSONArray jsonArray = jsonObject.getJSONArray("data");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject rootObject = jsonArray.getJSONObject(i);

//            JsonObject rootObject = element.getAsJsonObject();
            JSONObject attributesObject = rootObject.getJSONObject("attributes");

            Integer seasonNumber = attributesObject.getInt("seasonNumber");
            Season season = domainObjectFactory.createSeason();
            season.setSeasonNumber(seasonNumber);

            String divisionId = attributesObject.getString("divisionId");
            Division division = domainObjectFactory.createDivision();
            division.setDivisionId(divisionId);

            SeasonDivision seasonDivision = domainObjectFactory.createSeasonDivision();
            seasonDivision.setSeason(season);
            seasonDivision.setDivision(division);

            String homeTeamId = attributesObject.getString("homeTeamId");
            Team homeTeam = domainObjectFactory.createTeam();
            homeTeam.setTeamId(homeTeamId);
//            SeasonDivisionTeam homeSeasonDivisionTeam  = domainObjectFactory.createSeasonDivisionTeam();
//            homeSeasonDivisionTeam.setSeasonDivision(seasonDivision);
//            homeSeasonDivisionTeam.setTeam(homeTeam);

            String awayTeamId = attributesObject.getString("homeTeamId");
            Team awayTeam = domainObjectFactory.createTeam();
            awayTeam.setTeamId(awayTeamId);
//            SeasonDivisionTeam awaySeasonDivisionTeam  = domainObjectFactory.createSeasonDivisionTeam();
//            awaySeasonDivisionTeam.setSeasonDivision(seasonDivision);
//            awaySeasonDivisionTeam.setTeam(awayTeam);

            Fixture fixture = domainObjectFactory.createFixture();
            fixture.setSeasonDivision(seasonDivision);
            fixture.setHomeTeam(homeTeam);
            fixture.setAwayTeam(awayTeam);

            if (rootObject.has("id")) {
                String fixtureId = rootObject.getString("id");
                fixture.setFixtureId(fixtureId);
            }
            if (attributesObject.has("fixtureDate")) {
                String fixtureDate = attributesObject.getString("fixtureDate");
                fixture.setFixtureDate(DateFormat.toCalendar(fixtureDate));
            }
            if (attributesObject.has("homeGoals")) {
                Integer homeGoals = attributesObject.getInt("homeGoals");
                fixture.setHomeGoals(homeGoals);
            }
            if (attributesObject.has("awayGoals")) {
                Integer awayGoals = attributesObject.getInt("awayGoals");
                fixture.setAwayGoals(awayGoals);
            }

            fixtureList.add(fixture);
            logger.debug("Fixture to add: " + fixture);
        }

        fixtureRepository.saveAll(fixtureList);
    }

//    @GetMapping(value = "/xxx", produces = "application/json")
//    public ResponseEntity<String> loadAllFixturesForSeason (@RequestParam(value="season") String season) {
//        logger.info("TEST!!!");
//
//        return new ResponseEntity<>("Initialise Team Complete", HttpStatus.OK);
//    }

}
