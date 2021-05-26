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

package acme.features.anonymous.task;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tasks.Task;
import acme.entities.workplans.WorkPlan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousTaskRepository extends AbstractRepository {

	@Query("select t from Task t where t.endExecutionPeriod > current_timestamp() and t.share = acme.entities.tasks.TaskShare.PUBLIC")
	Collection<Task> findPublicAndNonFinishedTasks();
	
	@Query("select t from Task t where t.id = ?1")
	Task findTaskById(int id);
	
	@Query("select wp.tasks from WorkPlan wp where wp.id = ?1")
	Collection<Task> findTasksByWorkPlanId(int id);
	
	@Query("select wp from WorkPlan wp where wp.id = ?1")
	WorkPlan findWorkPlanById(int id);

}
