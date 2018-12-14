package mindbadger.football.api.repository.impl;

import io.crnk.core.exception.ResourceNotFoundException;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.NotImplementedException;
import mindbadger.football.api.model.*;
import mindbadger.football.api.repository.CrnkSeasonDivisionFixtureDateRepository;
import mindbadger.football.api.repository.CrnkTeamStatisticsRepository;
import mindbadger.football.api.repository.utils.SeasonUtils;
import mindbadger.football.api.util.DateFormat;
import mindbadger.football.api.util.SourceIdUtils;
import mindbadger.football.domain.DomainObjectFactory;
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
	private SeasonUtils seasonUtils;

	@Autowired
	private SeasonRepository seasonRepository;

	@Autowired
	private TeamStatisticRepository teamStatisticRepository;

	@Autowired
	private CrnkSeasonDivisionFixtureDateRepository seasonDivisionFixtureDateRepository;

	@Autowired
	private DomainObjectFactory domainObjectFactory;

	protected CrnkTeamStatisticsRepositoryImpl() {
		super(CrnkTeamStatistics.class);
	}

	@Override
	public synchronized ResourceList<CrnkTeamStatistics> findAll(QuerySpec querySpec) {
		throw new NotImplementedException("FindAll for Team Statistics not implemented");
	}

	@Override
	public ResourceList<CrnkTeamStatistics> findAll(Iterable<String> ids, QuerySpec querySpec) {
		throw new NotImplementedException("FindAll for Team Statistics with id not implemented");	}

	@Override
	public CrnkTeamStatistics findOne(String id, QuerySpec querySpec) {
		LOG.debug("******************************************");
		LOG.debug("  FIND ONE Team Statistics");
		LOG.debug("     id=" + id);
		LOG.debug("******************************************");

		CrnkTeamStatistics teamStatistics = getTeamStatistics(SourceIdUtils.parseSeasonDivisionTeamId(id),
				DateFormat.toCalendar(SourceIdUtils.parseFixtureDate(id)));

		if (teamStatistics == null) {
			throw new ResourceNotFoundException("No matching Team Statistics found");
		}
		return teamStatistics;
	}

	@Override
	public ResourceList<CrnkTeamStatistics> findStatisticsBySeasonDivisionAndDate(String seasonDivisionId,
																				  Calendar fixtureDate,
																				  QuerySpec querySpec) {
		LOG.debug("******************************************");
		LOG.debug("  FIND StatisticsBySeasonDivisionAndDate");
		LOG.debug("     ssnDivId=" + seasonDivisionId);
		LOG.debug("     fixtDate=" + fixtureDate);
		LOG.debug("******************************************");

		SeasonDivision seasonDivision = seasonUtils.getSeasonDivisionFromCrnkId(seasonDivisionId);

		Set<SeasonDivisionTeam> seasonDivisionTeams = seasonDivision.getSeasonDivisionTeams();
		LOG.info("seasonDivisionTeams count = " + seasonDivisionTeams.size());

		List<CrnkTeamStatistics> list = new ArrayList<>();
		for (SeasonDivisionTeam seasonDivisionTeam : seasonDivisionTeams) {
			CrnkTeamStatistics crnkTeamStatistics = new CrnkTeamStatistics (seasonDivisionTeam, fixtureDate);

			List<TeamStatistic> teamStatistics = teamStatisticRepository.findTeamStatisticsForSeasonDivisionTeamOnDate(seasonDivisionTeam, fixtureDate);

			addTeamStatisticsToCrnkTeamStatistics (teamStatistics, crnkTeamStatistics);

			list.add(crnkTeamStatistics);
		}

		return querySpec.apply(list);
	}

	@Override
	public <S extends CrnkTeamStatistics> S save(S resource) {
		LOG.debug("******************************************");
		LOG.debug("  SAVE Team Statistics");
		LOG.debug("     id=" + resource.getId());
		LOG.debug("     statistics count=" + resource.getStatistics().size());
		LOG.debug("******************************************");

		String seasonDivisionTeamId = SourceIdUtils.createSeasonDivisionTeamId(resource.getSeason().getSeasonNumber(),
				resource.getDivision().getId(), resource.getTeam().getId());

		CrnkTeamStatistics teamStatistics = getTeamStatistics (seasonDivisionTeamId, resource.getFixtureDate());

		delete(teamStatistics.getId());

		resource.getStatistics().forEach((statistic) -> {
			LOG.debug("    " + statistic);

			TeamStatistic teamStatistic =
					domainObjectFactory.createTeamStatistic(
							resource.getSeasonDivisionTeam(), resource.getFixtureDate(),
							statistic.getStatistic());
			teamStatistic.setValue(statistic.getValue());
			teamStatisticRepository.save(teamStatistic);
		});

		return resource;
	}

	@Override
	public void delete(String id) {
		LOG.debug("******************************************");
		LOG.debug("  DELETE Team Statistics");
		LOG.debug("     id=" + id);
		LOG.debug("******************************************");

		String seasonDivisionTeamId = SourceIdUtils.parseSeasonDivisionTeamId(id);
		Calendar fixtureDate = DateFormat.toCalendar(SourceIdUtils.parseFixtureDate(id));

		CrnkTeamStatistics crnkTeamStatistics = getTeamStatistics(seasonDivisionTeamId, fixtureDate);

		List<CrnkTeamStatistics.Statistic> statistics = crnkTeamStatistics.getStatistics();

		statistics.forEach((statistic) -> {
			TeamStatistic teamStatistic = domainObjectFactory.createTeamStatistic(
					crnkTeamStatistics.getSeasonDivisionTeam(), crnkTeamStatistics.getFixtureDate(),
					statistic.getStatistic());
			teamStatistic.setValue(statistic.getValue());
			teamStatisticRepository.delete(teamStatistic);
		});
	}

	@Override
	public <S extends CrnkTeamStatistics> S create(S resource) {
		throw new NotImplementedException("Team Statistics records are derived from Fixture Dates and cannot be created");
	}

	private CrnkTeamStatistics getTeamStatistics (String seasonDivisionTeamId,  Calendar fixtureDate) {
		SeasonDivisionTeam seasonDivisionTeam = seasonUtils.getSeasonDivisionTeamFromCrnkId(seasonDivisionTeamId);

		CrnkSeasonDivisionFixtureDate crnkSeasonDivisionFixtureDate = seasonDivisionFixtureDateRepository.findOne(SourceIdUtils.
				createFixtureDateId(seasonDivisionTeam.getSeasonDivision().getSeason().getSeasonNumber(),
						seasonDivisionTeam.getSeasonDivision().getDivision().getDivisionId(),
						DateFormat.toString(fixtureDate)), new QuerySpec(seasonDivisionTeamId));

		CrnkTeamStatistics crnkTeamStatistics = new CrnkTeamStatistics();
		crnkTeamStatistics.setSeason(new CrnkSeason(seasonDivisionTeam.getSeasonDivision().getSeason()));
		crnkTeamStatistics.setDivision(new CrnkDivision(seasonDivisionTeam.getSeasonDivision().getDivision()));
		crnkTeamStatistics.setTeam(new CrnkTeam(seasonDivisionTeam.getTeam()));
		crnkTeamStatistics.setFixtureDate(fixtureDate);

		List<TeamStatistic> teamStatistics = teamStatisticRepository.
				findTeamStatisticsForSeasonDivisionTeamOnDate(seasonDivisionTeam, fixtureDate);

		addTeamStatisticsToCrnkTeamStatistics (teamStatistics, crnkTeamStatistics);

		return crnkTeamStatistics;
	}

	private void addTeamStatisticsToCrnkTeamStatistics (List<TeamStatistic> teamStatistics,
														CrnkTeamStatistics crnkTeamStatistics) {
		for (TeamStatistic teamStatistic : teamStatistics) {
			crnkTeamStatistics.addStatistic(teamStatistic.getStatistic(), teamStatistic.getValue());
		}
	}
}
