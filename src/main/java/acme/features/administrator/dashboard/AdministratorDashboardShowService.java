/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;
import acme.utils.StandardDeviation;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	@Autowired
	protected AdministratorDashboardRepository repository;

	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "totalNumberOfPublicTasks", "totalNumberOfPrivateTasks", "totalNumberOfFinishedTasks",
			"totalNumberOfNonFinishedTasks", "averageTaskExecutionPeriods", "deviationTaskExecutionPeriods", "minimumTaskExecutionPeriod",
			"maximumTaskExecutionPeriod", "averageTaskWorkloads", "deviationTaskWorkloads", "minimumTaskWorkload", "maximumTaskWorkload",
			"totalNumberOfPublicWorkPlans", "totalNumberOfPrivateWorkPlans", "totalNumberOfFinishedWorkPlans",
			"totalNumberOfNonFinishedWorkPlans", "averageWorkPlanExecutionPeriods", "deviationWorkPlanExecutionPeriods", "minimumWorkPlanExecutionPeriod",
			"maximumWorkPlanExecutionPeriod", "averageWorkPlanTotalWorkloads", "deviationWorkPlanTotalWorkloads", "minimumWorkPlanTotalWorkload",
			"maximumWorkPlanTotalWorkload");
	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;
		
		Dashboard result;
		result = new Dashboard();

		//Metodos de Task
		
		final Integer totalNumberOfPublicTasks = this.repository.totalNumberOfPublicTasks() != null ? this.repository.totalNumberOfPublicTasks() : 0;
		result.setTotalNumberOfPublicTasks(totalNumberOfPublicTasks);

		final Integer totalNumberOfPrivateTasks = this.repository.totalNumberOfPrivateTasks() != null ? this.repository.totalNumberOfPrivateTasks() : 0;
		result.setTotalNumberOfPrivateTasks(totalNumberOfPrivateTasks);

		final Integer totalNumberOfFinishedTasks = this.repository.totalNumberOfFinishedTasks() != null ? this.repository.totalNumberOfFinishedTasks() : 0;
		result.setTotalNumberOfFinishedTasks(totalNumberOfFinishedTasks);
		
		final Integer totalNumberOfNonFinishedTasks = this.repository.totalNumberOfNonFinishedTasks() != null ? this.repository.totalNumberOfNonFinishedTasks() : 0;
		result.setTotalNumberOfNonFinishedTasks(totalNumberOfNonFinishedTasks);
		
		final Double averageTaskExecutionPeriods = this.repository.averageTaskExecutionPeriods() != null ? this.repository.averageTaskExecutionPeriods() : 0.;
		result.setAverageTaskExecutionPeriods(averageTaskExecutionPeriods);

		final Double deviationTaskExecutionPeriods = this.repository.deviationTaskExecutionPeriods() != null ? this.repository.deviationTaskExecutionPeriods() : 0.;
		result.setDeviationTaskExecutionPeriods(deviationTaskExecutionPeriods);

		final Double minimumTaskExecutionPeriod = this.repository.minimumTaskExecutionPeriod() != null ? this.repository.minimumTaskExecutionPeriod() : 0.;
		result.setMinimumTaskExecutionPeriod(minimumTaskExecutionPeriod);
		
		final Double maximumTaskExecutionPeriod = this.repository.maximumTaskExecutionPeriod() != null ? this.repository.maximumTaskExecutionPeriod() : 0.;
		result.setMaximumTaskExecutionPeriod(maximumTaskExecutionPeriod);
		
		final Double averageTaskWorkloads = this.repository.averageTaskWorkloads() != null ? this.repository.averageTaskWorkloads() : 0.;
		result.setAverageTaskWorkloads(averageTaskWorkloads);

		final Double deviationTaskWorkloads = this.repository.deviationTaskWorkloads() != null ? this.repository.deviationTaskWorkloads() : 0.;
		result.setDeviationTaskWorkloads(deviationTaskWorkloads);

		final Double minimumTaskWorkload = this.repository.minimumTaskWorkload() != null ? this.repository.minimumTaskWorkload() : 0.;
		result.setMinimumTaskWorkload(minimumTaskWorkload);
		
		final Double maximumTaskWorkload = this.repository.maximumTaskWorkload() != null ? this.repository.maximumTaskWorkload() : 0.;
		result.setMaximumTaskWorkload(maximumTaskWorkload);

		//Metodos de WorkPlan
		
		final Integer totalNumberOfPublicWorkPlans = this.repository.totalNumberOfPublicWorkPlans() != null ? this.repository.totalNumberOfPublicWorkPlans() : 0;
		result.setTotalNumberOfPublicWorkPlans(totalNumberOfPublicWorkPlans);
		
		final Integer totalNumberOfPrivateWorkPlans = this.repository.totalNumberOfPrivateWorkPlans() != null ? this.repository.totalNumberOfPrivateWorkPlans() : 0;
		result.setTotalNumberOfPrivateWorkPlans(totalNumberOfPrivateWorkPlans);

		final Integer totalNumberOfFinishedWorkPlans = this.repository.totalNumberOfFinishedWorkPlans() != null ? this.repository.totalNumberOfFinishedWorkPlans() : 0;
		result.setTotalNumberOfFinishedWorkPlans(totalNumberOfFinishedWorkPlans);
		
		final Integer totalNumberOfNonFinishedWorkPlans = this.repository.totalNumberOfNonFinishedWorkPlans() != null ? this.repository.totalNumberOfNonFinishedWorkPlans() : 0;
		result.setTotalNumberOfNonFinishedWorkPlans(totalNumberOfNonFinishedWorkPlans);
		
		final Double averageWorkPlanExecutionPeriods = this.repository.averageWorkPlanExecutionPeriods() != null ? this.repository.averageWorkPlanExecutionPeriods() : 0.;
		result.setAverageWorkPlanExecutionPeriods(averageWorkPlanExecutionPeriods);

		final Double deviationWorkPlanExecutionPeriods = this.repository.deviationWorkPlanExecutionPeriods() != null ? this.repository.deviationWorkPlanExecutionPeriods() : 0.;
		result.setDeviationWorkPlanExecutionPeriods(deviationWorkPlanExecutionPeriods);
		
		final Double minimumWorkPlanExecutionPeriod = this.repository.minimumWorkPlanExecutionPeriod() != null ? this.repository.minimumWorkPlanExecutionPeriod() : 0.;
		result.setMinimumWorkPlanExecutionPeriod(minimumWorkPlanExecutionPeriod);
		
		final Double maximumWorkPlanExecutionPeriod = this.repository.maximumWorkPlanExecutionPeriod() != null ? this.repository.maximumWorkPlanExecutionPeriod() : 0.;
		result.setMaximumWorkPlanExecutionPeriod(maximumWorkPlanExecutionPeriod);
		
		final Double averageWorkPlanTotalWorkloads = this.repository.averageWorkPlanTotalWorkloads() != null ? this.repository.averageWorkPlanTotalWorkloads() : 0.;
		result.setAverageWorkPlanTotalWorkloads(averageWorkPlanTotalWorkloads);
		
		final Double deviationWorkPlanTotalWorkloads = this.repository.deviationWorkPlanTotalWorkloads().size() >= 2 ? StandardDeviation.calculateSD(this.repository.deviationWorkPlanTotalWorkloads()) : 0.;
		result.setDeviationWorkPlanTotalWorkloads(deviationWorkPlanTotalWorkloads);
		
		final Double minimumWorkPlanTotalWorkload = this.repository.minimumWorkPlanTotalWorkload() != null ? this.repository.minimumWorkPlanTotalWorkload() : 0.;
		result.setMinimumWorkPlanTotalWorkload(minimumWorkPlanTotalWorkload);
		
		final Double maximumWorkPlanTotalWorkload = this.repository.maximumWorkPlanTotalWorkload() != null ? this.repository.maximumWorkPlanTotalWorkload() : 0.;
		result.setMaximumWorkPlanTotalWorkload(maximumWorkPlanTotalWorkload);
		
		return result;
	}

}
