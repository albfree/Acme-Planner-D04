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
	<acme:form-textbox code="authenticated.task.form.label.title" path="title"/>
	<acme:form-moment code="authenticated.task.form.label.startExecutionPeriod" path="startExecutionPeriod"/>
	<acme:form-moment code="authenticated.task.form.label.endExecutionPeriod" path="endExecutionPeriod"/>
	<acme:form-double code="authenticated.task.form.label.workload" path="workload"/>
	<acme:form-textarea code="authenticated.task.form.label.description" path="description"/>
	<acme:form-select code="authenticated.task.form.label.share" path="share">
		<acme:form-option code="PUBLIC" value="PUBLIC" selected="${share == 'PUBLIC'}"/>
		<acme:form-option code="PRIVATE" value="PRIVATE" selected="${share == 'PRIVATE'}"/>
	</acme:form-select>
	<acme:form-url code="authenticated.task.form.label.link" path="link"/>
	
  	<acme:form-return code="authenticated.task.form.button.return"/>
</acme:form>