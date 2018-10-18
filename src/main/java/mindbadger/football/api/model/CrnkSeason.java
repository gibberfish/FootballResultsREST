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
import mindbadger.football.domain.SeasonImpl;

@SuppressWarnings("deprecation")
@JsonApiResource(type = "seasons")
public class CrnkSeason {

	private Season season;

	public CrnkSeason() {
		this.season = new SeasonImpl();
	}

	public CrnkSeason(Season season) {
		this.season = season;
	}

	@JsonProperty("seasonNumber")
	public Integer getSeasonNumber() {
		return season.getSeasonNumber();
	}
	public void setSeasonNumber(Integer seasonNumber) {
		season.setSeasonNumber(seasonNumber);
	}
	
	@JsonApiId
	public Integer getId() {
		return season.getSeasonNumber();
	}
	public void setId(Integer id) {
		season.setSeasonNumber(id);
	}

	@JsonApiToMany(opposite = "seasons")
	public Set<CrnkSeasonDivision> getSeasonDivisions () {
		Set<CrnkSeasonDivision> seasonDivisions = new HashSet<CrnkSeasonDivision>();

		//TODO make this code more concise with streams
        for (SeasonDivision seasonDivision : season.getSeasonDivisions() ) {
            seasonDivisions.add(new CrnkSeasonDivision(seasonDivision));
        }

        return seasonDivisions;
	}

    @JsonIgnore
	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!!
	public Season getSeason() {
		return season;
	}

    @JsonIgnore
	//TODO Even though we don't want to expose this directly, Crnk requires this to work!!!
	public void setSeason(Season season) {
		this.season = season;
	}
}
