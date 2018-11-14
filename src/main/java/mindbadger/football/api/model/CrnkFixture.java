package mindbadger.football.api.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.crnk.core.resource.annotations.*;
import mindbadger.configuration.ApplicationContextProvider;
import mindbadger.football.api.util.DateFormat;
import mindbadger.football.domain.*;
import mindbadger.football.repository.DivisionRepository;
import mindbadger.football.repository.FixtureRepository;
import mindbadger.football.repository.SeasonRepository;
import mindbadger.football.repository.TeamRepository;
import org.apache.log4j.Logger;

@JsonApiResource(type = "fixtures")
public class CrnkFixture {
	private static Logger LOG = Logger.getLogger(CrnkFixture.class);

	private Fixture fixture;

	private SeasonRepository seasonRepository;
	private DivisionRepository divisionRepository;
	private TeamRepository teamRepository;
	private FixtureRepository fixtureRepository;

	private DomainObjectFactory domainObjectFactory;

	public CrnkFixture () {
		LOG.debug("*********************** Create new CrnkFixture- empty constructor");

		this.domainObjectFactory = (DomainObjectFactory)
				ApplicationContextProvider.getApplicationContext().getBean("domainObjectFactory");

		this.seasonRepository = (SeasonRepository)
				ApplicationContextProvider.getApplicationContext().getBean("seasonRepository");

		this.divisionRepository = (DivisionRepository)
				ApplicationContextProvider.getApplicationContext().getBean("divisionRepository");

		this.teamRepository = (TeamRepository)
				ApplicationContextProvider.getApplicationContext().getBean("teamRepository");

		this.fixtureRepository = (FixtureRepository)
				ApplicationContextProvider.getApplicationContext().getBean("fixtureRepository");

		this.fixture = domainObjectFactory.createFixture();
		this.fixture.setSeasonDivision(domainObjectFactory.createSeasonDivision());
	}

	public CrnkFixture (Fixture fixture) {
		LOG.debug("*********************** Create new CrnkFixture");
		this.fixture = fixture;
	}

	@JsonApiId
	public String getId() {
		return fixture.getFixtureId();
	}
	public void setId (String id) {
		this.fixture.setFixtureId(id);
	}






	@JsonProperty("seasonNumber")
	public Integer getSeasonNumber () {
		return fixture.getSeasonDivision().getSeason().getSeasonNumber();
	}
	public void setSeasonNumber (Integer seasonNumber) {
		Season season = seasonRepository.findOne(seasonNumber);
		this.fixture.getSeasonDivision().setSeason(season);
	}

	@JsonProperty("divisionId")
	public String getDivisionId () {
		return fixture.getSeasonDivision().getDivision().getDivisionId();
	}
	public void setDivisionId (String divisionId) {
		Division division = divisionRepository.findOne(divisionId);
		this.fixture.getSeasonDivision().setDivision(division);
	}

	@JsonProperty("homeTeamId")
	public String getHomeTeamId () {
		return fixture.getHomeTeam().getTeamId();
	}
	public void setHomeTeamId (String homeTeamId) {
		Team team = teamRepository.findOne(homeTeamId);
		this.fixture.setHomeTeam(team);
	}

	@JsonProperty("awayTeamId")
	public String getAwayTeamId () {
		return fixture.getAwayTeam().getTeamId();
	}
	public void setAwayTeamId (String awayTeamId) {
		Team team = teamRepository.findOne(awayTeamId);
		this.fixture.setAwayTeam(team);
	}

	@JsonProperty("fixtureDate")
	public String getFixtureDate() {
		Calendar fixtureDate = fixture.getFixtureDate();
		return DateFormat.toString(fixtureDate.getTime());
	}
	public void setFixtureDate(String fixtureDate) throws ParseException {
		fixture.setFixtureDate(DateFormat.toCalendar(fixtureDate));
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






	@JsonApiRelation(lookUp= LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize= SerializeType.ONLY_ID)
	public CrnkSeasonDivision getSeasonDivision() {
		LOG.debug("*********************** CrnkFixture.getSeasonDivision");
		return new CrnkSeasonDivision(fixture.getSeasonDivision());
	}
	public void setSeasonDivision(CrnkSeasonDivision seasonDivision) {
		LOG.debug("*********************** CrnkSeasonDivisionTeam.setSeasonDivision");
		this.fixture.setSeasonDivision(seasonDivision.getSeasonDivision());
	}

	@JsonApiRelation(lookUp= LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize= SerializeType.ONLY_ID)
	public CrnkTeam getHomeTeam() {
		LOG.debug("*********************** CrnkSeasonDivisionTeam.getHomeTeam");
		return new CrnkTeam(fixture.getHomeTeam()); }
	public void setHomeTeam (CrnkTeam homeTeam) {
		LOG.debug("*********************** CrnkSeasonDivisionTeam.SetHomeTeam");
		this.fixture.setHomeTeam(homeTeam.getTeam());
	}

	@JsonApiRelation(lookUp= LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize= SerializeType.ONLY_ID)
	public CrnkTeam getAwayTeam() {
		LOG.debug("*********************** CrnkSeasonDivisionTeam.getAwayTeam");
		return new CrnkTeam(fixture.getAwayTeam()); }
	public void setAwayTeam (CrnkTeam awayTeam) {
		LOG.debug("*********************** CrnkSeasonDivisionTeam.SetAwayTeam");
		this.fixture.setAwayTeam(awayTeam.getTeam());
	}



//	@JsonProperty("season")
//	public Season getSeason() {
//		return fixture.getSeason();
//	}
//	public void setSeason(Season season) {
//		fixture.setSeason(season);
//	}
//
//	@JsonProperty("division")
//	public Division getDivision() {
//		return fixture.getDivision();
//	}
//
//	public void setDivision(Division division) {
//		fixture.setDivision(division);
//	}
//	@JsonProperty("homeTeam")
//	public Team getHomeTeam() {
//		return fixture.getHomeTeam();
//	}
//
//	public void setHomeTeam(Team homeTeam) {
//		fixture.setHomeTeam(homeTeam);
//	}
//	@JsonProperty("awayTeam")
//	public Team getAwayTeam() {
//		return fixture.getAwayTeam();
//	}
//
//	public void setAwayTeam(Team awayTeam) {
//		fixture.setAwayTeam(awayTeam);
//	}




	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!!
	@JsonIgnore
	public Fixture getFixture() {
		return fixture;
	}
	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!!
	@JsonIgnore
	private void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}
}
