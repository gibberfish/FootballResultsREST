package mindbadger.football.api.repository.impl;

import io.crnk.core.queryspec.FilterSpec;
import mindbadger.football.api.model.CrnkSeasonDivision;
import mindbadger.football.api.model.CrnkSeasonDivisionFixtureDate;
import mindbadger.football.api.model.CrnkSeasonDivisionTeam;
import mindbadger.football.api.repository.CrnkSeasonDivisionFixtureDateRepository;
import mindbadger.football.api.repository.CrnkSeasonDivisionRepository;
import mindbadger.football.api.util.DateFormat;
import mindbadger.football.api.util.SourceIdParser;
import mindbadger.football.domain.Season;
import mindbadger.football.domain.SeasonDivision;
import mindbadger.football.domain.SeasonDivisionTeam;
import mindbadger.football.repository.FixtureRepository;
import mindbadger.football.repository.SeasonRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class CrnkSeasonDivisionFixtureDateRepositoryImpl extends ResourceRepositoryBase<CrnkSeasonDivisionFixtureDate, String>
	implements CrnkSeasonDivisionFixtureDateRepository {

	private static Logger LOG = Logger.getLogger(CrnkSeasonDivisionFixtureDateRepositoryImpl.class);

	@Autowired
	private SeasonRepository seasonRepository;

	@Autowired
	private FixtureRepository fixtureRepository;

	//	@Autowired
//	private CrnkSeasonDivisionRepository seasonDivisionRepository;
//
//	@Autowired
//	private CrnkSeasonDivisionFixtureDateRepository seasonDivisionFixtureDateRepository;

	protected CrnkSeasonDivisionFixtureDateRepositoryImpl() {
		super(CrnkSeasonDivisionFixtureDate.class);
	}

	@Override
	public synchronized ResourceList<CrnkSeasonDivisionFixtureDate> findAll(QuerySpec querySpec) {
		LOG.debug("*********************** CrnkSeasonDivisionFixtureDateRepositoryImpl.findAll");

		List<CrnkSeasonDivisionFixtureDate> katharsisSeasonDivisionFixtureDates = new ArrayList<>();

		for (FilterSpec filter : querySpec.getFilters()) {
			LOG.debug("   filter: " + filter);
			LOG.debug("      expression: " + filter.getExpression());
			LOG.debug("      operator  : " + filter.getOperator());
			LOG.debug("      value     : " + filter.getValue());
			LOG.debug("      attribute : " + filter.getAttributePath().get(0));

			String value = filter.getValue().toString();
			value = value.replace("[", "");
			value = value.replace("]", "");

//			if (filter.getAttributePath().size() > 0 && "id".equals(filter.getAttributePath().get(0))) {
//				LOG.debug("  findAll filtering on id.");
//				katharsisSeasonDivisionTeams.add(findOne(value, querySpec));
//			}
		}

		return querySpec.apply(katharsisSeasonDivisionFixtureDates);
	}

//	@Override
//	public ResourceList<CrnkSeasonDivisionFixtureDate> findAll(Iterable<String> ids, QuerySpec querySpec) {
//		throw new NotImplementedException();
//	}

	@Override
	public CrnkSeasonDivisionFixtureDate findOne(String id, QuerySpec querySpec) {
		LOG.info("findOne CrnkSeasonDivisionFixtureDate : id = " + id);

		String seasonDivisionFixtureDateString = SourceIdParser.parseFixtureDate(id);

		Integer seasonNumber = SourceIdParser.parseSeasonId(id);
		String divisionId = SourceIdParser.parseDivisionId(id);
		String seasonDivisionId = SourceIdParser.parseSeasonDivisionId(id);

		LOG.info("seasonDivision ID = " + seasonDivisionId);
		LOG.info("fixtureDate string = " + seasonDivisionFixtureDateString);

		//TODO Copied from CrnkSeasonDivisionTeamRepoImpl - refactor into a service
		Season existingSeason = seasonRepository.findOne(seasonNumber);

		for (SeasonDivision seasonDivision : existingSeason.getSeasonDivisions()) {
			if (divisionId.equals(seasonDivision.getDivision().getDivisionId())) {

				List<Calendar> fixtureDates = fixtureRepository.getFixtureDatesForDivisionInSeason(seasonDivision);

				for (Calendar fixtureDate : fixtureDates) {
					if (seasonDivisionFixtureDateString.equals(DateFormat.toString(fixtureDate))) {
						return new CrnkSeasonDivisionFixtureDate(seasonDivision, seasonDivisionFixtureDateString);
					}
				}
			}
		}

		return null;
	}
}
