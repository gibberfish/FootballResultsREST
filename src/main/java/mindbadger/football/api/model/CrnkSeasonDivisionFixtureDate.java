package mindbadger.football.api.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import io.crnk.core.resource.annotations.JsonApiToMany;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;
import org.apache.log4j.Logger;

//TODO Change the entity name to fixtureDates to be consistent with other entities (change postman!)
@SuppressWarnings("deprecation")
@JsonApiResource(type = "fixtureDate")
public class CrnkSeasonDivisionFixtureDate {
	private static Logger LOG = Logger.getLogger(CrnkSeasonDivisionFixtureDate.class);

	private SeasonDivision seasonDivision;
	private String fixtureDate;
	private String id;

	public CrnkSeasonDivisionFixtureDate(SeasonDivision seasonDivision, String fixtureDate) {
		if (seasonDivision == null || fixtureDate == null) {
			throw new IllegalArgumentException("seasonDivision and fixtureDate must not be null");
		}
		this.fixtureDate = fixtureDate;
		this.seasonDivision = seasonDivision;
		this.id = seasonDivision.getSeason().getSeasonNumber() + "-" + seasonDivision.getDivision().getDivisionId() + "_" + fixtureDate;
	}

	@JsonProperty("fixtureDate")
	public String getFixtureDate() {
		return this.fixtureDate;
	}
	public void setFixtureDate(String fixtureDate) {
		this.fixtureDate = fixtureDate;
	}
	
	@JsonApiId
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonApiToMany(opposite = "fixture")
	public Set<CrnkFixture> getFixtures () {
		Set<CrnkFixture> fixtures = new HashSet<CrnkFixture>();
		LOG.info("CrnkSeasonDivisionFixtureDate.getFixtures() called. Currently returns and EMPTY set");
		return fixtures;
	}

	@JsonApiToMany(opposite = "teamStatistics")
	public Set<CrnkTeamStatistics> getTeamStatistics () {
		Set<CrnkTeamStatistics> teamStatistics = new HashSet<CrnkTeamStatistics>();
		LOG.info("CrnkSeasonDivisionFixtureDate.getTeamStatistics() called. Currently returns and EMPTY set");
		return teamStatistics;
	}

	@JsonIgnore
	public SeasonDivision getSeasonDivision(){
		return this.seasonDivision;
	}
}
