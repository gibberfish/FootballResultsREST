package mindbadger.football.api.repository;

import java.util.ArrayList;
import java.util.List;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import mindbadger.football.api.model.KatharsisDivision;
import mindbadger.football.domain.Division;
import mindbadger.football.repository.DivisionRepository;

@Component
public class KatharsisDivisionRepositoryImpl extends ResourceRepositoryBase<KatharsisDivision, String>
	implements KatharsisDivisionRepository {
	
	protected KatharsisDivisionRepositoryImpl() {
		super(KatharsisDivision.class);
	}

	@Autowired
	private DivisionRepository divisionRepository;

	@Override
	public synchronized ResourceList<KatharsisDivision> findAll(QuerySpec querySpec) {
		Iterable<Division> divisions = divisionRepository.findAll();
		
    	List<KatharsisDivision> katharsisDivisions = new ArrayList<KatharsisDivision> ();
    	for (Division division : divisions) {
    		katharsisDivisions.add(new KatharsisDivision(division));
    	}
		
		
		return querySpec.apply(katharsisDivisions);
	}
}
