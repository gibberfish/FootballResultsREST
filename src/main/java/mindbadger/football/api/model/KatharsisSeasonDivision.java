package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import io.crnk.core.resource.annotations.JsonApiToOne;
import mindbadger.football.domain.Division;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;

import java.util.HashSet;
import java.util.Set;

@JsonApiResource(type = "seasonDivisions")
public class KatharsisSeasonDivision {

	private SeasonDivision seasonDivision;

	public KatharsisSeasonDivision(SeasonDivision seasonDivision) {
		this.seasonDivision = seasonDivision;
	}

	@JsonApiToOne(opposite = "seasons")
	public KatharsisSeason getSeason() {
		return new KatharsisSeason(seasonDivision.getSeason());
	}
	public void setSeason(KatharsisSeason season) {
		this.seasonDivision.setSeason(season.getSeason());
	}

	public KatharsisDivision getDivision() { return new KatharsisDivision(seasonDivision.getDivision()); }
	public void setDivision (KatharsisDivision division) {
		this.seasonDivision.setDivision(division.getDivision());
	}

	@JsonApiId
	public String getId() {
		return seasonDivision.toString();
	}
}
