package mindbadger.football.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkDivision;
import mindbadger.football.api.model.CrnkSeasonDivision;

@Component
public class SeasonDivisionToDivisionRepository extends RelationshipRepositoryBase<CrnkSeasonDivision, String, CrnkDivision, String> {

    private CrnkDivisionRepository divisionRepository;

    @Autowired
    public SeasonDivisionToDivisionRepository (CrnkDivisionRepository divisionRepository) {
    	super (CrnkSeasonDivision.class, CrnkDivision.class);
    	this.divisionRepository = divisionRepository;
    }
    
    @Override
    public CrnkDivision findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
    	return divisionRepository.findOne(parseDivisionIdFromSourceId(sourceId), querySpec);
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResourceList<CrnkDivision> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	CrnkDivision division = findOneTarget(sourceId, fieldName, querySpec);
    	return querySpec.apply((Iterable<CrnkDivision>) division);
    }

    private String parseDivisionIdFromSourceId (String sourceId) {
    	String[] idSplit = sourceId.split("-");
    	return idSplit[1];
    }
    
    @Override
    protected CrnkDivision getTarget(String targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<CrnkDivision> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<CrnkSeasonDivision> getSourceResourceClass() {
        return CrnkSeasonDivision.class;
    }

    @Override
    public Class<CrnkDivision> getTargetResourceClass() {
        return CrnkDivision.class;
    }
}
