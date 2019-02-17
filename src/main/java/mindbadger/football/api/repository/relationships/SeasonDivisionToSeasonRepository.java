package mindbadger.football.api.repository.relationships;

import io.crnk.core.engine.registry.RegistryEntry;
import mindbadger.football.api.repository.CrnkSeasonRepository;
import mindbadger.football.api.util.SourceIdUtils;
import mindbadger.football.domain.Season;
import mindbadger.football.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkSeason;
import mindbadger.football.api.model.CrnkSeasonDivision;

@Component
public class SeasonDivisionToSeasonRepository extends RelationshipRepositoryBase<CrnkSeasonDivision, String, CrnkSeason, Integer> {

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    public SeasonDivisionToSeasonRepository (CrnkSeasonRepository seasonRepository) {
    	super (CrnkSeasonDivision.class, CrnkSeason.class);
    }
    
    @Override
    public CrnkSeason findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        Integer id = SourceIdUtils.parseSeasonId(sourceId);
        Season season = seasonRepository.findOne(id);
        return new CrnkSeason(season);
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResourceList<CrnkSeason> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	CrnkSeason season = findOneTarget(sourceId, fieldName, querySpec);
    	return querySpec.apply((Iterable<CrnkSeason>) season);
    }

    @Override
    protected CrnkSeason getTarget(RegistryEntry entry, Integer targetId) {
        return super.getTarget(entry, targetId);
    }

    //    @Override
//    protected CrnkSeason getTarget(Integer targetId) {
//        return super.getTarget(targetId);
//    }

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
