package mindbadger.football.api.repository;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryV2;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkFixture;

import java.util.Calendar;

public interface CrnkFixtureRepository extends ResourceRepositoryV2<CrnkFixture, String> {
    public ResourceList<CrnkFixture> findFixturesBySeasonDivisionAndDate (String seasonDivisionId, Calendar fixtureDate, QuerySpec querySpec);
}
