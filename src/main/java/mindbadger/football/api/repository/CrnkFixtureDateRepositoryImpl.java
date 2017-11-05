package mindbadger.football.api.repository;

import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkFixtureDate;

@Component
public class CrnkFixtureDateRepositoryImpl extends ResourceRepositoryBase<CrnkFixtureDate, String>
	implements CrnkFixtureDateRepository {
	
	protected CrnkFixtureDateRepositoryImpl() {
		super(CrnkFixtureDate.class);
	}

	@Override
	public synchronized ResourceList<CrnkFixtureDate> findAll(QuerySpec querySpec) {
		throw new RuntimeException();
	}

	@Override
	public ResourceList<CrnkFixtureDate> findAll(Iterable<String> ids, QuerySpec querySpec) {
		throw new RuntimeException();
	}

	@Override
	public CrnkFixtureDate findOne(String id, QuerySpec querySpec) {
		return new CrnkFixtureDate(null, "0");
	}
}
