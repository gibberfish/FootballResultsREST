package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.*;
import mindbadger.configuration.ApplicationContextProvider;
import mindbadger.football.api.util.SourceIdUtils;
import mindbadger.football.domain.*;
import mindbadger.football.repository.DivisionRepository;
import mindbadger.football.repository.SeasonRepository;
import mindbadger.football.repository.TeamRepository;
import org.apache.log4j.Logger;

@JsonApiResource(type = "seasonDivisionTeams")
public class CrnkSeasonDivisionTeam {
    private static Logger LOG = Logger.getLogger(CrnkSeasonDivisionTeam.class);

    private SeasonRepository seasonRepository;
    private DivisionRepository divisionRepository;
    private TeamRepository teamRepository;

    private DomainObjectFactory domainObjectFactory;

    private SeasonDivisionTeam seasonDivisionTeam;

    private String id;

    public CrnkSeasonDivisionTeam () {
        //LOG.debug("*********************** Create new CrnkSeasonDivisionTeam - empty constructor");

        this.domainObjectFactory = (DomainObjectFactory)
                ApplicationContextProvider.getApplicationContext().getBean("domainObjectFactory");

        this.seasonRepository = (SeasonRepository)
                ApplicationContextProvider.getApplicationContext().getBean("seasonRepository");

        this.divisionRepository = (DivisionRepository)
                ApplicationContextProvider.getApplicationContext().getBean("divisionRepository");

        this.teamRepository = (TeamRepository)
                ApplicationContextProvider.getApplicationContext().getBean("teamRepository");

        seasonDivisionTeam = domainObjectFactory.createSeasonDivisionTeam();
        ensureSeasonDivisionTeamHasASeasonDivision();
    }

    public CrnkSeasonDivisionTeam (SeasonDivisionTeam seasonDivisionTeam) {
        this.seasonDivisionTeam = seasonDivisionTeam;
        ensureSeasonDivisionTeamHasASeasonDivision();
        createId ();
    }
    @JsonApiRelation(lookUp= LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize= SerializeType.ONLY_ID)
//    @JsonApiToOne(opposite = "seasonDivisions")
    public CrnkSeasonDivision getSeasonDivision() {
        //LOG.debug("*********************** CrnkSeasonDivisionTeam.getSeasonDivision");
        return new CrnkSeasonDivision(seasonDivisionTeam.getSeasonDivision());
    }
    public void setSeasonDivision(CrnkSeasonDivision seasonDivision) {
        //LOG.debug("*********************** CrnkSeasonDivisionTeam.setSeasonDivision");
        this.seasonDivisionTeam.setSeasonDivision(seasonDivision.getSeasonDivision());
        createId();
    }

    @JsonApiRelation(lookUp= LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize= SerializeType.ONLY_ID)
//    @JsonApiToOne(opposite = "seasonDivisions")
    public CrnkTeam getTeam() {
        //LOG.debug("*********************** CrnkSeasonDivisionTeam.getTeam");
        return new CrnkTeam(seasonDivisionTeam.getTeam()); }
    public void setTeam (CrnkTeam team) {
        //LOG.debug("*********************** CrnkSeasonDivisionTeam.SetTeam");
        this.seasonDivisionTeam.setTeam(team.getTeam());
        createId();
    }

    @JsonProperty("seasonNumber")
    public Integer getSeasonNumber () {
        return seasonDivisionTeam.getSeasonDivision().getSeason().getSeasonNumber();
    }
    public void setSeasonNumber (Integer seasonNumber) {
        Season season = seasonRepository.findOne(seasonNumber);
        this.seasonDivisionTeam.getSeasonDivision().setSeason(season);
        createId();
    }

    @JsonProperty("divisionId")
    public String getDivisionId () {
        return seasonDivisionTeam.getSeasonDivision().getDivision().getDivisionId();
    }
    public void setDivisionId (String divisionId) {
        Division division = divisionRepository.findOne(divisionId);
        this.seasonDivisionTeam.getSeasonDivision().setDivision(division);
        createId();
    }

    @JsonProperty("teamId")
    public String getTeamId () {
        return seasonDivisionTeam.getTeam().getTeamId();
    }
    public void setTeamId (String teamId) {
        Team team = teamRepository.findOne(teamId);
        this.seasonDivisionTeam.setTeam(team);
        createId();
    }

    @JsonApiId
    public String getId() {
        //LOG.debug("*********************** CrnkSeasonDivisionTeam.getId");
        return this.id;
    }
    public void setId(String id) {
        //LOG.debug("*********************** CrnkSeasonDivisionTeam.setId - don't do anything here - id set automatically, value = " + id);
        //this.id = id;
    }

    @JsonIgnore
    public SeasonDivisionTeam getSeasonDivisionTeam() {
        //LOG.debug("*********************** CrnkSeasonDivisionTeam.getSeasonDivisionTeam");
        return seasonDivisionTeam;
    }

    @JsonIgnore
    private void setSeasonDivisionTeam(SeasonDivisionTeam seasonDivisionTeam) {
        //LOG.debug("*********************** CrnkSeasonDivisionTeam.setSeasonDivisionTeam");
        this.seasonDivisionTeam = seasonDivisionTeam;
        ensureSeasonDivisionTeamHasASeasonDivision();
        createId();
    }

    private void createId() {
        this.id = SourceIdUtils.createSeasonDivisionTeamId(
                seasonDivisionTeam.getSeasonDivision() == null || seasonDivisionTeam.getSeasonDivision().getSeason() == null ? null : seasonDivisionTeam.getSeasonDivision().getSeason().getSeasonNumber(),
                seasonDivisionTeam.getSeasonDivision() == null || seasonDivisionTeam.getSeasonDivision().getDivision() == null ? null : seasonDivisionTeam.getSeasonDivision().getDivision().getDivisionId(),
                seasonDivisionTeam.getTeam() == null ? null : seasonDivisionTeam.getTeam().getTeamId()
        );
        LOG.debug("CrnkSeasonDivisionTeam id now set to : " + this.id);
    }

    private void ensureSeasonDivisionTeamHasASeasonDivision () {
        if (seasonDivisionTeam.getSeasonDivision() == null) {
            SeasonDivision seasonDivision = domainObjectFactory.createSeasonDivision();
            seasonDivisionTeam.setSeasonDivision(seasonDivision);
        }
    }
}
