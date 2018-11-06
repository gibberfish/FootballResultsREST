package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import io.crnk.core.resource.annotations.JsonApiToOne;
import mindbadger.football.api.util.DateFormat;
import mindbadger.football.domain.SeasonDivisionTeam;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
@JsonApiResource(type = "teamStatistics")
public class CrnkTeamStatistics {

	private SeasonDivisionTeam seasonDivisionTeam;
	private Calendar fixtureDate;
	private Map<String, Integer> statistics = new HashMap<>();
	private String id;

	public CrnkTeamStatistics(SeasonDivisionTeam seasonDivisionTeam, Calendar fixtureDate) {
		this.seasonDivisionTeam = seasonDivisionTeam;
		this.fixtureDate = fixtureDate;
		String fixtureDateString = DateFormat.toString(fixtureDate);
		this.id = seasonDivisionTeam.getSeasonDivision().getSeason().getSeasonNumber() + "-" +
				seasonDivisionTeam.getSeasonDivision().getDivision().getDivisionId() + "-" +
				seasonDivisionTeam.getTeam().getTeamId() + "-" + fixtureDateString;
	}

	@JsonProperty("statistics")
	public Map<String, Integer> getStatistics () {
		return statistics;
	}
	public void setStatistics (Map<String, Integer> statistics) {
		this.statistics = statistics;
	}

	@JsonApiToOne(opposite = "seasonDivisions")
	public CrnkSeason getSeason() {
		return new CrnkSeason(seasonDivisionTeam.getSeasonDivision().getSeason());
	}
	public void setSeason(CrnkSeason season) {
		this.seasonDivisionTeam.getSeasonDivision().setSeason(season.getSeason());
	}

	@JsonApiToOne(opposite = "seasonDivisions")
	public CrnkDivision getDivision() { return new CrnkDivision(seasonDivisionTeam.getSeasonDivision().getDivision()); }
	public void setDivision (CrnkDivision division) {
		this.seasonDivisionTeam.getSeasonDivision().setDivision(division.getDivision());
	}

	@JsonApiToOne(opposite = "seasonDivisionTeams")
	public CrnkTeam getTeam() { return new CrnkTeam(seasonDivisionTeam.getTeam()); }
	public void setTeam (CrnkTeam team) {
		this.seasonDivisionTeam.setTeam(team.getTeam());
	}

	@JsonApiId
	public String getId() {
		return this.id;
	}
	public void setId() {
		throw new IllegalAccessError("Please set only as part of constructor");
	}

	@JsonIgnore
	public SeasonDivisionTeam getSeasonDivisionTeam() {
		return seasonDivisionTeam;
	}

	@JsonIgnore
	private void setSeasonDivisionTeam(SeasonDivisionTeam seasonDivisionTeam) {
		this.seasonDivisionTeam = seasonDivisionTeam;
	}

	@JsonIgnore
	public Calendar getFixtureDate () {
		return this.fixtureDate;
	}

	@JsonIgnore
	private void setFixtureDate(Calendar fixtureDate) {
		this.fixtureDate = fixtureDate;
	}
}
