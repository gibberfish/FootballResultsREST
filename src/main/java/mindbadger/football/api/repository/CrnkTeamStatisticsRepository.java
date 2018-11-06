package mindbadger.football.api.repository;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryV2;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkTeamStatistics;

import java.util.Calendar;

public interface CrnkTeamStatisticsRepository extends ResourceRepositoryV2<CrnkTeamStatistics, String> {
    ResourceList<CrnkTeamStatistics> findStatisticsBySeasonDivisionAndDate(String seasonDivisionId, Calendar cal, QuerySpec querySpec);
}
