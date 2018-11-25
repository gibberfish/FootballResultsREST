package mindbadger.football.api.repository.impl;

import io.crnk.core.engine.registry.ResourceRegistry;
import io.crnk.core.exception.BadRequestException;
import io.crnk.core.exception.ResourceNotFoundException;
import io.crnk.core.queryspec.FilterSpec;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import mindbadger.football.api.ConflictException;
import mindbadger.football.api.model.CrnkSeasonDivisionTeam;
import mindbadger.football.api.repository.CrnkSeasonDivisionTeamRepository;
import mindbadger.football.api.util.SourceIdUtils;
import mindbadger.football.domain.*;
import mindbadger.football.repository.DivisionRepository;
import mindbadger.football.repository.SeasonRepository;
import mindbadger.football.repository.TeamRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CrnkSeasonDivisionTeamRepositoryImpl extends ResourceRepositoryBase<CrnkSeasonDivisionTeam, String>
	implements CrnkSeasonDivisionTeamRepository {
	Logger LOG = Logger.getLogger (CrnkSeasonDivisionTeamRepositoryImpl.class);

	@Autowired
	private SeasonRepository seasonRepository;

	@Autowired
	private DivisionRepository divisionRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private DomainObjectFactory domainObjectFactory;

	protected CrnkSeasonDivisionTeamRepositoryImpl() {
		super(CrnkSeasonDivisionTeam.class);
	}

	@Override
	public synchronized ResourceList<CrnkSeasonDivisionTeam> findAll(QuerySpec querySpec) {
		LOG.debug("*********************** CrnkSeasonDivisionTeamRepositoryImpl.findAll");

		List<CrnkSeasonDivisionTeam> katharsisSeasonDivisionTeams = new ArrayList<>();

		for (FilterSpec filter : querySpec.getFilters()) {
			LOG.debug("   filter: " + filter);
			LOG.debug("      expression: " + filter.getExpression());
			LOG.debug("      operator  : " + filter.getOperator());
			LOG.debug("      value     : " + filter.getValue());
			LOG.debug("      attribute : " + filter.getAttributePath().get(0));

			String value = filter.getValue().toString();
			value = value.replace("[", "");
			value = value.replace("]", "");

			if (filter.getAttributePath().size() > 0 && "id".equals(filter.getAttributePath().get(0))) {
				LOG.debug("  findAll filtering on id.");
				katharsisSeasonDivisionTeams.add(findOne(value, querySpec));
			}
		}

		return querySpec.apply(katharsisSeasonDivisionTeams);
	}

	@Override
	public ResourceList<CrnkSeasonDivisionTeam> findAll(Iterable<String> ids, QuerySpec querySpec) {
		LOG.debug("*********************** CrnkSeasonDivisionTeamRepositoryImpl.findAll with ids");
		return super.findAll(ids, querySpec);
	}

	@Override
	public CrnkSeasonDivisionTeam findOne(String id, QuerySpec querySpec) {
		LOG.debug("*********************** CrnkSeasonDivisionTeamRepositoryImpl.findOne");

		Integer seasonId = SourceIdUtils.parseSeasonId(id);
		String divisionId = SourceIdUtils.parseDivisionId(id);
		String teamId = SourceIdUtils.parseTeamId(id);

		Season existingSeason = seasonRepository.findOne(seasonId);

		if (existingSeason == null) {
			throw new ResourceNotFoundException("Season Division Team not found");
		}

		for (SeasonDivision seasonDivision : existingSeason.getSeasonDivisions()) {
			if (divisionId.equals(seasonDivision.getDivision().getDivisionId())) {

				for (SeasonDivisionTeam seasonDivisionTeam : seasonDivision.getSeasonDivisionTeams()) {
					if (teamId.equals(seasonDivisionTeam.getTeam().getTeamId())) {
						return new CrnkSeasonDivisionTeam(seasonDivisionTeam);
					}
				}

			}
		}

		throw new ResourceNotFoundException("Season Division Team not found");
	}

	protected CrnkSeasonDivisionTeamRepositoryImpl(Class<CrnkSeasonDivisionTeam> resourceClass) {
		super(resourceClass);
	}

	@Override
	public Class<CrnkSeasonDivisionTeam> getResourceClass() {
		LOG.debug("*********************** CrnkSeasonDivisionTeamRepositoryImpl.getResourceClass");
		return super.getResourceClass();
	}

	@Override
	public <S extends CrnkSeasonDivisionTeam> S save(S resource) {
		LOG.debug("*********************** CrnkSeasonDivisionTeamRepositoryImpl.save");
		Season existingSeason = seasonRepository.findOne(resource.getSeasonDivision().getSeason().getId());
		Division existingDivision = resource.getSeasonDivision().getDivision().getDivision();
		Team newTeamToAddToSeasonDivision = resource.getTeam().getTeam();

		if (existingSeason == null) {
			throw new BadRequestException("Season doesn't exist");
		}

		if (existingDivision == null) {
			throw new BadRequestException("Division doesn't exist");
		}

		if (newTeamToAddToSeasonDivision == null) {
			throw new BadRequestException("Team doesn't exist");
		}

		for (SeasonDivision seasonDivision : existingSeason.getSeasonDivisions()) {
			if (existingDivision.getDivisionId().equals(seasonDivision.getDivision().getDivisionId())) {
				SeasonDivisionTeam newSeasonDivisionTeam =
						domainObjectFactory.createSeasonDivisionTeam(seasonDivision, newTeamToAddToSeasonDivision);

				seasonDivision.getSeasonDivisionTeams().add(newSeasonDivisionTeam);

				seasonRepository.save(existingSeason);

				return resource;
			}
		}

		return null;
	}

	@Override
	public <S extends CrnkSeasonDivisionTeam> S create(S resource) {
		LOG.debug("*********************** CrnkSeasonDivisionTeamRepositoryImpl.create");

		if (resource.getSeasonDivision() == null) {
			throw new BadRequestException("No SeasonDivision supplied");
		}
		if (resource.getSeasonDivision().getSeason() == null) {
			throw new BadRequestException("No Season supplied");
		}
		if (resource.getSeasonDivision().getDivision() == null) {
			throw new BadRequestException("No Division supplied");
		}
		if (resource.getTeam() == null) {
			throw new BadRequestException("No Team supplied");
		}

		Season existingSeason = seasonRepository.findOne(resource.getSeasonDivision().getSeason().getId());
		Division existingDivision = divisionRepository.findOne(resource.getSeasonDivision().getDivision().getId());
		Team existingTeam = teamRepository.findOne(resource.getTeam().getId());

		if (existingSeason == null) {
			throw new BadRequestException("Season doesn't exist");
		}

		if (existingDivision == null) {
			throw new BadRequestException("Division doesn't exist");
		}

		if (existingTeam == null) {
			throw new BadRequestException("Team doesn't exist");
		}

		for (SeasonDivision seasonDivision : existingSeason.getSeasonDivisions()) {
			if (existingDivision.getDivisionId().equals(seasonDivision.getDivision().getDivisionId())) {
				for (SeasonDivisionTeam seasonDivisionTeam : seasonDivision.getSeasonDivisionTeams()) {
					if (existingTeam.getTeamId().equals(seasonDivisionTeam.getTeam().getTeamId())) {
						throw new ConflictException("Season Division Team already exists");
					}
				}
			}
		}

		return save(resource);
	}

	@Override
	public void delete(String id) {
		LOG.debug("*********************** CrnkSeasonDivisionTeamRepositoryImpl.delete");

		Integer seasonId = SourceIdUtils.parseSeasonId(id);
		String divisionId = SourceIdUtils.parseDivisionId(id);
		String teamId = SourceIdUtils.parseTeamId(id);

		LOG.debug("***********************    seasonId: " + seasonId + ", divisionId: " +
				divisionId + ", teamId: " + teamId);

		Season existingSeason = seasonRepository.findOne(seasonId);
		if (existingSeason == null) {
			throw new BadRequestException("Season doesn't exist");
		}

		boolean found = false;

		Iterator<SeasonDivision> seasonDivisionIterator = existingSeason.getSeasonDivisions().iterator();
		while (seasonDivisionIterator.hasNext()) {
			SeasonDivision seasonDivision = seasonDivisionIterator.next();

			if (divisionId.equals(seasonDivision.getDivision().getDivisionId())) {
				Iterator<SeasonDivisionTeam> seasonDivisionTeamIterator = seasonDivision.getSeasonDivisionTeams().iterator();

				while (seasonDivisionTeamIterator.hasNext()) {
					SeasonDivisionTeam seasonDivisionTeam = seasonDivisionTeamIterator.next();

					if (teamId.equals(seasonDivisionTeam.getTeam().getTeamId())) {
						found = true;
						seasonDivision.getSeasonDivisionTeams().remove(seasonDivisionTeam);
					}
				}
			}
		}

		if (!found) {
			throw new BadRequestException("SeasonDivisionTeam doesn't exist");
		}

		seasonRepository.save(existingSeason);
	}

	@Override
	public void setResourceRegistry(ResourceRegistry resourceRegistry) {
		LOG.debug("*********************** CrnkSeasonDivisionTeamRepositoryImpl.setResourceRegistry");
		super.setResourceRegistry(resourceRegistry);
	}

}
