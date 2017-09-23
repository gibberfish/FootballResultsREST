package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;
import mindbadger.football.domain.Season;

@JsonApiResource(type = "seasons")
public class KatharsisSeason {

	private Season season;

	public KatharsisSeason(Season season) {
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

	//TODO Even though we don't want to expose this directly, Katharsis requires this to work!!!
	public Season getSeason() {
		return season;
	}

	//TODO Even though we don't want to expose this directly, Katharsis requires this to work!!!
	public void setSeason(Season season) {
		this.season = season;
	}
}
