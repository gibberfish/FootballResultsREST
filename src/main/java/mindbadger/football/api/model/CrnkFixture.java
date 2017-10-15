package mindbadger.football.api.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
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
	public String getFixtureDate() {
		Calendar fixtureDate = fixture.getFixtureDate();
		return sdf.format(fixtureDate.getTime());
	}
	public void setFixtureDate(String fixtureDate) throws ParseException {
		Date fixtureDateDate = sdf.parse(fixtureDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fixtureDateDate);
		fixture.setFixtureDate(cal);
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
