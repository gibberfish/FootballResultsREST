package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.*;
import mindbadger.configuration.ApplicationContextProvider;
import mindbadger.football.domain.DomainObjectFactory;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.repository.DivisionRepository;
import mindbadger.football.repository.SeasonRepository;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("deprecation")
@JsonApiResource(type = "seasonDivisions")
public class CrnkSeasonDivision {
	private static Logger LOG = Logger.getLogger(CrnkSeasonDivision.class);

	private SeasonRepository seasonRepository;
	private DivisionRepository divisionRepository;
	private DomainObjectFactory domainObjectFactory;

	private SeasonDivision seasonDivision;
	private String id;

	public CrnkSeasonDivision () {
		LOG.debug("*********************** Create new CrnkSeasonDivision - empty constructor");

		this.domainObjectFactory = (DomainObjectFactory)
				ApplicationContextProvider.getApplicationContext().getBean("domainObjectFactory");

		this.seasonRepository = (SeasonRepository)
				ApplicationContextProvider.getApplicationContext().getBean("seasonRepository");

		this.divisionRepository = (DivisionRepository)
				ApplicationContextProvider.getApplicationContext().getBean("divisionRepository");

		seasonDivision = domainObjectFactory.createSeasonDivision();
	}

	public CrnkSeasonDivision(SeasonDivision seasonDivision) {
		this.seasonDivision = seasonDivision;
		createId();
	}

//	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize=SerializeType.ONLY_ID)
	@JsonApiToOne(opposite = "seasonDivisions")
	public CrnkSeason getSeason() {
		LOG.debug("*********************** CrnkSeasonDivision.getSeason");
		return new CrnkSeason(seasonDivision.getSeason());
	}
	public void setSeason(CrnkSeason season) {
		LOG.debug("*********************** CrnkSeasonDivision.setSeason");
		this.seasonDivision.setSeason(season.getSeason());
		createId();
	}

//	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize=SerializeType.ONLY_ID)
	@JsonApiToOne(opposite = "seasonDivisions")
	public CrnkDivision getDivision() {
		LOG.debug("*********************** CrnkSeasonDivision.getDivision");
		return new CrnkDivision(seasonDivision.getDivision()); }
	public void setDivision (CrnkDivision division) {
		LOG.debug("*********************** CrnkSeasonDivision.SetDivision");
		this.seasonDivision.setDivision(division.getDivision());
		createId();
	}

	@JsonProperty("seasonNumber")
	public int getSeasonNumber () {
		return seasonDivision.getSeason().getSeasonNumber();
	}
	public void setSeasonNumber (int seasonNumber) {
		this.seasonDivision.setSeason(seasonRepository.findOne(seasonNumber));
		createId();
	}

	@JsonProperty("divisionId")
	public String getDivisionId () {
		return seasonDivision.getDivision().getDivisionId();
	}
	public void setDivisionId (String divisionId) {
		this.seasonDivision.setDivision(divisionRepository.findOne(divisionId));
		createId();
	}

	@JsonProperty("position")
	public int getPosition () {
		LOG.debug("*********************** CrnkSeasonDivision.getPosition");
		return seasonDivision.getDivisionPosition();
	}
	public void setPosition (int position) {
		LOG.debug("*********************** CrnkSeasonDivision.setPosition");
		seasonDivision.setDivisionPosition(position);
	}

	@JsonApiToMany(opposite = "team")
	public Set<CrnkSeasonDivisionTeam> getTeams () {
		LOG.debug("*********************** CrnkSeasonDivision.getTeams");
		Set<CrnkSeasonDivisionTeam> teams = new HashSet<> ();

		seasonDivision.getSeasonDivisionTeams().stream().forEach(seasonDivisionTeam -> {

			teams.add(new CrnkSeasonDivisionTeam(seasonDivisionTeam));
		});

		return teams;
	}

	@JsonApiToMany(opposite = "fixture")
	public Set<CrnkFixture> getFixtures () {
		LOG.debug("*********************** CrnkSeasonDivision.getFixtures");
		throw new UnsupportedOperationException("CrnkSeasonDivision.getFixtures not implemented yet");
//		Set<CrnkFixture> fixtures = new HashSet<CrnkFixture>();
//		return fixtures;
	}

	@JsonApiToMany(opposite = "fixtureDate")
	public Set<CrnkSeasonDivisionFixtureDate> getFixtureDates () {
		LOG.debug("*********************** CrnkSeasonDivision.getFixtureDates");
		throw new UnsupportedOperationException("CrnkSeasonDivision.getFixtureDates not implemented yet");
//		Set<CrnkSeasonDivisionFixtureDate> fixtureDates = new HashSet<CrnkSeasonDivisionFixtureDate>();
//		return fixtureDates;
	}

	@JsonApiId
	public String getId() {
		LOG.debug("*********************** CrnkSeasonDivision.getId");
		return this.id;
	}
	public void setId(String id) {
		LOG.debug("*********************** CrnkSeasonDivision.setId - don't do anything here - id set automatically, value = " + id);
		//this.id = id;
	}
	
	@JsonIgnore
	public SeasonDivision getSeasonDivision() {
		LOG.debug("*********************** CrnkSeasonDivision.getSeasonDivision");
		return seasonDivision;
	}

	@JsonIgnore
	private void setSeasonDivision(SeasonDivision seasonDivision) {
		LOG.debug("*********************** CrnkSeasonDivision.setSeasonDivision");
		this.seasonDivision = seasonDivision;
		createId();
	}

	private void createId () {
		this.id = seasonDivision.getSeason() == null ? "-" : seasonDivision.getSeason().getSeasonNumber() + "-";
		this.id += seasonDivision.getDivision() == null ? "" : seasonDivision.getDivision().getDivisionId();
		LOG.debug("CrnkSeasonDivision id now set to : " + this.id);
	}
}
