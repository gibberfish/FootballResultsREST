package mindbadger.football.api.repository;

import io.crnk.core.engine.internal.utils.MultivaluedMap;
import io.crnk.core.engine.query.QueryAdapter;
import io.crnk.core.engine.registry.ResourceRegistry;
import io.crnk.core.queryspec.FilterOperator;
import io.crnk.core.queryspec.FilterSpec;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.RelationshipRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkFixture;
import mindbadger.football.api.model.CrnkFixtureDate;
import mindbadger.football.domain.Fixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@Component
public class FixtureDateToFixtureRepository extends RelationshipRepositoryBase<CrnkFixtureDate, String, CrnkFixture, String> {

    private CrnkFixtureRepository fixtureRepository;

    @Autowired
    public FixtureDateToFixtureRepository (CrnkFixtureRepository fixtureRepository) {
        super(CrnkFixtureDate.class, CrnkFixture.class);
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

        String seasonDivisionId = parseSeasonDivisionFromSourceId(sourceId);
        String fixtureDateParam = parseFixtureDateFromSourceid(sourceId);

        try {
            Date fixtureDate = CrnkFixture.fixtureDateFormatter.parse(fixtureDateParam);
            Calendar cal = Calendar.getInstance();
            cal.setTime(fixtureDate);
            return fixtureRepository.findFixturesBySeasonDivisionAndDate(seasonDivisionId, cal, querySpec);
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        ResourceList<CrnkFixture> fixtures = fixtureRepository.findAll(querySpec);
//        CrnkSeason season = seasonRepository.findOne(parseSeasonIdFromSourceId(sourceId), querySpec);
//
//        System.out.println("***** DEBUG: Number of season divisions = " + season.getSeasonDivisions().size());
//
//        for (CrnkSeasonDivision seasonDivision : season.getSeasonDivisions() ) {
//            if (sourceId.equals(seasonDivision.getId())) {
//                System.out.println("***** DEBUG: Number of season divisions = " + seasonDivision.getTeams().size());
//
//                return querySpec.apply(seasonDivision.getTeams());
//            }
//        }
//        return null;

        return null;
    }

    private String parseSeasonDivisionFromSourceId (String sourceId) {
        String[] idSplit = sourceId.split("_");
        return idSplit[0];
    }

    private String parseFixtureDateFromSourceid (String sourceId) {
        String[] idSplit = sourceId.split("_");
        return idSplit[1];
    }

    @Override
    public Class<CrnkFixtureDate> getSourceResourceClass() {
        return CrnkFixtureDate.class;
    }

    @Override
    public Class<CrnkFixture> getTargetResourceClass() {
        return CrnkFixture.class;
    }
}
