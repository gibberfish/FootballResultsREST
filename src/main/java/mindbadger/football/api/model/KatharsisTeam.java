package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import mindbadger.football.domain.Team;

@JsonApiResource(type = "teams")
public class KatharsisTeam {
	private Team team;
	
	public KatharsisTeam (Team team) {
		this.team = team;
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
		return team.getTeamId();
	}
	public void setId(String id) {
		team.setTeamId(id);
	}

	//TODO Even though we don't want to expose this directly, Katharsis requires this to work!!!
	@JsonIgnore
	public Team getTeam() {	return team; }

	//TODO Even though we don't want to expose this directly, Katharsis requires this to work!!
	@JsonIgnore
	public void setTeam(Team team) {	this.team = team;	}
}
