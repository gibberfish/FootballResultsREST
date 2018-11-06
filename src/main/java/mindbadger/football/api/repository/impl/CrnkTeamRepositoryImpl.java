package mindbadger.football.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import mindbadger.football.api.repository.CrnkTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkTeam;
import mindbadger.football.domain.Team;
import mindbadger.football.repository.TeamRepository;

@Component
public class CrnkTeamRepositoryImpl extends ResourceRepositoryBase<CrnkTeam, String> 
	implements CrnkTeamRepository {
	
	protected CrnkTeamRepositoryImpl() {
		super(CrnkTeam.class);
	}

	@Autowired
	private TeamRepository teamRepository;

	@Override
	public synchronized ResourceList<CrnkTeam> findAll(QuerySpec querySpec) {
		Iterable<Team> teams = teamRepository.findAll();
		
    	List<CrnkTeam> katharsisTeams = new ArrayList<CrnkTeam> ();
    	for (Team team : teams) {
    		katharsisTeams.add(new CrnkTeam(team));
    	}
		
		return querySpec.apply(katharsisTeams);
	}
}
