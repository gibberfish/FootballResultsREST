package mindbadger.football.api.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkFixture;
import mindbadger.football.api.model.CrnkFixtureDate;
import mindbadger.football.api.model.CrnkSeasonDivision;
import mindbadger.football.domain.Fixture;
import mindbadger.football.repository.FixtureRepository;

@Component
public class SeasonDivisionToFixtureDateRepository extends RelationshipRepositoryBase<CrnkSeasonDivision, String, CrnkFixtureDate, String> {

	@Autowired
	private FixtureRepository fixtureRepository;
	
	@Autowired
	private CrnkSeasonDivisionRepository seasonDivisionRepository;

    @Autowired
    public SeasonDivisionToFixtureDateRepository() {
        super (CrnkSeasonDivision.class, CrnkFixtureDate.class);
    }

    @Override
    public CrnkFixtureDate findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new IllegalArgumentException();
    }

    //TODO Really inefficient - needs refactoring
    @Override
    public ResourceList<CrnkFixtureDate> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	
    	
    	CrnkSeasonDivision crnkSeasonDivision = seasonDivisionRepository.findOne(sourceId, querySpec);
    	
    	List<Fixture> fixtures = fixtureRepository.getFixturesForDivisionInSeason(crnkSeasonDivision.getSeasonDivision());
    	
    	List<String> fixtureDatesWithDuplicates = new ArrayList<String> (); 
    	
		for (Fixture fixture : fixtures) {
			CrnkFixture crnkFixture = new CrnkFixture(fixture);
			fixtureDatesWithDuplicates.add(crnkFixture.getFixtureDate());
		}
		
		Set<String> uniqueFixtureDates = new HashSet<String> (fixtureDatesWithDuplicates);
		
		Set<CrnkFixtureDate> crnkFixtureDates = new HashSet<CrnkFixtureDate> ();
		
		for (String uniqueFixtureDate : uniqueFixtureDates) {
			CrnkFixtureDate fixtureDate = new CrnkFixtureDate (crnkSeasonDivision.getSeasonDivision(), uniqueFixtureDate);
			crnkFixtureDates.add(fixtureDate);
		}
		
		
    	return querySpec.apply(crnkFixtureDates);
    }

    @Override
    protected CrnkFixtureDate getTarget(String targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<CrnkFixtureDate> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<CrnkSeasonDivision> getSourceResourceClass() {
        return CrnkSeasonDivision.class;
    }

    @Override
    public Class<CrnkFixtureDate> getTargetResourceClass() {
        return CrnkFixtureDate.class;
    }
}
