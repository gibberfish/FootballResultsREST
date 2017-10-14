package mindbadger.football.api.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private CrnkSeasonDivisionRepository seasonDivisionRepository;

    @Autowired
    public SeasonDivisionToFixtureRepository(CrnkSeasonDivisionRepository seasonDivisionRepository,CrnkFixtureRepository fixtureRepository) {
        super (CrnkSeasonDivision.class, CrnkFixture.class);
        this.seasonDivisionRepository = seasonDivisionRepository;
    }

    @Override
    public CrnkFixture findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new IllegalArgumentException();
    }

    @Override
    public ResourceList<CrnkFixture> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	
    	Set<CrnkFixture> crnkFixtures = new HashSet<CrnkFixture> ();
    	
    	CrnkSeasonDivision crnkSeasonDivision = seasonDivisionRepository.findOne(sourceId, querySpec);
    	
    	List<Fixture> fixtures = fixtureRepository.getFixturesForDivisionInSeason(crnkSeasonDivision.getSeasonDivision());
    	
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
