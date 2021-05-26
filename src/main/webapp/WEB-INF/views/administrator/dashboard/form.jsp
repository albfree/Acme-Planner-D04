<%--
- form.jsp
-
- Copyright (C) 2012-2021 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h2>
	<acme:message code="administrator.dashboard.form.title.show"/>
</h2>

<br/>
	
<table class="table table-sm">
	<caption>
		<acme:message code="administrator.dashboard.form.title.task-indicators"/>
	</caption>

	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfPublicTasks"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPublicTasks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfPrivateTasks"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPrivateTasks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfFinishedTasks"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfFinishedTasks}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfNonFinishedTasks"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfNonFinishedTasks}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.averageTaskExecutionPeriods"/>
		</th>
		<td>
			<acme:print value="${averageTaskExecutionPeriods}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviationTaskExecutionPeriods"/>
		</th>
		<td>
			<acme:print value="${deviationTaskExecutionPeriods}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimumTaskExecutionPeriod"/>
		</th>
		<td>
			<acme:print value="${minimumTaskExecutionPeriod}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximumTaskExecutionPeriod"/>
		</th>
		<td>
			<acme:print value="${maximumTaskExecutionPeriod}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.averageTaskWorkloads"/>
		</th>
		<td>
			<acme:print value="${averageTaskWorkloads}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviationTaskWorkloads"/>
		</th>
		<td>
			<acme:print value="${deviationTaskWorkloads}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimumTaskWorkload"/>
		</th>
		<td>
			<acme:print value="${minimumTaskWorkload}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximumTaskWorkload"/>
		</th>
		<td>
			<acme:print value="${maximumTaskWorkload}"/>
		</td>
	</tr>
	
	</table>
	
	<br/>
	
	<table class="table table-sm">
	<caption>
		<acme:message code="administrator.dashboard.form.title.workplan-indicators"/>
	</caption>
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfPublicWorkPlans"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPublicWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfPrivateWorkPlans"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPrivateWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfFinishedWorkPlans"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfFinishedWorkPlans}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.totalNumberOfNonFinishedWorkPlans"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfNonFinishedWorkPlans}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.averageWorkPlanExecutionPeriods"/>
		</th>
		<td>
			<acme:print value="${averageWorkPlanExecutionPeriods}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviationWorkPlanExecutionPeriods"/>
		</th>
		<td>
			<acme:print value="${deviationWorkPlanExecutionPeriods}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimumWorkPlanExecutionPeriod"/>
		</th>
		<td>
			<acme:print value="${minimumWorkPlanExecutionPeriod}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximumWorkPlanExecutionPeriod"/>
		</th>
		<td>
			<acme:print value="${maximumWorkPlanExecutionPeriod}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.averageWorkPlanTotalWorkloads"/>
		</th>
		<td>
			<acme:print value="${averageWorkPlanTotalWorkloads}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviationWorkPlanTotalWorkloads"/>
		</th>
		<td>
			<acme:print value="${deviationWorkPlanTotalWorkloads}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimumWorkPlanTotalWorkload"/>
		</th>
		<td>
			<acme:print value="${minimumWorkPlanTotalWorkload}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximumWorkPlanTotalWorkload"/>
		</th>
		<td>
			<acme:print value="${maximumWorkPlanTotalWorkload}"/>
		</td>
	</tr>
</table>

<br/>

<h2>
	<acme:message code="administrator.dashboard.form.title.chart"/>
</h2>

<br/>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"TOTAL", "PUBLISHED", "NOT PUBLISHED"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${totalNumberOfPublicWorkPlans} + ${totalNumberOfPrivateWorkPlans} "/>, 
						<jstl:out value="${totalNumberOfPublicWorkPlans}"/>, 
						<jstl:out value="${totalNumberOfPrivateWorkPlans}"/>
					],
					backgroundColor: [
						  'rgba(54, 162, 235, 0.2)',
					      'rgba(75, 192, 192, 0.2)',
					      'rgba(255, 99, 132, 0.2)',
					    ],
					    borderColor: [
					        'rgb(54, 162, 235)',
					        'rgb(75, 192, 192)',
					        'rgb(255, 99, 132)',
					      ],
					      borderWidth: 1
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>
