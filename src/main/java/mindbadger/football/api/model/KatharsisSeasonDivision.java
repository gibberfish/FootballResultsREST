package mindbadger.football.api.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import io.crnk.core.resource.annotations.JsonApiToMany;
import io.crnk.core.resource.annotations.JsonApiToOne;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.domain.SeasonDivisionTeam;

@JsonApiResource(type = "seasonDivisions")
public class KatharsisSeasonDivision {

	private SeasonDivision seasonDivision;
	private String id;

	public KatharsisSeasonDivision(SeasonDivision seasonDivision) {
		this.seasonDivision = seasonDivision;
		this.id = seasonDivision.getSeason().getSeasonNumber() + "-" + seasonDivision.getDivision().getDivisionId();
	}

	@JsonApiToOne(opposite = "seasonDivisions")
	public KatharsisSeason getSeason() {
		return new KatharsisSeason(seasonDivision.getSeason());
	}
	public void setSeason(KatharsisSeason season) {
		this.seasonDivision.setSeason(season.getSeason());
	}

	@JsonApiToOne(opposite = "seasonDivisions")
	public KatharsisDivision getDivision() { return new KatharsisDivision(seasonDivision.getDivision()); }
	public void setDivision (KatharsisDivision division) {
		this.seasonDivision.setDivision(division.getDivision());
	}

	@JsonProperty("position")
	public int getPosition () {
		return seasonDivision.getDivisionPosition();
	}
	public void setPosition (int position) {
		seasonDivision.setDivisionPosition(position);
	}

	@JsonApiToMany(opposite = "team")
	public Set<KatharsisTeam> getTeams () {
		Set<KatharsisTeam> teams = new HashSet<KatharsisTeam> ();
		
		//TODO make this code more concise with streams
		for (SeasonDivisionTeam seasonDivisionTeam : seasonDivision.getSeasonDivisionTeams()) {
			teams.add(new KatharsisTeam(seasonDivisionTeam.getTeam()));
		}
		
		return teams;
	}

	@JsonApiId
	public String getId() {
		return this.id;
	}
	public void setId() {
		throw new IllegalAccessError("Please set only as part of constructor");
	}
	
	@JsonIgnore
	private SeasonDivision getSeasonDivision() {
		return seasonDivision;
	}

	@JsonIgnore
	private void setSeasonDivision(SeasonDivision seasonDivision) {
		this.seasonDivision = seasonDivision;
	}
}
