/*
 * ManagerWorkPlanDeleteService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.workplan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workplans.WorkPlan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class ManagerWorkPlanDeleteService implements AbstractDeleteService<Manager, WorkPlan> {

	@Autowired
	protected ManagerWorkPlanRepository repository;

	@Override
	public boolean authorise(final Request<WorkPlan> request) {
		assert request != null;
		
		boolean result;
		int workPlanId;
		WorkPlan workPlan;
		int managerId;

		workPlanId = request.getModel().getInteger("id");
		workPlan = this.repository.findWorkPlanById(workPlanId);
		managerId = request.getPrincipal().getActiveRoleId();
		
		result = workPlan.getManager().getId() == managerId;
		
		return result;
	}

	@Override
	public void bind(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startExecutionPeriod", "endExecutionPeriod", "share", "totalWorkload");
		
		model.setAttribute("wpID", entity.getId());
		
		model.setAttribute("hasTasks", !entity.getTasks().isEmpty());
		
		// Preparamos las variables para los desplegables de tareas

		final List<Task> tasksToAdd = (List<Task>) this.repository.findAllTasksByManager(request.getPrincipal().getActiveRoleId());
		final List<Task> tasksToDelete = (List<Task>) entity.getTasks();

		if (!tasksToDelete.isEmpty()) {
			model.setAttribute("tasksToDelete", tasksToDelete);

			if (!tasksToAdd.isEmpty()) {
				tasksToAdd.removeAll(tasksToDelete);
			}
		}

		model.setAttribute("hasAvailableTasks", !tasksToAdd.isEmpty());

		if (!tasksToAdd.isEmpty()) {
			model.setAttribute("tasksToAdd", tasksToAdd);
		}
	}

	@Override
	public WorkPlan findOne(final Request<WorkPlan> request) {
		assert request != null;

		WorkPlan result;
		int workPlanId;

		workPlanId = request.getModel().getInteger("id");
		result = this.repository.findWorkPlanById(workPlanId);

		return result;
	}

	@Override
	public void validate(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<WorkPlan> request, final WorkPlan entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
	}

}
