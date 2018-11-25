package mindbadger.football.api.repository.relationships;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkSeasonDivisionFixtureDate;
import mindbadger.football.api.model.CrnkTeamStatistics;
import mindbadger.football.api.repository.CrnkTeamStatisticsRepository;
import mindbadger.football.api.util.DateFormat;
import mindbadger.football.api.util.SourceIdUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class FixtureDateToTeamStatisticsRepository extends RelationshipRepositoryBase<CrnkSeasonDivisionFixtureDate, String, CrnkTeamStatistics, String> {
    private static Logger LOG = Logger.getLogger(FixtureDateToTeamStatisticsRepository.class);

    @Autowired
    private CrnkTeamStatisticsRepository teamStatisticsRepository;

    @Autowired
    public FixtureDateToTeamStatisticsRepository(CrnkTeamStatisticsRepository teamStatisticsRepository) {
        super(CrnkSeasonDivisionFixtureDate.class, CrnkTeamStatistics.class);
        this.teamStatisticsRepository = teamStatisticsRepository;
    }

    @Override
    public CrnkTeamStatistics findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new IllegalArgumentException();
    }

    @Override
    public ResourceList<CrnkTeamStatistics> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
        LOG.info("findManyTargets() to get a list of CrnkTeamStatistics");
        LOG.info("sourceId = " + sourceId);
        LOG.info("fieldName = " + fieldName);

        String seasonDivisionId = SourceIdUtils.parseSeasonDivisionId(sourceId);
        String fixtureDateParam = SourceIdUtils.parseFixtureDate(sourceId);

        Calendar cal = DateFormat.toCalendar(fixtureDateParam);

        return teamStatisticsRepository.findStatisticsBySeasonDivisionAndDate (seasonDivisionId, cal, querySpec);
    }

    @Override
    public Class<CrnkSeasonDivisionFixtureDate> getSourceResourceClass() {
        return CrnkSeasonDivisionFixtureDate.class;
    }

    @Override
    public Class<CrnkTeamStatistics> getTargetResourceClass() {
        return CrnkTeamStatistics.class;
    }
}
