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

<acme:form readonly="true">
	<acme:form-textbox code="anonymous.workplan.form.label.title" path="title"/>
	<acme:form-moment code="anonymous.workplan.form.label.startExecutionPeriod" path="startExecutionPeriod"/>
	<acme:form-moment code="anonymous.workplan.form.label.endExecutionPeriod" path="endExecutionPeriod"/>
	<acme:form-double code="anonymous.workplan.form.label.totalWorkload" path="totalWorkload"/>
	<acme:form-select code="anonymous.workplan.form.label.share" path="share">
		<acme:form-option code="PUBLIC" value="PUBLIC" selected="${share == 'PUBLIC'}"/>
		<acme:form-option code="PRIVATE" value="PRIVATE" selected="${share == 'PRIVATE'}"/>
	</acme:form-select>
	
	<acme:form-submit method="get" code="anonymous.workplan.form.button.tasks"
		action="/anonymous/task/list_by?id=${wpID}" />
	
  	<acme:form-return code="anonymous.workplan.form.button.return"/>
</acme:form>
