package acme.forms;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard implements Serializable {
	
	private static final long	serialVersionUID	= 1L;

	Integer						totalNumberOfPublicTasks;
	Integer						totalNumberOfPrivateTasks;
	Integer						totalNumberOfFinishedTasks;
	Integer						totalNumberOfNonFinishedTasks;
	
	Double						averageTaskExecutionPeriods;
	Double						deviationTaskExecutionPeriods;
	Double						minimumTaskExecutionPeriod;
	Double						maximumTaskExecutionPeriod;
	
	Double						averageTaskWorkloads;
	Double						deviationTaskWorkloads;
	Double						minimumTaskWorkload;
	Double						maximumTaskWorkload;
	
	Integer						totalNumberOfPublicWorkPlans;
	Integer						totalNumberOfPrivateWorkPlans;
	Integer						totalNumberOfFinishedWorkPlans;
	Integer						totalNumberOfNonFinishedWorkPlans;
	
	Double						averageWorkPlanExecutionPeriods;
	Double						deviationWorkPlanExecutionPeriods;
	Double						minimumWorkPlanExecutionPeriod;
	Double						maximumWorkPlanExecutionPeriod;
	
	Double						averageWorkPlanTotalWorkloads;
	Double						deviationWorkPlanTotalWorkloads;
	Double						minimumWorkPlanTotalWorkload;
	Double						maximumWorkPlanTotalWorkload;

}
