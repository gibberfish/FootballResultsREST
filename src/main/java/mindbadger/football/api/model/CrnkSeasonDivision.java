package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.*;
import mindbadger.configuration.ApplicationContextProvider;
import mindbadger.football.api.util.SourceIdUtils;
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

	@JsonApiToOne(opposite = "seasonDivisions")
	public CrnkSeason getSeason() {
		return new CrnkSeason(seasonDivision.getSeason());
	}
	public void setSeason(CrnkSeason season) {
		this.seasonDivision.setSeason(season.getSeason());
		createId();
	}

	@JsonApiToOne(opposite = "seasonDivisions")
	public CrnkDivision getDivision() {
		return new CrnkDivision(seasonDivision.getDivision()); }
	public void setDivision (CrnkDivision division) {
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
		return seasonDivision.getDivisionPosition();
	}
	public void setPosition (int position) {
		seasonDivision.setDivisionPosition(position);
	}

	@JsonApiToMany(opposite = "team")
	public Set<CrnkSeasonDivisionTeam> getTeams () {
		Set<CrnkSeasonDivisionTeam> teams = new HashSet<> ();
		seasonDivision.getSeasonDivisionTeams().stream().forEach(seasonDivisionTeam -> {
			teams.add(new CrnkSeasonDivisionTeam(seasonDivisionTeam));
		});
		return teams;
	}

	@JsonApiToMany(opposite = "fixture")
	public Set<CrnkFixture> getFixtures () {
		throw new UnsupportedOperationException("CrnkSeasonDivision.getFixtures not implemented yet");
	}

	@JsonApiToMany(opposite = "fixtureDate")
	public Set<CrnkSeasonDivisionFixtureDate> getFixtureDates () {
		throw new UnsupportedOperationException("CrnkSeasonDivision.getFixtureDates not implemented yet");
	}

	@JsonApiId
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
	}
	
	@JsonIgnore
	public SeasonDivision getSeasonDivision() {
		return seasonDivision;
	}

	@JsonIgnore
	private void setSeasonDivision(SeasonDivision seasonDivision) {
		this.seasonDivision = seasonDivision;
		createId();
	}

	private void createId () {
		this.id = SourceIdUtils.createSeasonDivisionId(
				seasonDivision.getSeason() == null ? null : seasonDivision.getSeason().getSeasonNumber(),
				seasonDivision.getDivision() == null ? null : seasonDivision.getDivision().getDivisionId()
		);
	}
}
