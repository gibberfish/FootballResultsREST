package mindbadger.football.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;
import mindbadger.football.api.model.KatharsisDivision;
import mindbadger.football.domain.Division;
import mindbadger.football.repository.DivisionRepository;

@Component
@Validated
public class KatharsisDivisionRepository extends ResourceRepositoryBase<KatharsisDivision, String> {
	
	protected KatharsisDivisionRepository() {
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
