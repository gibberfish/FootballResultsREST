package mindbadger.football.api.repository.utils;

import mindbadger.football.api.model.CrnkSeasonDivisionFixtureDate;
import mindbadger.football.api.repository.relationships.SeasonDivisionToFixtureDateRepository;
import mindbadger.football.api.util.SourceIdParser;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.repository.SeasonRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeasonUtils {
    private static Logger LOG = Logger.getLogger(SeasonDivisionToFixtureDateRepository.class);

    @Autowired
    private SeasonRepository seasonRepository;

    public SeasonDivision getSeasonDivisionFromCrnkId (String sourceId) {
        LOG.info("SeasonUtils.getSeasonDivisionFromCrnkId : id = " + sourceId);

        Integer seasonNumber = SourceIdParser.parseSeasonId(sourceId);
        String divisionId = SourceIdParser.parseDivisionId(sourceId);

        Season existingSeason = seasonRepository.findOne(seasonNumber);

        for (SeasonDivision seasonDivision : existingSeason.getSeasonDivisions()) {
            if (divisionId.equals(seasonDivision.getDivision().getDivisionId())) {
                return seasonDivision;
            }
        }

        throw new IllegalArgumentException("No Season Division found for id");
    }
}
