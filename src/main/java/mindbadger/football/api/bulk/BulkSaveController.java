package mindbadger.football.api.bulk;

import mindbadger.football.api.util.DateFormat;
import mindbadger.football.domain.*;
import mindbadger.football.repository.FixtureRepository;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public ResponseEntity<String> saveFixtures (@RequestBody String fixturesJson) {
        logger.info("Bulk Update Fixtures");
        logger.debug(fixturesJson);
        List<Fixture> fixtureList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(fixturesJson);

        JSONArray jsonArray = jsonObject.getJSONArray("data");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject rootObject = jsonArray.getJSONObject(i);

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

            String awayTeamId = attributesObject.getString("awayTeamId");
            Team awayTeam = domainObjectFactory.createTeam();
            awayTeam.setTeamId(awayTeamId);

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

        return ResponseEntity.ok("Saved " + fixtureList + " fixtures.");
    }
}
