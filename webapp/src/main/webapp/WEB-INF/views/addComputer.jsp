<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section id="main">

	<h1>
		<spring:message code="addComputer" text="default text" />
	</h1>
	<c:if test="${error==true}">
		<c:out value="${errorName}"></c:out>
		<br />
		<c:out value="${errorIntroduced}"></c:out>
		<br />
		<c:out value="${errorDiscontinued}"></c:out>
		<br />
	</c:if>

	<form:form modelAttribute="cDTO" action="/ProjetWeb/add" method="POST">
		<fieldset>
			<div class="clearfix">
				<form:label path="name">
					<spring:message code="name" text="default text" />:</form:label>
				<div class="input">
					<form:input type="text" path="name" data-validation="required" />
					<span class="help-inline"><spring:message code="required"
							text="default text" /></span>
					<form:errors path="name" cssClass="errorMessage" />
				</div>
			</div>
			<div class="clearfix">
				<form:label path="introduced">
					<spring:message code="introduced" text="default text" />:</form:label>
				<div class="input">
					<form:input type="date" path="introduced" data-validation="date"
						data-validation-optional="true"
						data-validation-format="yyyy-mm-dd" />
					<span class="help-inline">YYYY-MM-dd</span>
					<form:errors path="introduced" cssClass="errorMessage" />
				</div>
			</div>
			<div class="clearfix">
				<form:label path="discontinued">
					<spring:message code="discontinued" text="default text" />:</form:label>
				<div class="input">
					<form:input type="date" path="discontinued" data-validation="date"
						data-validation-optional="true"
						data-validation-format="yyyy-mm-dd" />
					<span class="help-inline">YYYY-MM-dd</span>
					<form:errors path="discontinued" cssClass="errorMessage" />
				</div>
			</div>
			<div class="clearfix">
				<form:label path="companyName">
					<spring:message code="company" text="default text" />:</form:label>
				<div class="input">
					<form:select path="companyId">
						<form:option value="0">--</form:option>
						<form:options items="${dtoWrapper.listCompaniesDTO}" itemValue="id"
							itemLabel="name" />
					</form:select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit"
				value="<spring:message
				code="add" text="default text" />"
				class="btn primary">
			<spring:message code="or" text="default text" />
			<a href="index.jsp" class="btn"><spring:message code="cancel"
					text="default text" /></a>
		</div>
	</form:form>
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>
	<script>
		$.validate();
	</script>
</section>
<jsp:include page="include/footer.jsp" />