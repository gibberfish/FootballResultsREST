package mindbadger.football.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.model.CrnkDivision;
import mindbadger.football.domain.Division;
import mindbadger.football.repository.DivisionRepository;

@Component
public class CrnkDivisionRepositoryImpl extends ResourceRepositoryBase<CrnkDivision, String>
	implements CrnkDivisionRepository {
	
	protected CrnkDivisionRepositoryImpl() {
		super(CrnkDivision.class);
	}

	@Autowired
	private DivisionRepository divisionRepository;

	@Override
	public synchronized ResourceList<CrnkDivision> findAll(QuerySpec querySpec) {
		Iterable<Division> divisions = divisionRepository.findAll();
		
    	List<CrnkDivision> katharsisDivisions = new ArrayList<CrnkDivision> ();
    	for (Division division : divisions) {
    		katharsisDivisions.add(new CrnkDivision(division));
    	}
		
		return querySpec.apply(katharsisDivisions);
	}
}
