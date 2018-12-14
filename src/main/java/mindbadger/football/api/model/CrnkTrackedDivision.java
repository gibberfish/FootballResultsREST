package mindbadger.football.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;
import mindbadger.configuration.ApplicationContextProvider;
import mindbadger.football.domain.DomainObjectFactory;
import mindbadger.football.domain.TrackedDivision;
import org.apache.log4j.Logger;

@JsonApiResource(type = "tracked_division")
public class CrnkTrackedDivision {
    private static Logger LOG = Logger.getLogger(CrnkTrackedDivision.class);

    private TrackedDivision trackedDivision;

    private DomainObjectFactory domainObjectFactory;

    private String id;

    public CrnkTrackedDivision() {
        this.domainObjectFactory = (DomainObjectFactory)
                ApplicationContextProvider.getApplicationContext().getBean("domainObjectFactory");
        this.trackedDivision = domainObjectFactory.createTrackedDivision();
        createId();
    }

    public CrnkTrackedDivision(TrackedDivision trackedDivision) {
        this.trackedDivision = trackedDivision;
        createId();
    }

    @JsonApiId
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("dialect")
    public String getDialect() {
        return trackedDivision.getDialect();
    }

    public void setDialect(String dialect) {
        trackedDivision.setDialect(dialect);
        createId();
    }

    @JsonProperty("sourceId")
    public Integer getSourceId() {
        return trackedDivision.getSourceId();
    }

    public void setSourceId(Integer sourceId) {
        this.trackedDivision.setSourceId(sourceId);
        createId();
    }

    @JsonIgnore
    public TrackedDivision getTrackedDivision() {
        return trackedDivision;
    }

    @JsonIgnore
    public void setTrackedDivision(TrackedDivision trackedDivision) {
        this.trackedDivision = trackedDivision;
        createId();
    }

    private void createId () {
        this.id = trackedDivision.getDialect() + "_" +
                trackedDivision.getSourceId();
    }
}
