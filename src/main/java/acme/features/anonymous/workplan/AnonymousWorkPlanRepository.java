/*
 * AnonymousTaskRepository.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.anonymous.workplan;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.workplans.WorkPlan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousWorkPlanRepository extends AbstractRepository {

	@Query("select wp from WorkPlan wp where wp.endExecutionPeriod > current_timestamp() and wp.share = acme.entities.workplans.WorkPlanShare.PUBLIC")
	Collection<WorkPlan> findPublicAndNonFinishedWorkPlans();
	
	@Query("select wp from WorkPlan wp where wp.id = ?1")
	WorkPlan findWorkPlanById(int id);

}
