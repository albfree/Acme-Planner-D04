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

<h3>
	<acme:message code="administrator.customisation.form.title.show"/>
</h3>

<br/>

<acme:form>
	<acme:form-textarea code="administrator.customisation.form.label.words" path="spamWords"/>
	<acme:form-double code="administrator.customisation.form.label.threshold" path="threshold"/>

	<acme:form-submit code="administrator.customisation.form.button.update" action="/administrator/customisation/update"/>
</acme:form>