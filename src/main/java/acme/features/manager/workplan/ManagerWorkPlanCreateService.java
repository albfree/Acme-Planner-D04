/*
 * ManagerWorkPlanCreateService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.workplans.WorkPlan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;
import acme.utils.SpamChecker;

@Service
public class ManagerWorkPlanCreateService implements AbstractCreateService<Manager, WorkPlan> {

	@Autowired
	protected ManagerWorkPlanRepository repository;
	
	@Autowired
	private SpamChecker spamChecker;
	
	@Override
	public boolean authorise(final Request<WorkPlan> request) {
		assert request != null;
		
		return true;
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

		request.unbind(entity, model, "title", "startExecutionPeriod", "endExecutionPeriod", "share");
	}
	
	@Override
	public WorkPlan instantiate(final Request<WorkPlan> request) {
		assert request != null;
		
		WorkPlan result;
		Manager manager;
		
		result = new WorkPlan();
		manager = this.repository.findManagerById(request.getPrincipal().getActiveRoleId());
		
		result.setManager(manager);
		
		return result;
	}
	
	@Override
	public void validate(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("title")) {
			final String title = entity.getTitle();
			errors.state(request, !this.spamChecker.isSpamText(title), "title", "manager.work-plan.form.error.contains-spam");
		}
		
		if (!errors.hasErrors("startExecutionPeriod") && !errors.hasErrors("endExecutionPeriod")) {
			errors.state(request, entity.getEndExecutionPeriod().after(entity.getStartExecutionPeriod()), 
				"endExecutionPeriod", "manager.work-plan.form.error.period-invalid");
		}
	}
	
	@Override
	public void create(final Request<WorkPlan> request, final WorkPlan entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
}
