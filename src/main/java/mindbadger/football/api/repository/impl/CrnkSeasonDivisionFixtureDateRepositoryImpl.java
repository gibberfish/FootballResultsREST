package mindbadger.football.api.repository.impl;

import mindbadger.football.api.model.CrnkSeasonDivision;
import mindbadger.football.api.model.CrnkSeasonDivisionFixtureDate;
import mindbadger.football.api.repository.CrnkSeasonDivisionFixtureDateRepository;
import mindbadger.football.api.repository.CrnkSeasonDivisionRepository;
import mindbadger.football.api.util.SourceIdParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class CrnkSeasonDivisionFixtureDateRepositoryImpl extends ResourceRepositoryBase<CrnkSeasonDivisionFixtureDate, String>
	implements CrnkSeasonDivisionFixtureDateRepository {

	private static Logger LOG = Logger.getLogger(CrnkSeasonDivisionFixtureDateRepositoryImpl.class);

	@Autowired
	private CrnkSeasonDivisionRepository seasonDivisionRepository;

	@Autowired
	private CrnkSeasonDivisionFixtureDateRepository seasonDivisionFixtureDateRepository;

	protected CrnkSeasonDivisionFixtureDateRepositoryImpl() {
		super(CrnkSeasonDivisionFixtureDate.class);
	}

	@Override
	public synchronized ResourceList<CrnkSeasonDivisionFixtureDate> findAll(QuerySpec querySpec) {
		throw new NotImplementedException();
	}

	@Override
	public ResourceList<CrnkSeasonDivisionFixtureDate> findAll(Iterable<String> ids, QuerySpec querySpec) {
		throw new NotImplementedException();
	}

	@Override
	public CrnkSeasonDivisionFixtureDate findOne(String id, QuerySpec querySpec) {
		String fixutureDateString = SourceIdParser.parseFixtureDate(id);
		LOG.info("findOne CrnkSeasonDivisionFixtureDate : id = " + id);
		LOG.info("seasonDivision ID = " + SourceIdParser.parseSeasonDivisionId(id));
		LOG.info("fixtureDate string = " + fixutureDateString);
		CrnkSeasonDivision seasonDivision = seasonDivisionRepository.findOne(SourceIdParser.parseSeasonDivisionId(id), querySpec);
		LOG.info("seasonDivision = " + seasonDivision);
		LOG.info("count of fixture dates = " + seasonDivision.getFixtureDates().size());

		for (CrnkSeasonDivisionFixtureDate fixtureDate : seasonDivision.getFixtureDates()) {
			LOG.info("...fixt date found : " + fixtureDate.getFixtureDate());
			if (fixutureDateString.equals(fixtureDate.getFixtureDate())) {
				return fixtureDate;
			}
		}
		return null;
	}
}
