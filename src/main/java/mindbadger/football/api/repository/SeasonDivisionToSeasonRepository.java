package mindbadger.football.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.KatharsisSeason;
import mindbadger.football.api.model.KatharsisSeasonDivision;

@Component
public class SeasonDivisionToSeasonRepository extends RelationshipRepositoryBase<KatharsisSeasonDivision, String, KatharsisSeason, Integer> {

    private KatharsisSeasonRepository seasonRepository;

    @Autowired
    public SeasonDivisionToSeasonRepository (KatharsisSeasonRepository seasonRepository) {
    	super (KatharsisSeasonDivision.class, KatharsisSeason.class);
    	this.seasonRepository = seasonRepository;
    }
    
    @Override
    public KatharsisSeason findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
    	return seasonRepository.findOne(parseSeasonIdFromSourceId(sourceId), querySpec);
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResourceList<KatharsisSeason> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	KatharsisSeason season = findOneTarget(sourceId, fieldName, querySpec);
    	return querySpec.apply((Iterable<KatharsisSeason>) season);
    }
    
    private Integer parseSeasonIdFromSourceId (String sourceId) {
    	String[] idSplit = sourceId.split("-");
    	return Integer.parseInt(idSplit[0]);
    }
    
    @Override
    protected KatharsisSeason getTarget(Integer targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<KatharsisSeason> getTargets(Iterable<Integer> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<KatharsisSeasonDivision> getSourceResourceClass() {
        return KatharsisSeasonDivision.class;
    }

    @Override
    public Class<KatharsisSeason> getTargetResourceClass() {
        return KatharsisSeason.class;
    }
}
