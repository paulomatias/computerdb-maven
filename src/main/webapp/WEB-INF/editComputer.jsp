<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section id="main">
	<h1 id="homeTitle">Edit the following computer !</h1>
	<c:if test="${error==true}">
		<c:out value="${errorName}"></c:out>
		<br />
		<c:out value="${errorIntroduced}"></c:out>
		<br />
		<c:out value="${errorDiscontinued}"></c:out>
		<br />
	</c:if>
	<form
		action="/ProjetWebExcilysMaven/editing?id=${wrapper.computerDTO.id}"
		method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" name="name" value="${wrapper.computerDTO.name}"
						data-validation="required" /> <span class="help-inline">Required</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" name="introduced" data-validation="date"
						data-validation-optional="true"
						data-validation-format="yyyy-mm-dd"
						value="${wrapper.computerDTO.introduced}" /> <span
						class="help-inline">YYYY-MM-dd</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" name="discontinued" data-validation="date"
						data-validation-optional="true"
						data-validation-format="yyyy-mm-dd"
						value="${wrapper.computerDTO.discontinued}" /> <span
						class="help-inline">YYYY-MM-dd</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company">
						<option value="0">--</option>
						<c:forEach items="${wrapper.listCompaniesDTO}" var="var">
							<c:choose>
								<c:when test="${var.id==wrapper.computerDTO.companyId}">
									<option value="${var.id}" selected="selected">${var.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${var.id}">${var.name}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value="Edit" class="btn primary"> or <a
				href="index.jsp" class="btn">Cancel</a>
		</div>
	</form>
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>
	<script>
		$.validate();
	</script>
</section>

<jsp:include page="include/footer.jsp" />
