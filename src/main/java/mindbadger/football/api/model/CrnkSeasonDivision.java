package mindbadger.football.api.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import io.crnk.core.resource.annotations.JsonApiToMany;
import io.crnk.core.resource.annotations.JsonApiToOne;
import mindbadger.football.domain.SeasonDivision;
import org.apache.log4j.Logger;

@SuppressWarnings("deprecation")
@JsonApiResource(type = "seasonDivisions")
public class CrnkSeasonDivision {
	private static Logger LOG = Logger.getLogger(CrnkSeasonDivisionFixtureDate.class);

	private SeasonDivision seasonDivision;
	private String id;

	public CrnkSeasonDivision(SeasonDivision seasonDivision) {
		this.seasonDivision = seasonDivision;
		this.id = seasonDivision.getSeason().getSeasonNumber() + "-" + seasonDivision.getDivision().getDivisionId();
	}

	@JsonApiToOne(opposite = "seasonDivisions")
	public CrnkSeason getSeason() {
		return new CrnkSeason(seasonDivision.getSeason());
	}
	public void setSeason(CrnkSeason season) {
		this.seasonDivision.setSeason(season.getSeason());
	}

	@JsonApiToOne(opposite = "seasonDivisions")
	public CrnkDivision getDivision() { return new CrnkDivision(seasonDivision.getDivision()); }
	public void setDivision (CrnkDivision division) {
		this.seasonDivision.setDivision(division.getDivision());
	}

	@JsonProperty("position")
	public int getPosition () {
		return seasonDivision.getDivisionPosition();
	}
	public void setPosition (int position) {
		seasonDivision.setDivisionPosition(position);
	}

	@JsonApiToMany(opposite = "team")
	public Set<CrnkTeam> getTeams () {
		Set<CrnkTeam> teams = new HashSet<CrnkTeam> ();

		seasonDivision.getSeasonDivisionTeams().stream().forEach(seasonDivisionTeam -> {
			teams.add(new CrnkTeam(seasonDivisionTeam.getTeam()));
		});

		return teams;
	}

	@JsonApiToMany(opposite = "fixture")
	public Set<CrnkFixture> getFixtures () {
		Set<CrnkFixture> fixtures = new HashSet<CrnkFixture>();
		return fixtures;
	}

	@JsonApiToMany(opposite = "fixtureDate")
	public Set<CrnkSeasonDivisionFixtureDate> getFixtureDates () {
		LOG.info("getFixtureDates() - currently returns EMPTY set");
		Set<CrnkSeasonDivisionFixtureDate> fixtureDates = new HashSet<CrnkSeasonDivisionFixtureDate>();
		return fixtureDates;
	}

	@JsonApiId
	public String getId() {
		return this.id;
	}
	public void setId() {
		throw new IllegalAccessError("Please set only as part of constructor");
	}
	
	@JsonIgnore
	public SeasonDivision getSeasonDivision() {
		return seasonDivision;
	}

	@JsonIgnore
	private void setSeasonDivision(SeasonDivision seasonDivision) {
		this.seasonDivision = seasonDivision;
	}
}
