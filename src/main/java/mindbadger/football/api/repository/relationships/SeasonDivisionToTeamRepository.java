package mindbadger.football.api.repository.relationships;

import mindbadger.football.api.model.CrnkSeasonDivisionTeam;
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
public class SeasonDivisionToTeamRepository extends RelationshipRepositoryBase<CrnkSeasonDivision, String, CrnkSeasonDivisionTeam, String> {

    private CrnkSeasonRepository seasonRepository;

    @Autowired
    public SeasonDivisionToTeamRepository(CrnkSeasonRepository seasonRepository, CrnkTeamRepository teamRepository) {
        super (CrnkSeasonDivision.class, CrnkSeasonDivisionTeam.class);
        this.seasonRepository = seasonRepository;
    }

    @Override
    public CrnkSeasonDivisionTeam findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new IllegalArgumentException();
    }

    @Override
    public ResourceList<CrnkSeasonDivisionTeam> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	CrnkSeason season = seasonRepository.findOne(SourceIdParser.parseSeasonId(sourceId), querySpec);
    	String divisionId = SourceIdParser.parseDivisionId(sourceId);

    	System.out.println("***** DEBUG: Number of season divisions = " + season.getSeasonDivisions().size());
    	
    	for (CrnkSeasonDivision seasonDivision : season.getSeasonDivisions() ) {
    		if (divisionId.equals(seasonDivision.getId())) {
    			System.out.println("***** DEBUG: Number of teams = " + seasonDivision.getTeams().size());
    			
    			return querySpec.apply(seasonDivision.getTeams());
    		}
    	}
    	return null;
    }

    @Override
    protected CrnkSeasonDivisionTeam getTarget(String targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<CrnkSeasonDivisionTeam> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<CrnkSeasonDivision> getSourceResourceClass() {
        return CrnkSeasonDivision.class;
    }

    @Override
    public Class<CrnkSeasonDivisionTeam> getTargetResourceClass() {
        return CrnkSeasonDivisionTeam.class;
    }
}
