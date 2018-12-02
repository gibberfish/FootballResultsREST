package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import io.crnk.core.resource.annotations.JsonApiToOne;
import mindbadger.configuration.ApplicationContextProvider;
import mindbadger.football.api.util.DateFormat;
import mindbadger.football.api.util.SourceIdUtils;
import mindbadger.football.domain.DomainObjectFactory;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.domain.SeasonDivisionTeam;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressWarnings("deprecation")
@JsonApiResource(type = "teamStatistics")
public class CrnkTeamStatistics {
	private static Logger LOG = Logger.getLogger(CrnkTeamStatistics.class);

	private SeasonDivisionTeam seasonDivisionTeam;
	private Calendar fixtureDate;

	private List<Statistic> statistics = new ArrayList<>();

	private DomainObjectFactory domainObjectFactory;

	private String id;

	public CrnkTeamStatistics() {
		this.domainObjectFactory = (DomainObjectFactory)
				ApplicationContextProvider.getApplicationContext().getBean("domainObjectFactory");

		this.seasonDivisionTeam = domainObjectFactory.createSeasonDivisionTeam();
		ensureSeasonDivisionTeamHasASeasonDivision();
	}

	public CrnkTeamStatistics(SeasonDivisionTeam seasonDivisionTeam, Calendar fixtureDateCalendar) {
		this.seasonDivisionTeam = seasonDivisionTeam;
		this.fixtureDate = fixtureDateCalendar;
		ensureSeasonDivisionTeamHasASeasonDivision();
		createId();
	}

	@JsonApiId
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
//		throw new IllegalAccessError("Please set only as part of constructor");
	}

	@JsonProperty("statistics")
	public List<Statistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}

	@JsonApiToOne(opposite = "seasonDivisions")
	@JsonProperty("season")
	public CrnkSeason getSeason() {
		return new CrnkSeason(seasonDivisionTeam.getSeasonDivision().getSeason());
	}

	public void setSeason(CrnkSeason season) {
		this.seasonDivisionTeam.getSeasonDivision().setSeason(season.getSeason());
		createId();
	}
	@JsonApiToOne(opposite = "seasonDivisions")
	public CrnkDivision getDivision() {
		return new CrnkDivision(seasonDivisionTeam.getSeasonDivision().getDivision());
	}

	public void setDivision(CrnkDivision division) {
		this.seasonDivisionTeam.getSeasonDivision().setDivision(division.getDivision());
		createId();
	}
	@JsonApiToOne(opposite = "seasonDivisionTeams")
	public CrnkTeam getTeam() {
		return new CrnkTeam(seasonDivisionTeam.getTeam());
	}

	public void setTeam(CrnkTeam team) {
		this.seasonDivisionTeam.setTeam(team.getTeam());
		createId();
	}
	@JsonIgnore
	public SeasonDivisionTeam getSeasonDivisionTeam() {
		return seasonDivisionTeam;
	}

	@JsonIgnore
	private void setSeasonDivisionTeam(SeasonDivisionTeam seasonDivisionTeam) {
		this.seasonDivisionTeam = seasonDivisionTeam;
		ensureSeasonDivisionTeamHasASeasonDivision();
		createId();
	}

	@JsonIgnore
	public Calendar getFixtureDate() {
		return fixtureDate;
	}
	@JsonIgnore
	public void setFixtureDate(Calendar fixtureDate) {
		this.fixtureDate = fixtureDate;
		createId();
	}

	private void createId() {
		// <season>-<division>-<team>-<date>
		this.id = SourceIdUtils.createTeamStatisticsId(
				seasonDivisionTeam.getSeasonDivision() == null || seasonDivisionTeam.getSeasonDivision().getSeason() == null ? null : seasonDivisionTeam.getSeasonDivision().getSeason().getSeasonNumber(),
				seasonDivisionTeam.getSeasonDivision() == null || seasonDivisionTeam.getSeasonDivision().getDivision() == null ? null : seasonDivisionTeam.getSeasonDivision().getDivision().getDivisionId(),
				seasonDivisionTeam.getTeam() == null ? null : seasonDivisionTeam.getTeam().getTeamId(),
				fixtureDate == null ? null : DateFormat.toString(fixtureDate)
		);
		LOG.debug("CrnkTeamStatistics id now set to : " + this.id);
	}

	private void ensureSeasonDivisionTeamHasASeasonDivision () {
		if (seasonDivisionTeam.getSeasonDivision() == null) {
			SeasonDivision seasonDivision = domainObjectFactory.createSeasonDivision();
			seasonDivisionTeam.setSeasonDivision(seasonDivision);
		}
	}

	public void addStatistic (String statistic, Integer value) {
		this.statistics.add(
				new Statistic(statistic,value)
		);
	}

	public static class Statistic {
		private String statistic;
		private Integer value;

		public Statistic () {
		}

		public Statistic (String statistic, Integer value) {
			this.statistic = statistic;
			this.value = value;
		}

		public String getStatistic() {
			return statistic;
		}

		public Integer getValue() {
			return value;
		}

		public void setStatistic(String statistic) {
			this.statistic = statistic;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "STATISTIC:"+statistic+"="+value;
		}
	}
}
