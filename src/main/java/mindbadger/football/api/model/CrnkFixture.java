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
		//LOG.debug("*********************** Create new CrnkFixture- empty constructor");

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
		this();
		//LOG.debug("*********************** Create new CrnkFixture");
		this.fixture = fixture;
	}

	@JsonApiId
	public String getId() {
		if(fixture == null) return null;
		return fixture.getFixtureId();
	}
	public void setId (String id) {
		this.fixture.setFixtureId(id);
	}

	@JsonProperty("seasonNumber")
	public Integer getSeasonNumber () {
		if (fixture==null) return null;
		return fixture.getSeasonDivision().getSeason().getSeasonNumber();
	}
	public void setSeasonNumber (Integer seasonNumber) {
		if (seasonNumber == null) return;
		Season season = seasonRepository.findOne(seasonNumber);
		this.fixture.getSeasonDivision().setSeason(season);
	}

	@JsonProperty("divisionId")
	public String getDivisionId () {
		if (fixture == null) return null;
		return fixture.getSeasonDivision().getDivision().getDivisionId();
	}
	public void setDivisionId (String divisionId) {
		Division division = divisionRepository.findOne(divisionId);
		this.fixture.getSeasonDivision().setDivision(division);
	}

	@JsonProperty("homeTeamId")
	public String getHomeTeamId () {
		if (fixture==null) return null;
		return fixture.getHomeTeam().getTeamId();
	}
	public void setHomeTeamId (String homeTeamId) {
		Team team = teamRepository.findOne(homeTeamId);
		this.fixture.setHomeTeam(team);
	}

	@JsonProperty("awayTeamId")
	public String getAwayTeamId () {
		if (fixture==null) return null;
		return fixture.getAwayTeam().getTeamId();
	}
	public void setAwayTeamId (String awayTeamId) {
		Team team = teamRepository.findOne(awayTeamId);
		this.fixture.setAwayTeam(team);
	}

	@JsonProperty("fixtureDate")
	public String getFixtureDate() {
		if (fixture == null || fixture.getFixtureDate() == null) return null;
		Calendar fixtureDate = fixture.getFixtureDate();
		return DateFormat.toString(fixtureDate.getTime());
	}
	public void setFixtureDate(String fixtureDate) throws ParseException {
		if (fixtureDate == null) {
			fixture.setFixtureDate(null);
		} else {
			fixture.setFixtureDate(DateFormat.toCalendar(fixtureDate));
		}
	}

	@JsonProperty("homeGoals")
	public Integer getHomeGoals() {
		if (fixture==null) return null;
		return fixture.getHomeGoals();
	}
	public void setHomeGoals(Integer homeGoals) {
		//LOG.debug("######################################################################");
		//LOG.debug("*********************** CrnkFixture.setHomeGoals = " + homeGoals);
		//LOG.debug("######################################################################");
		fixture.setHomeGoals(homeGoals);
	}

	@JsonProperty("awayGoals")
	public Integer getAwayGoals() {
		if(fixture==null) return null;
		return fixture.getAwayGoals();
	}
	public void setAwayGoals(Integer awayGoals) {
		fixture.setAwayGoals(awayGoals);
	}

	@JsonApiRelation(lookUp= LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize= SerializeType.ONLY_ID)
	public CrnkSeasonDivision getSeasonDivision() {
		//LOG.debug("*********************** CrnkFixture.getSeasonDivision");
		if(fixture == null) return null;
		return new CrnkSeasonDivision(fixture.getSeasonDivision());
	}
	public void setSeasonDivision(CrnkSeasonDivision seasonDivision) {
		//LOG.debug("*********************** CrnkSeasonDivisionTeam.setSeasonDivision");
		this.fixture.setSeasonDivision(seasonDivision.getSeasonDivision());
	}

	@JsonApiRelation(lookUp= LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize= SerializeType.ONLY_ID)
	public CrnkTeam getHomeTeam() {
		//LOG.debug("*********************** CrnkSeasonDivisionTeam.getHomeTeam");
		if(fixture == null) return null;
		return new CrnkTeam(fixture.getHomeTeam()); }
	public void setHomeTeam (CrnkTeam homeTeam) {
		//LOG.debug("*********************** CrnkSeasonDivisionTeam.SetHomeTeam");
		this.fixture.setHomeTeam(homeTeam.getTeam());
	}

	@JsonApiRelation(lookUp= LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize= SerializeType.ONLY_ID)
	public CrnkTeam getAwayTeam() {
		//LOG.debug("*********************** CrnkSeasonDivisionTeam.getAwayTeam");
		if(fixture == null) return null;
		return new CrnkTeam(fixture.getAwayTeam()); }
	public void setAwayTeam (CrnkTeam awayTeam) {
		//LOG.debug("*********************** CrnkSeasonDivisionTeam.SetAwayTeam");
		this.fixture.setAwayTeam(awayTeam.getTeam());
	}

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
