package mindbadger.football.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkSeason;
import mindbadger.football.api.model.CrnkSeasonDivision;

@Component
public class SeasonDivisionToSeasonRepository extends RelationshipRepositoryBase<CrnkSeasonDivision, String, CrnkSeason, Integer> {

    private CrnkSeasonRepository seasonRepository;

    @Autowired
    public SeasonDivisionToSeasonRepository (CrnkSeasonRepository seasonRepository) {
    	super (CrnkSeasonDivision.class, CrnkSeason.class);
    	this.seasonRepository = seasonRepository;
    }
    
    @Override
    public CrnkSeason findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
    	return seasonRepository.findOne(parseSeasonIdFromSourceId(sourceId), querySpec);
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResourceList<CrnkSeason> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	CrnkSeason season = findOneTarget(sourceId, fieldName, querySpec);
    	return querySpec.apply((Iterable<CrnkSeason>) season);
    }
    
    private Integer parseSeasonIdFromSourceId (String sourceId) {
    	String[] idSplit = sourceId.split("-");
    	return Integer.parseInt(idSplit[0]);
    }
    
    @Override
    protected CrnkSeason getTarget(Integer targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<CrnkSeason> getTargets(Iterable<Integer> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<CrnkSeasonDivision> getSourceResourceClass() {
        return CrnkSeasonDivision.class;
    }

    @Override
    public Class<CrnkSeason> getTargetResourceClass() {
        return CrnkSeason.class;
    }
}
