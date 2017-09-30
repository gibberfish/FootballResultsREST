package mindbadger.football.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.KatharsisDivision;
import mindbadger.football.api.model.KatharsisSeasonDivision;

@Component
public class SeasonDivisionToDivisionRepository extends RelationshipRepositoryBase<KatharsisSeasonDivision, String, KatharsisDivision, String> {

    private KatharsisDivisionRepository divisionRepository;

    @Autowired
    public SeasonDivisionToDivisionRepository (KatharsisDivisionRepository divisionRepository) {
    	super (KatharsisSeasonDivision.class, KatharsisDivision.class);
    	this.divisionRepository = divisionRepository;
    }
    
    @Override
    public KatharsisDivision findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
    	return divisionRepository.findOne(parseDivisionIdFromSourceId(sourceId), querySpec);
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResourceList<KatharsisDivision> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	KatharsisDivision division = findOneTarget(sourceId, fieldName, querySpec);
    	return querySpec.apply((Iterable<KatharsisDivision>) division);
    }

    private String parseDivisionIdFromSourceId (String sourceId) {
    	String[] idSplit = sourceId.split("-");
    	return idSplit[1];
    }
    
    @Override
    protected KatharsisDivision getTarget(String targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<KatharsisDivision> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<KatharsisSeasonDivision> getSourceResourceClass() {
        return KatharsisSeasonDivision.class;
    }

    @Override
    public Class<KatharsisDivision> getTargetResourceClass() {
        return KatharsisDivision.class;
    }
}
