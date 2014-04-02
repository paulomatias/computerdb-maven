<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<section id="main">

	<h1>Add a Computer</h1>
	<c:if test="${error==true}">
		<c:out value="${errorName}"></c:out>
		<br />
		<c:out value="${errorIntroduced}"></c:out>
		<br />
		<c:out value="${errorDiscontinued}"></c:out>
		<br />
	</c:if>

	<form:form modelAttribute="cDTO" action="/ProjetWebExcilysMaven/adding"
		method="POST">
		<fieldset>
			<div class="clearfix">
				<form:label path="name">Computer name:</form:label>
				<div class="input">
					<form:input type="text" path="name" data-validation="required" />
					<span class="help-inline">Required</span>
					<form:errors path="name" cssClass="errorMessage" />
				</div>
			</div>
			<div class="clearfix">
				<form:label path="introduced">Introduced date:</form:label>
				<div class="input">
					<form:input type="date" path="introduced" data-validation="date"
						data-validation-optional="true"
						data-validation-format="yyyy-mm-dd" />
					<span class="help-inline">YYYY-MM-dd</span>
					<form:errors path="introduced" cssClass="errorMessage" />
				</div>
			</div>
			<div class="clearfix">
				<form:label path="discontinued">Discontinued date:</form:label>
				<div class="input">
					<form:input type="date" path="discontinued" data-validation="date"
						data-validation-optional="true"
						data-validation-format="yyyy-mm-dd" />
					<span class="help-inline">YYYY-MM-dd</span>
					<form:errors path="discontinued" cssClass="errorMessage" />
				</div>
			</div>
			<div class="clearfix">
				<form:label path="companyName">Company Name:</form:label>
				<div class="input">
					<form:select path="companyId">
						<form:option value="0">--</form:option>
						<c:forEach items="${wrapper.listCompaniesDTO}" var="var">
							<form:option value="${var.id}">${var.name}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value="Add" class="btn primary"> or <a
				href="index.jsp" class="btn">Cancel</a>
		</div>
	</form:form>
	<!-- 	<script -->
	<!-- 		src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script> -->
	<!-- 	<script -->
	<!-- 		src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script> -->
	<!-- 	<script> -->
	<!-- 		$.validate(); -->
	<!-- 	</script> -->
</section>
<jsp:include page="include/footer.jsp" />