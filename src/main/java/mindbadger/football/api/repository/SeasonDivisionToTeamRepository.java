package mindbadger.football.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.KatharsisSeason;
import mindbadger.football.api.model.KatharsisSeasonDivision;
import mindbadger.football.api.model.KatharsisTeam;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class SeasonDivisionToTeamRepository extends RelationshipRepositoryBase<KatharsisSeasonDivision, String, KatharsisTeam, String> {

    private KatharsisSeasonRepository seasonRepository;

    @Autowired
    public SeasonDivisionToTeamRepository(KatharsisSeasonRepository seasonRepository,KatharsisTeamRepository teamRepository) {
        super (KatharsisSeasonDivision.class, KatharsisTeam.class);
        this.seasonRepository = seasonRepository;
    }

    @Override
    public KatharsisTeam findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new NotImplementedException();
    }

    @Override
    public ResourceList<KatharsisTeam> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	KatharsisSeason season = seasonRepository.findOne(parseSeasonIdFromSourceId(sourceId), querySpec);
    	
    	for (KatharsisSeasonDivision seasonDivision : season.getSeasonDivisions() ) {
    		if (sourceId.equals(seasonDivision.getId())) {
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
    protected KatharsisTeam getTarget(String targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<KatharsisTeam> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<KatharsisSeasonDivision> getSourceResourceClass() {
        return KatharsisSeasonDivision.class;
    }

    @Override
    public Class<KatharsisTeam> getTargetResourceClass() {
        return KatharsisTeam.class;
    }
}
