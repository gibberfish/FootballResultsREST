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

@SuppressWarnings("deprecation")
@JsonApiResource(type = "fixtureDate")
public class CrnkFixtureDate {

	private SeasonDivision seasonDivision;
	private String fixtureDate;
	private String id;

	public CrnkFixtureDate(SeasonDivision seasonDivision, String fixtureDate) {
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
		System.out.println("CrnkFixtureDate.getFixtures() called.");
		return fixtures;
	}
}
