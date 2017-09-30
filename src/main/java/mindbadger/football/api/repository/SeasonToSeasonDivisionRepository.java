package mindbadger.football.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkSeason;
import mindbadger.football.api.model.CrnkSeasonDivision;

@Component
public class SeasonToSeasonDivisionRepository extends RelationshipRepositoryBase<CrnkSeason, Integer, CrnkSeasonDivision, String> {

    private CrnkSeasonRepository seasonRepository;

    @Autowired
    public SeasonToSeasonDivisionRepository(CrnkSeasonRepository seasonRepository) {
        super (CrnkSeason.class, CrnkSeasonDivision.class);
        this.seasonRepository = seasonRepository;
    }

    @Override
    public CrnkSeasonDivision findOneTarget(Integer sourceId, String fieldName, QuerySpec querySpec) {
        throw new RuntimeException();
    }

    @Override
    public ResourceList<CrnkSeasonDivision> findManyTargets(Integer sourceId, String fieldName, QuerySpec querySpec) {
        CrnkSeason season = seasonRepository.findOne(sourceId, querySpec);
        Iterable<CrnkSeasonDivision> seasonDivisions = season.getSeasonDivisions();
        return querySpec.apply(seasonDivisions);
    }

    @Override
    protected CrnkSeasonDivision getTarget(String targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<CrnkSeasonDivision> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<CrnkSeason> getSourceResourceClass() {
        return CrnkSeason.class;
    }

    @Override
    public Class<CrnkSeasonDivision> getTargetResourceClass() {
        return CrnkSeasonDivision.class;
    }
}
