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

<acme:form>
	<jstl:if test="${hasTasks}">
	<acme:message code="${suggestedStartDate}"/>
	<acme:menu-separator/>
	<acme:message code="${suggestedEndDate}"/>
	<acme:menu-separator/>
	</jstl:if>
	
	<acme:form-hidden path="wpID"/>
	
	<acme:form-textbox code="manager.work-plan.form.label.title" path="title"/>
	
	<acme:form-moment code="manager.work-plan.form.label.startExecutionPeriod" path="startExecutionPeriod"/>
	<acme:form-moment code="manager.work-plan.form.label.endExecutionPeriod" path="endExecutionPeriod"/>
	
	<acme:form-select code="manager.work-plan.form.label.share" path="share">
		<acme:form-option code="PUBLIC" value="PUBLIC" selected="${share == 'PUBLIC'}"/>
		<acme:form-option code="PRIVATE" value="PRIVATE" selected="${share == 'PRIVATE'}"/>
	</acme:form-select>
	
	<jstl:if test="${command != 'create'}">
	<acme:form-double readonly="true" code="manager.work-plan.form.label.total-workload" path="totalWorkload"/>
	</jstl:if>
	
	<jstl:if test="${command != 'create' and hasAvailableTasks}">
		<acme:form-select code="manager.work-plan.form.label.task.add" path="addTask">
			<acme:form-option code="Seleccionar" value="-1" selected="true"/>
			<jstl:forEach items="${tasksToAdd}" var="task">
				<acme:form-option code="${task.id} - ${task.title}" value="${task.id}" selected="${addTask == task}"/>
			</jstl:forEach>
		</acme:form-select>
	</jstl:if>
	
	<jstl:if test="${command != 'create' and hasTasks}">
		<acme:form-select code="manager.work-plan.form.label.task.delete" path="deleteTask">
			<acme:form-option code="Seleccionar" value="-1" selected="true"/>
			<jstl:forEach items="${tasksToDelete}" var="task">
				<acme:form-option code="${task.id} - ${task.title}" value="${task.id}" selected="${deleteTask == task}"/>
			</jstl:forEach>
		</acme:form-select>
	</jstl:if>
	
	<acme:form-submit test="${command != 'create'}" method="get" code="manager.work-plan.form.button.task.list" 
		action="/manager/task/list_by?id=${wpID}"/>
	
	<acme:menu-separator/>
	
	<acme:form-submit test="${command == 'create'}" code="manager.work-plan.form.button.create" 
		action="/manager/work-plan/create"/>
	<acme:form-submit test="${command != 'create'}" code="manager.work-plan.form.button.update" 
		action="/manager/work-plan/update"/>
	<acme:form-submit test="${command != 'create'}" code="manager.work-plan.form.button.delete" 
		action="/manager/work-plan/delete"/>
		
	<acme:form-return code="manager.work-plan.form.button.return"/>
	
</acme:form>