package mindbadger.football.api.repository.relationships;

import mindbadger.football.api.model.*;
import mindbadger.football.api.repository.CrnkSeasonRepository;
import mindbadger.football.api.repository.CrnkTeamRepository;
import mindbadger.football.api.repository.utils.SeasonUtils;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.domain.SeasonDivisionTeam;
import mindbadger.football.repository.SeasonRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeasonDivisionToTeamRepository extends RelationshipRepositoryBase<CrnkSeasonDivision, String, CrnkSeasonDivisionTeam, String> {

    private static Logger LOG = Logger.getLogger(SeasonDivisionToTeamRepository.class);

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private SeasonUtils seasonUtils;

    @Autowired
    public SeasonDivisionToTeamRepository(CrnkSeasonRepository seasonRepository, CrnkTeamRepository teamRepository) {
        super (CrnkSeasonDivision.class, CrnkSeasonDivisionTeam.class);
    }

    @Override
    public CrnkSeasonDivisionTeam findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new IllegalArgumentException();
    }

    @Override
    public ResourceList<CrnkSeasonDivisionTeam> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
        LOG.info("findOne SeasonDivisionToFixtureDateRepository : id = " + sourceId);

        List<CrnkSeasonDivisionTeam> crnkSeasonDivisionTeams = new ArrayList<>();

        SeasonDivision seasonDivision = seasonUtils.getSeasonDivisionFromCrnkId(sourceId);

        for (SeasonDivisionTeam seasonDivisionTeam : seasonDivision.getSeasonDivisionTeams()) {
            crnkSeasonDivisionTeams.add (
                new CrnkSeasonDivisionTeam(seasonDivisionTeam));
        }

        return querySpec.apply(crnkSeasonDivisionTeams);
    }

//    @Override
//    protected CrnkSeasonDivisionTeam getTarget(String targetId) {
//        return super.getTarget(targetId);
//    }

    @Override
    protected Iterable<CrnkSeasonDivisionTeam> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<CrnkSeasonDivision> getSourceResourceClass() {
        return CrnkSeasonDivision.class;
    }

    @Override
    public Class<CrnkSeasonDivisionTeam> getTargetResourceClass() {
        return CrnkSeasonDivisionTeam.class;
    }
}
