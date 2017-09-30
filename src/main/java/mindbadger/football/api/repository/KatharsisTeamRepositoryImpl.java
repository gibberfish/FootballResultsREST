package mindbadger.football.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.KatharsisTeam;
import mindbadger.football.domain.Team;
import mindbadger.football.repository.TeamRepository;

@Component
public class KatharsisTeamRepositoryImpl extends ResourceRepositoryBase<KatharsisTeam, String> 
	implements KatharsisTeamRepository {
	
	protected KatharsisTeamRepositoryImpl() {
		super(KatharsisTeam.class);
	}

	@Autowired
	private TeamRepository teamRepository;

	@Override
	public synchronized ResourceList<KatharsisTeam> findAll(QuerySpec querySpec) {
		Iterable<Team> teams = teamRepository.findAll();
		
    	List<KatharsisTeam> katharsisTeams = new ArrayList<KatharsisTeam> ();
    	for (Team team : teams) {
    		katharsisTeams.add(new KatharsisTeam(team));
    	}
		
		
		return querySpec.apply(katharsisTeams);
	}
}
