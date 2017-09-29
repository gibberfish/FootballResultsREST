package mindbadger.football.api.repository;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.KatharsisDivision;
import mindbadger.football.api.model.KatharsisSeason;
import mindbadger.football.api.model.KatharsisSeasonDivision;
import mindbadger.football.api.model.KatharsisTeam;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeasonDivisionToSeasonRepository extends RelationshipRepositoryBase<KatharsisSeasonDivision, String, KatharsisSeason, Integer> {

    @Autowired
    private SeasonRepository seasonRepository;

    public SeasonDivisionToSeasonRepository() {
        super (KatharsisSeasonDivision.class, KatharsisSeason.class);
    }

    @Override
    public KatharsisSeason findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new NotImplementedException();
    }

//    @Override
//    public ResourceList<KatharsisTeam> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
//        //Season season = seasonRepository.findOne(sourceId);
//        //Iterable<Team> teams = season.getSeasonDivisions();
//
//        List<KatharsisSeasonDivision> katharsisSeasonDivisions = new ArrayList<KatharsisSeasonDivision>();
//
//        for (SeasonDivision seasonDivision : seasonDivisions) {
//            katharsisSeasonDivisions.add(new KatharsisSeasonDivision(seasonDivision));
//        }
//
//        return querySpec.apply(katharsisSeasonDivisions);
//    }

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
