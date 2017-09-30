package mindbadger.football.api.repository;

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
    public SeasonDivisionToTeamRepository(CrnkSeasonRepository seasonRepository,CrnkTeamRepository teamRepository) {
        super (CrnkSeasonDivision.class, CrnkTeam.class);
        this.seasonRepository = seasonRepository;
    }

    @Override
    public CrnkTeam findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new IllegalArgumentException();
    }

    @Override
    public ResourceList<CrnkTeam> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	CrnkSeason season = seasonRepository.findOne(parseSeasonIdFromSourceId(sourceId), querySpec);
    	
    	System.out.println("***** DEBUG: Number of season divisions = " + season.getSeasonDivisions().size());
    	
    	for (CrnkSeasonDivision seasonDivision : season.getSeasonDivisions() ) {
    		if (sourceId.equals(seasonDivision.getId())) {
    			System.out.println("***** DEBUG: Number of season divisions = " + seasonDivision.getTeams().size());
    			
    			return querySpec.apply(seasonDivision.getTeams());
    		}
    	}
    	return null;
    }

    private Integer parseSeasonIdFromSourceId (String sourceId) {
    	String[] idSplit = sourceId.split("-");
    	return Integer.parseInt(idSplit[0]);
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
