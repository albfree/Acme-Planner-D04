/*
 * AdministratorDashboardRepository.java
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

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	//Queries Task
	
	@Query("select count(t) from Task t where t.share = acme.entities.tasks.TaskShare.PUBLIC")
	Integer totalNumberOfPublicTasks();

	@Query("select count(t) from Task t where t.share = acme.entities.tasks.TaskShare.PRIVATE")
	Integer totalNumberOfPrivateTasks();

	@Query("select count(t) from Task t where t.endExecutionPeriod < current_timestamp()")
	Integer totalNumberOfFinishedTasks();
	
	@Query("select count(t) from Task t where t.endExecutionPeriod > current_timestamp()")
	Integer totalNumberOfNonFinishedTasks();
	
	@Query("select avg(datediff(t.endExecutionPeriod, t.startExecutionPeriod)) from Task t")
	Double averageTaskExecutionPeriods();

	@Query("select stddev(datediff(t.endExecutionPeriod, t.startExecutionPeriod)) from Task t")
	Double deviationTaskExecutionPeriods();

	@Query("select min(datediff(t.endExecutionPeriod, t.startExecutionPeriod)) from Task t")
	Double minimumTaskExecutionPeriod();
	
	@Query("select max(datediff(t.endExecutionPeriod, t.startExecutionPeriod)) from Task t")
	Double maximumTaskExecutionPeriod();
	
	@Query("select avg(t.workload) from Task t")
	Double averageTaskWorkloads();

	@Query("select stddev(t.workload) from Task t")
	Double deviationTaskWorkloads();

	@Query("select min(t.workload) from Task t")
	Double minimumTaskWorkload();
	
	@Query("select max(t.workload) from Task t")
	Double maximumTaskWorkload();
	
	//Queries WorkPlan

	@Query("select count(wp) from WorkPlan wp where wp.share = acme.entities.workplans.WorkPlanShare.PUBLIC")
	Integer totalNumberOfPublicWorkPlans();
	
	@Query("select count(wp) from WorkPlan wp where wp.share = acme.entities.workplans.WorkPlanShare.PRIVATE")
	Integer totalNumberOfPrivateWorkPlans();
	
	@Query("select count(wp) from WorkPlan wp where wp.endExecutionPeriod < current_timestamp()")
	Integer totalNumberOfFinishedWorkPlans();
	
	@Query("select count(wp) from WorkPlan wp where wp.endExecutionPeriod > current_timestamp()")
	Integer totalNumberOfNonFinishedWorkPlans();
	
	@Query("select avg(datediff(wp.endExecutionPeriod, wp.startExecutionPeriod)) from WorkPlan wp")
	Double averageWorkPlanExecutionPeriods();
	
	@Query("select stddev(datediff(wp.endExecutionPeriod, wp.startExecutionPeriod)) from WorkPlan wp")
	Double deviationWorkPlanExecutionPeriods();
	
	@Query("select min(datediff(wp.endExecutionPeriod, wp.startExecutionPeriod)) from WorkPlan wp")
	Double minimumWorkPlanExecutionPeriod();

	@Query("select max(datediff(wp.endExecutionPeriod, wp.startExecutionPeriod)) from WorkPlan wp")
	Double maximumWorkPlanExecutionPeriod();
	
	@Query("select avg (select sum (t.workload) from WorkPlan wp join wp.tasks t where wp = wp1) from WorkPlan wp1")
	Double averageWorkPlanTotalWorkloads();

	@Query("select (select sum (t.workload) from WorkPlan wp join wp.tasks t where wp = wp1) from WorkPlan wp1")
	List<Double> deviationWorkPlanTotalWorkloads();

	@Query("select min (select sum (t.workload) from WorkPlan wp join wp.tasks t where wp = wp1) from WorkPlan wp1")
	Double minimumWorkPlanTotalWorkload();
	
	@Query("select max (select sum (t.workload) from WorkPlan wp join wp.tasks t where wp = wp1) from WorkPlan wp1")
	Double maximumWorkPlanTotalWorkload();
	
}
