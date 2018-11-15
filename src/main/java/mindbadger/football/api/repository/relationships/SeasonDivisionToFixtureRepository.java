package mindbadger.football.api.repository.relationships;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mindbadger.football.api.repository.CrnkFixtureRepository;
import mindbadger.football.api.repository.CrnkSeasonDivisionRepository;
import mindbadger.football.api.repository.utils.SeasonUtils;
import mindbadger.football.domain.SeasonDivision;
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
public class SeasonDivisionToFixtureRepository extends RelationshipRepositoryBase<CrnkSeasonDivision, String, CrnkFixture, String> {

	@Autowired
	private FixtureRepository fixtureRepository;
	
	@Autowired
    private SeasonUtils seasonUtils;

    @Autowired
    public SeasonDivisionToFixtureRepository(CrnkSeasonDivisionRepository seasonDivisionRepository, CrnkFixtureRepository fixtureRepository) {
        super (CrnkSeasonDivision.class, CrnkFixture.class);
    }

    @Override
    public CrnkFixture findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new IllegalArgumentException();
    }

    @Override
    public ResourceList<CrnkFixture> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	
    	Set<CrnkFixture> crnkFixtures = new HashSet<CrnkFixture> ();

        SeasonDivision seasonDivision = seasonUtils.getSeasonDivisionFromCrnkId(sourceId);

    	List<Fixture> fixtures = fixtureRepository.getFixturesForDivisionInSeason(seasonDivision);
    	
		for (Fixture fixture : fixtures) {
			crnkFixtures.add(new CrnkFixture(fixture));
		}
		
    	return querySpec.apply(crnkFixtures);
    }

    @Override
    protected CrnkFixture getTarget(String targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<CrnkFixture> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<CrnkSeasonDivision> getSourceResourceClass() {
        return CrnkSeasonDivision.class;
    }

    @Override
    public Class<CrnkFixture> getTargetResourceClass() {
        return CrnkFixture.class;
    }
}
