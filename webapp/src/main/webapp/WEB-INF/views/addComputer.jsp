<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container-fluid">
	<h1 class="text-center">
		<spring:message code="addComputer" />
	</h1>

	<h4>
		<form:form modelAttribute="cDTO" action="/ProjetWeb/add" method="POST"
			class="form-horizontal" role="form">
			<fieldset>
				<div class="form-group">
					<form:label path="name" class="col-sm-2 control-label">
						<spring:message code="name" />:</form:label>
					<div class="col-sm-10">
						<form:input type="text" path="name" data-validation="required" />
						<span class="help-inline"><spring:message code="required" /></span>
						<form:errors path="name" cssClass="errorMessage" />
					</div>
				</div>
				<div class="form-group">
					<form:label path="introduced" class="col-sm-2 control-label">
						<spring:message code="introduced" />:</form:label>
					<div class="col-sm-10">
						<form:input type="date" path="introduced" data-validation="date"
							data-validation-optional="true"
							data-validation-format="yyyy-mm-dd" />
						<span class="help-inline">YYYY-MM-dd</span>
						<form:errors path="introduced" cssClass="errorMessage" />
					</div>
				</div>
				<div class="form-group">
					<form:label path="discontinued" class="col-sm-2 control-label">
						<spring:message code="discontinued" />:</form:label>
					<div class="col-sm-10">
						<form:input type="date" path="discontinued" data-validation="date"
							data-validation-optional="true"
							data-validation-format="yyyy-mm-dd" />
						<span class="help-inline">YYYY-MM-dd</span>
						<form:errors path="discontinued" cssClass="errorMessage" />
					</div>
				</div>
				<div class="form-group">
					<form:label path="companyName" class="col-sm-2 control-label">
						<spring:message code="company" />:</form:label>
					<div class="col-sm-10">
						<form:select path="companyId">
							<form:option value="0">--</form:option>
							<form:options items="${dtoWrapper.listCompaniesDTO}"
								itemValue="id" itemLabel="name" />
						</form:select>
					</div>
				</div>
			</fieldset>

			<div class="col-sm-offset-2 col-sm-10">
				<input type="submit"
					value="<spring:message
				code="add"  />"
					class="btn btn-success">
				<spring:message code="or" />
				<a href="index.jsp" class="btn btn-primary"><i class="fa fa-ban"></i> <spring:message
						code="cancel" /> </a>
			</div>
		</form:form>
	</h4>
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>
	<script>
		$.validate();
	</script>
</div>
<jsp:include page="include/footer.jsp" />