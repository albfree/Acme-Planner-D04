/*
 * AnonymousTaskListService.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.entities.tasks.TaskShare;
import acme.entities.workplans.WorkPlan;
import acme.entities.workplans.WorkPlanShare;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractListService;

@Service
public class AnonymousTaskByWorkPlanListService implements AbstractListService<Anonymous, Task> {

	@Autowired
	protected AnonymousTaskRepository repository;

	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		
		boolean result;
		int workplanId;
		WorkPlan workplan;

		workplanId = request.getModel().getInteger("id");
		workplan = this.repository.findWorkPlanById(workplanId);
		result = workplan.getShare().equals(WorkPlanShare.PUBLIC) && workplan.getEndExecutionPeriod().after(new Date());

		return result;
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startExecutionPeriod", "endExecutionPeriod", "workload");
	}

	@Override
	public Collection<Task> findMany(final Request<Task> request) {
		assert request != null;

		Collection<Task> result;
		final int wpID = request.getModel().getInteger("id");
		final Date today = new Date();

		result = this.repository.findTasksByWorkPlanId(wpID);
		
		final List<Task> remove = new ArrayList<Task>();
		
		for(final Task t : result) {
			if(t.getShare() == TaskShare.PRIVATE || t.getEndExecutionPeriod().before(today)) {
				remove.add(t);
			}
		}
		
		result.removeAll(remove);

		return result;
	}

}
