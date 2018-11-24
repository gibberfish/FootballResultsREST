package mindbadger.football.api.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import io.crnk.core.resource.annotations.JsonApiToMany;
import mindbadger.configuration.ApplicationContextProvider;
import mindbadger.football.domain.DomainObjectFactory;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.domain.SeasonImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("deprecation")
@JsonApiResource(type = "seasons")
public class CrnkSeason {

	Logger LOG = Logger.getLogger(CrnkSeason.class);

	//@Autowired
	private DomainObjectFactory domainObjectFactory;

	private Season season;

	public CrnkSeason() {
		this.domainObjectFactory = (DomainObjectFactory)
				ApplicationContextProvider.getApplicationContext().getBean("domainObjectFactory");

		this.season = domainObjectFactory.createSeason();
	}

	public CrnkSeason(Season season) {
		this.season = season;
	}

	@JsonProperty("seasonNumber")
	public Integer getSeasonNumber() {
		//LOG.debug("*********************** CrnkSeason.getSeasonNumber: " + season.getSeasonNumber());
		return season.getSeasonNumber();
	}
	public void setSeasonNumber(Integer seasonNumber) {
		//LOG.debug("*********************** CrnkSeason.setSeasonNumber: " + seasonNumber);
		season.setSeasonNumber(seasonNumber);
	}
	
	@JsonApiId
	public Integer getId() {
		if (season == null) return null;
		//LOG.debug("*********************** CrnkSeason.getId: " + season.getSeasonNumber());
		return season.getSeasonNumber();
	}
	public void setId(Integer id) {
		//LOG.debug("*********************** CrnkSeason.setId: " + id);
		season.setSeasonNumber(id);
	}

	@JsonApiToMany(opposite = "seasons")
	public Set<CrnkSeasonDivision> getSeasonDivisions () {
		//LOG.debug("*********************** CrnkSeason.getSeasonDivisions");

		Set<CrnkSeasonDivision> seasonDivisions = new HashSet<CrnkSeasonDivision>();

		//TODO make this code more concise with streams
        for (SeasonDivision seasonDivision : season.getSeasonDivisions() ) {
            seasonDivisions.add(new CrnkSeasonDivision(seasonDivision));
        }

        return seasonDivisions;
	}
	public void setSeasonDivisions (Set<CrnkSeasonDivision> seasonDivisions) {
		//LOG.debug("*********************** CrnkSeason.setSeasonDivisions");
	}

    @JsonIgnore
	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!!
	public Season getSeason() {
		//LOG.debug("*********************** CrnkSeason.getSeason");
		return season;
	}

    @JsonIgnore
	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!!
	public void setSeason(Season season) {
		//LOG.debug("*********************** CrnkSeason.setSeason");
		this.season = season;
	}
}
