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
import java.util.Set;

@Component
public class SeasonDivisionToTeamRepository extends RelationshipRepositoryBase<KatharsisSeasonDivision, String, KatharsisTeam, String> {

    private KatharsisSeasonRepository seasonRepository;
    private KatharsisTeamRepository teamRepository;

    @Autowired
    public SeasonDivisionToTeamRepository(KatharsisSeasonRepository seasonRepository,KatharsisTeamRepository teamRepository) {
        super (KatharsisSeasonDivision.class, KatharsisTeam.class);
        this.seasonRepository = seasonRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public KatharsisTeam findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new NotImplementedException();
    }

    @Override
    public ResourceList<KatharsisTeam> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {
    	KatharsisSeason season = seasonRepository.findOne(parseSeasonIdFromSourceId(sourceId), querySpec);
    	Set<KatharsisSeasonDivision> seasonDivisions = season.getSeasonDivisions();
    	//TODO How do we get a single season division, from which we can get the teams?
    	return null;
    }

    private Integer parseSeasonIdFromSourceId (String sourceId) {
    	String[] idSplit = sourceId.split("-");
    	return Integer.parseInt(idSplit[0]);
    }

    private String parseDivisionIdFromSourceId (String sourceId) {
    	String[] idSplit = sourceId.split("-");
    	return idSplit[1];
    }

    @Override
    protected KatharsisTeam getTarget(String targetId) {
        return super.getTarget(targetId);
    }

    @Override
    protected Iterable<KatharsisTeam> getTargets(Iterable<String> targetIds) {
        return super.getTargets(targetIds);
    }

    @Override
    public Class<KatharsisSeasonDivision> getSourceResourceClass() {
        return KatharsisSeasonDivision.class;
    }

    @Override
    public Class<KatharsisTeam> getTargetResourceClass() {
        return KatharsisTeam.class;
    }
}
