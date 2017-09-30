package mindbadger.football.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkSeason;
import mindbadger.football.api.model.CrnkSeasonDivision;

@Component
public class CrnkSeasonDivisionRepositoryImpl extends ResourceRepositoryBase<CrnkSeasonDivision, String>
	implements CrnkSeasonDivisionRepository {

	@Autowired
	private CrnkSeasonRepository seasonRepository;
	
	protected CrnkSeasonDivisionRepositoryImpl() {
		super(CrnkSeasonDivision.class);
	}

	@Override
	public synchronized ResourceList<CrnkSeasonDivision> findAll(QuerySpec querySpec) {
		throw new RuntimeException();
	}

	@Override
	public ResourceList<CrnkSeasonDivision> findAll(Iterable<String> ids, QuerySpec querySpec) {
		throw new RuntimeException();
	}

	@Override
	public CrnkSeasonDivision findOne(String id, QuerySpec querySpec) {
		CrnkSeason season = seasonRepository.findOne(parseSeasonIdFromSourceId(id), querySpec);
		for (CrnkSeasonDivision seasonDivision : season.getSeasonDivisions()) {
			if (id.equals(seasonDivision.getId())) {
				return seasonDivision;
			}
		}
		return null;
	}
	
    private Integer parseSeasonIdFromSourceId (String id) {
    	String[] idSplit = id.split("-");
    	return Integer.parseInt(idSplit[0]);
    }
}
