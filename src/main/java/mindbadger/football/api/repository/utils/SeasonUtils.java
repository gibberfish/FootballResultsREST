package mindbadger.football.api.repository.utils;

import io.crnk.core.exception.ResourceNotFoundException;
import mindbadger.football.api.repository.relationships.SeasonDivisionToFixtureDateRepository;
import mindbadger.football.api.util.SourceIdUtils;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.repository.SeasonRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeasonUtils {
    private static Logger LOG = Logger.getLogger(SeasonDivisionToFixtureDateRepository.class);

    @Autowired
    private SeasonRepository seasonRepository;

    public SeasonDivision getSeasonDivisionFromCrnkId (String sourceId) {
        LOG.info("SeasonUtils.getSeasonDivisionFromCrnkId : id = " + sourceId);

        Integer seasonNumber = SourceIdUtils.parseSeasonId(sourceId);
        String divisionId = SourceIdUtils.parseDivisionId(sourceId);

        Season existingSeason = seasonRepository.findOne(seasonNumber);

        for (SeasonDivision seasonDivision : existingSeason.getSeasonDivisions()) {
            if (divisionId.equals(seasonDivision.getDivision().getDivisionId())) {
                return seasonDivision;
            }
        }

        throw new ResourceNotFoundException("No Season Division found for id");
    }
}
