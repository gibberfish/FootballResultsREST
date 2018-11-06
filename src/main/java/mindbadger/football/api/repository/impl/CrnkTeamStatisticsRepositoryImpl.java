package mindbadger.football.api.repository.impl;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkSeasonDivisionFixtureDate;
import mindbadger.football.api.model.CrnkTeamStatistics;
import mindbadger.football.api.repository.CrnkTeamStatisticsRepository;
import mindbadger.football.api.util.DateFormat;
import mindbadger.football.api.util.SourceIdParser;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.domain.SeasonDivisionTeam;
import mindbadger.football.domain.TeamStatistic;
import mindbadger.football.repository.SeasonRepository;
import mindbadger.football.repository.TeamStatisticRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Component
public class CrnkTeamStatisticsRepositoryImpl extends ResourceRepositoryBase<CrnkTeamStatistics, String>
	implements CrnkTeamStatisticsRepository {

	private static Logger LOG = Logger.getLogger(CrnkTeamStatisticsRepositoryImpl.class);

	@Autowired
	private SeasonRepository seasonRepository;

	@Autowired
	private TeamStatisticRepository teamStatisticRepository;

	protected CrnkTeamStatisticsRepositoryImpl() {
		super(CrnkTeamStatistics.class);
	}

	@Override
	public synchronized ResourceList<CrnkTeamStatistics> findAll(QuerySpec querySpec) {
		throw new RuntimeException();
	}

	@Override
	public ResourceList<CrnkTeamStatistics> findAll(Iterable<String> ids, QuerySpec querySpec) {
		throw new RuntimeException();
	}

	@Override
	public CrnkTeamStatistics findOne(String id, QuerySpec querySpec) {
		System.out.println("CrnkTeamStatisticsRepositoryImpl.findOne, id = " + id);
		throw new RuntimeException();
	}

	@Override
	public ResourceList<CrnkTeamStatistics> findStatisticsBySeasonDivisionAndDate(String seasonDivisionId, Calendar cal, QuerySpec querySpec) {
		LOG.info("******************** findStatisticsBySeasonDivisionAndDate *****************************");
		LOG.info("date to find = " + DateFormat.toString(cal));
		LOG.info("seasonDivisionId= " + seasonDivisionId);
		String id = seasonDivisionId + "_" + DateFormat.toString(cal);

		Season season = seasonRepository.findOne(SourceIdParser.parseSeasonId(seasonDivisionId));
		String divisionId = SourceIdParser.parseDivisionId (seasonDivisionId);
		SeasonDivision seasonDivision = null;
		for (SeasonDivision seasonDivisionToCheck : season.getSeasonDivisions()) {
			if (divisionId.equals(seasonDivisionToCheck.getDivision().getDivisionId())) {
				seasonDivision = seasonDivisionToCheck;
			}
		}

		LOG.info("seasonDivision = " + seasonDivision);

		Set<SeasonDivisionTeam> seasonDivisionTeams = seasonDivision.getSeasonDivisionTeams();
		LOG.info("seasonDivisionTeams count = " + seasonDivisionTeams.size());

		List<CrnkTeamStatistics> list = new ArrayList<>();
		for (SeasonDivisionTeam seasonDivisionTeam : seasonDivisionTeams) {
			CrnkTeamStatistics crnkTeamStatistics = new CrnkTeamStatistics (seasonDivisionTeam, cal);

			List<TeamStatistic> teamStatistics = teamStatisticRepository.findTeamStatisticsForSeasonDivisionTeamOnDate(seasonDivisionTeam, cal);

			for (TeamStatistic teamStatistic : teamStatistics) {
				crnkTeamStatistics.getStatistics().put(teamStatistic.getStatistic(), teamStatistic.getValue());
			}

			list.add(crnkTeamStatistics);
		}

		return querySpec.apply(list);
	}
}
