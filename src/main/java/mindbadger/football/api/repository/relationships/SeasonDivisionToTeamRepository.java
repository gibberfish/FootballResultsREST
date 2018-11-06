package mindbadger.football.api.repository.relationships;

import mindbadger.football.api.repository.CrnkSeasonRepository;
import mindbadger.football.api.repository.CrnkTeamRepository;
import mindbadger.football.api.util.SourceIdParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkSeason;
import mindbadger.football.api.model.CrnkSeasonDivision;
import mindbadger.football.api.model.CrnkTeam;

@Component
public class SeasonDivisionToTeamRepository extends RelationshipRepositoryBase<CrnkSeasonDivision, String, CrnkTeam, String> {

    private CrnkSeasonRepository seasonRepository;

    @Autowired
    public SeasonDivisionToTeamRepository(CrnkSeasonRepository seasonRepository, CrnkTeamRepository teamRepository) {
        super (CrnkSeasonDivision.class, CrnkTeam.class);
        this.seasonRepository = seasonRepository;
    }

    @Override
    public CrnkTeam findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new IllegalArgumentException();
    }

    @Override
    public ResourceList<CrnkTeam> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	CrnkSeason season = seasonRepository.findOne(SourceIdParser.parseSeasonId(sourceId), querySpec);
    	
    	System.out.println("***** DEBUG: Number of season divisions = " + season.getSeasonDivisions().size());
    	
    	for (CrnkSeasonDivision seasonDivision : season.getSeasonDivisions() ) {
    		if (sourceId.equals(seasonDivision.getId())) {
    			System.out.println("***** DEBUG: Number of teams = " + seasonDivision.getTeams().size());
    			
    			return querySpec.apply(seasonDivision.getTeams());
    		}
    	}
    	return null;
    }

    @Override
    protected CrnkTeam getTarget(String targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<CrnkTeam> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<CrnkSeasonDivision> getSourceResourceClass() {
        return CrnkSeasonDivision.class;
    }

    @Override
    public Class<CrnkTeam> getTargetResourceClass() {
        return CrnkTeam.class;
    }
}
