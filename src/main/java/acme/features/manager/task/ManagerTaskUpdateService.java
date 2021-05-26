/*
 * ManagerTaskUpdateService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.task;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.tasks.TaskShare;
import acme.entities.workplans.WorkPlan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;
import acme.utils.SpamChecker;

@Service
public class ManagerTaskUpdateService implements AbstractUpdateService<Manager, Task> {

	@Autowired
	protected ManagerTaskRepository repository;
	
	@Autowired
	private SpamChecker spamChecker;
	
	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		
		boolean result;
		int taskId;
		Task task;
		int managerId;

		taskId = request.getModel().getInteger("id");
		task = this.repository.findTaskById(taskId);
		managerId = request.getPrincipal().getActiveRoleId();
		result = task.getManager().getId() == managerId;
		
		return result;
	}
	
	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}
	
	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startExecutionPeriod", "endExecutionPeriod", "workload", "description", "share", "link");
	}
	
	@Override
	public Task findOne(final Request<Task> request) {
		assert request != null;

		Task result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findTaskById(id);

		return result;
	}
	
	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("workload")) {
			final Double workload = entity.getWorkload();
			final String workloadString = String.valueOf(workload);
			
			if (workloadString.contains(".")) {
				String minutesString = workloadString.substring(workloadString.indexOf(".") + 1);
				
				if (!minutesString.equals("0")) {
					
					if (minutesString.length() < 2) {
						minutesString = minutesString + "0";
					}
					
					errors.state(request, Integer.parseInt(minutesString) < 60, "workload", "manager.task.form.error.workload-minutes-exceeded");
				}
			}
			
			if (!errors.hasErrors("startExecutionPeriod") && !errors.hasErrors("endExecutionPeriod")) {
				final Double maxWorkload = entity.maxWorkload();
				errors.state(request, workload <= maxWorkload, "workload", "manager.task.form.error.max-workload-exceeded");
			}
		}
		
		if (!errors.hasErrors("startExecutionPeriod") && !errors.hasErrors("endExecutionPeriod")) {
			final Date startExecutionPeriod = entity.getStartExecutionPeriod();
			final Date endExecutionPeriod = entity.getEndExecutionPeriod();
			errors.state(request, endExecutionPeriod.after(startExecutionPeriod), "endExecutionPeriod", "manager.task.form.error.period-invalid");
		}
		
		if (!errors.hasErrors("title")) {
			final String title = entity.getTitle();
			errors.state(request, !this.spamChecker.isSpamText(title), "title", "manager.task.form.error.contains-spam");
		}
		
		if (!errors.hasErrors("description")) {
			final String description = entity.getDescription();
			errors.state(request, !this.spamChecker.isSpamText(description), "description", "manager.task.form.error.contains-spam");
		}
		
		if (entity.getShare().equals(TaskShare.PRIVATE)) {
			final Collection<WorkPlan> workPlans = this.repository.findAllPublicWorkPlans();
			
			if (!workPlans.isEmpty()) {
				for (final WorkPlan wp: workPlans) {
					if (wp.getTasks().contains(entity)) {
						errors.state(request, false, "title", "manager.task.form.error.task.in.public.workplan");
						break;
					}
				}
			}
		}
	}
	
	@Override
	public void update(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
}
