package mindbadger.football.api.repository.relationships;

import mindbadger.football.api.repository.CrnkSeasonRepository;
import mindbadger.football.api.repository.impl.CrnkSeasonRepositoryImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkSeason;
import mindbadger.football.api.model.CrnkSeasonDivision;

@Component
public class SeasonToSeasonDivisionRepository extends RelationshipRepositoryBase<CrnkSeason, Integer, CrnkSeasonDivision, String> {

    Logger LOG = Logger.getLogger (SeasonToSeasonDivisionRepository.class);

    private CrnkSeasonRepository seasonRepository;

    @Autowired
    public SeasonToSeasonDivisionRepository(CrnkSeasonRepository seasonRepository) {
        super (CrnkSeason.class, CrnkSeasonDivision.class);
        LOG.debug("*********************** SeasonToSeasonDivisionRepository.create");
        this.seasonRepository = seasonRepository;
    }

    @Override
    public CrnkSeasonDivision findOneTarget(Integer sourceId, String fieldName, QuerySpec querySpec) {
        LOG.debug("*********************** SeasonToSeasonDivisionRepository.findOneTarget");
        throw new RuntimeException();
    }

    @Override
    public ResourceList<CrnkSeasonDivision> findManyTargets(Integer sourceId, String fieldName, QuerySpec querySpec) {
        LOG.debug("*********************** SeasonToSeasonDivisionRepository.findManyTargets");
        CrnkSeason season = seasonRepository.findOne(sourceId, querySpec);
        Iterable<CrnkSeasonDivision> seasonDivisions = season.getSeasonDivisions();
        return querySpec.apply(seasonDivisions);
    }

    @Override
    protected CrnkSeasonDivision getTarget(String targetId) {
        LOG.debug("*********************** SeasonToSeasonDivisionRepository.getTarget");
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<CrnkSeasonDivision> getTargets(Iterable<String> targetIds) {
        LOG.debug("*********************** SeasonToSeasonDivisionRepository.getTargets");
        return super.getTargets(targetIds);
    }

    @Override
    public Class<CrnkSeason> getSourceResourceClass() {
        LOG.debug("*********************** SeasonToSeasonDivisionRepository.getSourceResourceClass");
        return CrnkSeason.class;
    }

    @Override
    public Class<CrnkSeasonDivision> getTargetResourceClass() {
        LOG.debug("*********************** SeasonToSeasonDivisionRepository.getTargetResourceClass");
        return CrnkSeasonDivision.class;
    }
}
