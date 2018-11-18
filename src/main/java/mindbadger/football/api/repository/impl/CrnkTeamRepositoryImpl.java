package mindbadger.football.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import io.crnk.core.exception.BadRequestException;
import io.crnk.core.exception.ResourceNotFoundException;
import mindbadger.football.api.repository.CrnkTeamRepository;
import mindbadger.football.domain.Division;
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

	@Override
	public CrnkTeam findOne(String id, QuerySpec querySpec) {
		Team team = teamRepository.findOne(id);
		if (team == null) {
			throw new ResourceNotFoundException("Team does not exist");
		}
		return new CrnkTeam(team);
	}

	@Override
	public ResourceList<CrnkTeam> findAll(Iterable<String> ids, QuerySpec querySpec) {
		return super.findAll(ids, querySpec);
	}

	@Override
	public <S extends CrnkTeam> S save(S resource) {
		Team team = teamRepository.save(resource.getTeam());
		return (S) new CrnkTeam(team);
	}

	@Override
	public <S extends CrnkTeam> S create(S resource) {
		Team team = teamRepository.findMatching(resource.getTeam());
		if (team != null) {
			throw new BadRequestException("Team already exists");
		}
		return save(resource);
	}

	@Override
	public void delete(String id) {
		Team team = teamRepository.findOne(id);
		if (team == null) {
			throw new ResourceNotFoundException("Team does not exist");
		}
		teamRepository.delete(team);
	}
}
