package mindbadger.football.api.repository.impl;

import mindbadger.football.api.model.CrnkSeasonDivisionFixtureDate;
import mindbadger.football.api.repository.CrnkSeasonDivisionFixtureDateRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class CrnkSeasonDivisionFixtureDateRepositoryImpl extends ResourceRepositoryBase<CrnkSeasonDivisionFixtureDate, String>
	implements CrnkSeasonDivisionFixtureDateRepository {

	private static Logger LOG = Logger.getLogger(CrnkSeasonDivisionFixtureDateRepositoryImpl.class);

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
		LOG.info("findOne, id = " + id);
		throw new NotImplementedException();
	}
}
