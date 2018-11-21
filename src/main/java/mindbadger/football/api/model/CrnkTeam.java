package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import mindbadger.configuration.ApplicationContextProvider;
import mindbadger.football.domain.DomainObjectFactory;
import mindbadger.football.domain.Team;
import org.apache.log4j.Logger;

@JsonApiResource(type = "teams")
public class CrnkTeam {
	private static Logger LOG = Logger.getLogger(CrnkTeam.class);

	private Team team;

	private DomainObjectFactory domainObjectFactory;

	public CrnkTeam (Team team) {
		this.team = team;
	}

	public CrnkTeam () {
		LOG.debug("*********************** Create new CrnkTeam- empty constructor");

		this.domainObjectFactory = (DomainObjectFactory)
				ApplicationContextProvider.getApplicationContext().getBean("domainObjectFactory");
		team = domainObjectFactory.createTeam();
	}

	@JsonProperty("teamName")	
	public String getTeamName() {
		return team.getTeamName();
	}
	public void setTeamName(String teamName) {
		team.setTeamName(teamName);
	}
	
	@JsonApiId
	public String getId() {
		if (team == null) return null;
		return team.getTeamId();
	}
	public void setId(String id) {
		team.setTeamId(id);
	}

	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!!
	@JsonIgnore
	public Team getTeam() {	return team; }

	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!
	@JsonIgnore
	private void setTeam(Team team) {	this.team = team;	}
}
