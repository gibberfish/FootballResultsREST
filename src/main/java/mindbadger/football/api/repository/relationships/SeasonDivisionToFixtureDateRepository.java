package mindbadger.football.api.repository.relationships;

import java.util.*;

import mindbadger.football.api.model.CrnkSeasonDivisionFixtureDate;
import mindbadger.football.api.repository.CrnkSeasonDivisionRepository;
import mindbadger.football.api.repository.impl.CrnkSeasonDivisionFixtureDateRepositoryImpl;
import mindbadger.football.api.repository.utils.SeasonUtils;
import mindbadger.football.api.util.DateFormat;
import mindbadger.football.api.util.SourceIdParser;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.repository.SeasonRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkFixture;
import mindbadger.football.api.model.CrnkSeasonDivision;
import mindbadger.football.domain.Fixture;
import mindbadger.football.repository.FixtureRepository;

@Component
public class SeasonDivisionToFixtureDateRepository extends RelationshipRepositoryBase<CrnkSeasonDivision, String, CrnkSeasonDivisionFixtureDate, String> {

    private static Logger LOG = Logger.getLogger(SeasonDivisionToFixtureDateRepository.class);

	@Autowired
	private FixtureRepository fixtureRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private SeasonUtils seasonUtils;

//	@Autowired
//	private CrnkSeasonDivisionRepository seasonDivisionRepository;

    @Autowired
    public SeasonDivisionToFixtureDateRepository() {
        super (CrnkSeasonDivision.class, CrnkSeasonDivisionFixtureDate.class);
    }

    @Override
    public CrnkSeasonDivisionFixtureDate findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new IllegalArgumentException();
    }

    @Override
    public ResourceList<CrnkSeasonDivisionFixtureDate> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
        LOG.info("findOne SeasonDivisionToFixtureDateRepository : id = " + sourceId);

        List<CrnkSeasonDivisionFixtureDate> crnkSeasonDivisionFixtureDates = new ArrayList<>();

        SeasonDivision seasonDivision = seasonUtils.getSeasonDivisionFromCrnkId(sourceId);

        List<Calendar> fixtureDates = fixtureRepository.getFixtureDatesForDivisionInSeason(seasonDivision);

        for (Calendar fixtureDate : fixtureDates) {
            crnkSeasonDivisionFixtureDates.add(
                new CrnkSeasonDivisionFixtureDate(seasonDivision, DateFormat.toString(fixtureDate)));
        }

    	return querySpec.apply(crnkSeasonDivisionFixtureDates);
    }

    @Override
    protected CrnkSeasonDivisionFixtureDate getTarget(String targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<CrnkSeasonDivisionFixtureDate> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<CrnkSeasonDivision> getSourceResourceClass() {
        return CrnkSeasonDivision.class;
    }

    @Override
    public Class<CrnkSeasonDivisionFixtureDate> getTargetResourceClass() {
        return CrnkSeasonDivisionFixtureDate.class;
    }
}
