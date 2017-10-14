package mindbadger.football.api.model;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import mindbadger.football.domain.Division;
import mindbadger.football.domain.Fixture;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.Team;

@JsonApiResource(type = "fixtures")
public class CrnkFixture {
	private Fixture fixture;
	
	public CrnkFixture (Fixture fixture) {
		this.fixture = fixture;
	}

	@JsonApiId
	public String getId() {
		return fixture.getFixtureId();
	}
	public void setId (String id) {
		this.fixture.setFixtureId(id);
	}
	
	@JsonProperty("season")	
	public Season getSeason() {
		return fixture.getSeason();
	}
	public void setSeason(Season season) {
		fixture.setSeason(season);
	}

	@JsonProperty("division")
	public Division getDivision() {
		return fixture.getDivision();
	}
	public void setDivision(Division division) {
		fixture.setDivision(division);
	}

	@JsonProperty("fixtureDate")
	public Calendar getFixtureDate() {
		return fixture.getFixtureDate();
	}
	public void setFixtureDate(Calendar fixtureDate) {
		fixture.setFixtureDate(fixtureDate);
	}

	@JsonProperty("homeTeam")
	public Team getHomeTeam() {
		return fixture.getHomeTeam();
	}
	public void setHomeTeam(Team homeTeam) {
		fixture.setHomeTeam(homeTeam);
	}

	@JsonProperty("awayTeam")
	public Team getAwayTeam() {
		return fixture.getAwayTeam();
	}
	public void setAwayTeam(Team awayTeam) {
		fixture.setAwayTeam(awayTeam);
	}

	@JsonProperty("homeGoals")
	public Integer getHomeGoals() {
		return fixture.getHomeGoals();
	}
	public void setHomeGoals(Integer homeGoals) {
		fixture.setHomeGoals(homeGoals);
	}

	@JsonProperty("awayGoals")
	public Integer getAwayGoals() {
		return fixture.getAwayGoals();
	}
	public void setAwayGoals(Integer awayGoals) {
		fixture.setAwayGoals(awayGoals);
	}

	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!!
	@JsonIgnore
	private Fixture getFixture() {
		return fixture;
	}
	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!!
	@JsonIgnore
	private void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}
}
