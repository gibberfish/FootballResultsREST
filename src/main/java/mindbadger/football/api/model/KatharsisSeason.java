package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import io.crnk.core.resource.annotations.JsonApiToMany;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	@JsonApiToMany(opposite = "seasonDivisions")
	public Set<KatharsisSeasonDivision> getSeasonDivisions () {
		Set<KatharsisSeasonDivision> seasonDivisions = new HashSet<KatharsisSeasonDivision>();

		//TODO make this code more consise with streams
        for (SeasonDivision seasonDivision : season.getSeasonDivisions() ) {
            seasonDivisions.add(new KatharsisSeasonDivision(seasonDivision));
        }

        return seasonDivisions;
	}

    @JsonIgnore
	//TODO Even though we don't want to expose this directly, Katharsis requires this to work!!!
	public Season getSeason() {
		return season;
	}

    @JsonIgnore
	//TODO Even though we don't want to expose this directly, Katharsis requires this to work!!!
	public void setSeason(Season season) {
		this.season = season;
	}
}
