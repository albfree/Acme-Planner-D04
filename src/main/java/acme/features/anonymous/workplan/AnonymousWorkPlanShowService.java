/*
 * AnonoymousWorkPlanShowService.java
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

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.workplans.WorkPlan;
import acme.entities.workplans.WorkPlanShare;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractShowService;

@Service
public class AnonymousWorkPlanShowService implements AbstractShowService<Anonymous, WorkPlan> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnonymousWorkPlanRepository repository;

	// AbstractShowService<Anonymous, WorkPlan> interface -----------------------------


	@Override
	public boolean authorise(final Request<WorkPlan> request) {
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
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startExecutionPeriod", "endExecutionPeriod", "totalWorkload", "share");
		
		final int wpID = entity.getId();

		model.setAttribute("wpID", wpID);
	}

	@Override
	public WorkPlan findOne(final Request<WorkPlan> request) {
		assert request != null;

		WorkPlan result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findWorkPlanById(id);

		return result;
	}

}
