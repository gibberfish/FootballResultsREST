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

    @Autowired
    private SeasonRepository seasonRepository;

    public SeasonToSeasonDivisionRepository() {
        super (KatharsisSeason.class, KatharsisSeasonDivision.class);
    }

    @Override
    public KatharsisSeasonDivision findOneTarget(Integer sourceId, String fieldName, QuerySpec querySpec) {
        throw new NotImplementedException();
    }

    @Override
    public ResourceList<KatharsisSeasonDivision> findManyTargets(Integer sourceId, String fieldName, QuerySpec querySpec) {
        Season season = seasonRepository.findOne(sourceId);
        Iterable<SeasonDivision> seasonDivisions = season.getSeasonDivisions();

        List<KatharsisSeasonDivision> katharsisSeasonDivisions = new ArrayList<KatharsisSeasonDivision>();

        for (SeasonDivision seasonDivision : seasonDivisions) {
            katharsisSeasonDivisions.add(new KatharsisSeasonDivision(seasonDivision));
        }

        return querySpec.apply(katharsisSeasonDivisions);
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
