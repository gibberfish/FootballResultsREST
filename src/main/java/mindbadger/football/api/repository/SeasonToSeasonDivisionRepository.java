package mindbadger.football.api.repository;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.KatharsisDivision;
import mindbadger.football.api.model.KatharsisSeason;
import mindbadger.football.api.model.KatharsisSeasonDivision;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeasonToSeasonDivisionRepository extends RelationshipRepositoryBase<KatharsisSeason, Integer, KatharsisSeasonDivision, String> {

    private KatharsisSeasonRepository seasonRepository;

    @Autowired
    public SeasonToSeasonDivisionRepository(KatharsisSeasonRepository seasonRepository) {
        super (KatharsisSeason.class, KatharsisSeasonDivision.class);
        this.seasonRepository = seasonRepository;
    }

    @Override
    public KatharsisSeasonDivision findOneTarget(Integer sourceId, String fieldName, QuerySpec querySpec) {
        throw new NotImplementedException();
    }

    @Override
    public ResourceList<KatharsisSeasonDivision> findManyTargets(Integer sourceId, String fieldName, QuerySpec querySpec) {
        KatharsisSeason season = seasonRepository.findOne(sourceId, querySpec);
        Iterable<KatharsisSeasonDivision> seasonDivisions = season.getSeasonDivisions();
        return querySpec.apply(seasonDivisions);
    }

    @Override
    protected KatharsisSeasonDivision getTarget(String targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<KatharsisSeasonDivision> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<KatharsisSeason> getSourceResourceClass() {
        return KatharsisSeason.class;
    }

    @Override
    public Class<KatharsisSeasonDivision> getTargetResourceClass() {
        return KatharsisSeasonDivision.class;
    }
}
