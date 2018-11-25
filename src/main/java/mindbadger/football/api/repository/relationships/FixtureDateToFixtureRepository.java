package mindbadger.football.api.repository.relationships;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkFixture;
import mindbadger.football.api.model.CrnkSeasonDivisionFixtureDate;
import mindbadger.football.api.repository.CrnkFixtureRepository;
import mindbadger.football.api.util.DateFormat;
import mindbadger.football.api.util.SourceIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class FixtureDateToFixtureRepository extends RelationshipRepositoryBase<CrnkSeasonDivisionFixtureDate, String, CrnkFixture, String> {

    private CrnkFixtureRepository fixtureRepository;

    @Autowired
    public FixtureDateToFixtureRepository (CrnkFixtureRepository fixtureRepository) {
        super(CrnkSeasonDivisionFixtureDate.class, CrnkFixture.class);
        this.fixtureRepository = fixtureRepository;
    }

    @Override
    public CrnkFixture findOneTarget(String sourceId, String fieldName, QuerySpec querySpec) {
        throw new IllegalArgumentException();
    }

    @Override
    public ResourceList<CrnkFixture> findManyTargets(String sourceId, String fieldName, QuerySpec querySpec) {

        System.out.println("sourceId = " + sourceId);
        System.out.println("fieldName = " + fieldName);

        String seasonDivisionId = SourceIdUtils.parseSeasonDivisionId(sourceId);
        String fixtureDateParam = SourceIdUtils.parseFixtureDate(sourceId);

        Calendar cal = DateFormat.toCalendar(fixtureDateParam);
        return fixtureRepository.findFixturesBySeasonDivisionAndDate(seasonDivisionId, cal, querySpec);
    }

    @Override
    public Class<CrnkSeasonDivisionFixtureDate> getSourceResourceClass() {
        return CrnkSeasonDivisionFixtureDate.class;
    }

    @Override
    public Class<CrnkFixture> getTargetResourceClass() {
        return CrnkFixture.class;
    }
}
