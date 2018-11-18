package mindbadger.football.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import io.crnk.core.exception.BadRequestException;
import io.crnk.core.exception.ResourceNotFoundException;
import mindbadger.football.api.repository.CrnkDivisionRepository;
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

	@Override
	public CrnkDivision findOne(String id, QuerySpec querySpec) {
		Division division = divisionRepository.findOne(id);
		if (division == null) {
			throw new ResourceNotFoundException("Division does not exist");
		}
		return new CrnkDivision(division);
	}

	@Override
	public ResourceList<CrnkDivision> findAll(Iterable<String> ids, QuerySpec querySpec) {
		return super.findAll(ids, querySpec);
	}

	@Override
	public <S extends CrnkDivision> S save(S resource) {
		Division division = divisionRepository.save(resource.getDivision());
		return (S) new CrnkDivision(division);
	}

	@Override
	public <S extends CrnkDivision> S create(S resource) {
		Division division = divisionRepository.findMatching(resource.getDivision());
		if (division != null) {
			throw new BadRequestException("Division already exists");
		}
		return save(resource);
	}

	@Override
	public void delete(String id) {
		Division division = divisionRepository.findOne(id);
		if (division == null) {
			throw new ResourceNotFoundException("Division does not exist");
		}
		divisionRepository.delete(division);
	}
}
