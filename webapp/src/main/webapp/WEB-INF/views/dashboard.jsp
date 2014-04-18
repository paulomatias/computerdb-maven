<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section id="main">
	<h1>
		<spring:message code="${wrapper.message}" text="default text" />
	</h1>
	<h2>
		<c:out value="${wrapper.nbrComputers}" />
		<spring:message code="nbrComputer" text="default text" />
	</h2>
	<div id="actions">
		<form action="/ProjetWeb/dashboard" method="GET">
			<input type="search" id="searchbox" value="" name="searchComputer"
				placeholder="<spring:message
				code="searchComputer" text="default text" />"> <input type="search"
				id="searchbox" value="" name="searchCompany"
				placeholder="<spring:message
				code="searchCompany" text="default text" />"> <input type="submit"
				id="searchbox" value="<spring:message
				code="search" text="default text" />" class="btn primary">
		</form>
		<a class="btn success" id="add" href="/ProjetWeb/addForm"><spring:message
				code="addComputer" text="default text" /> </a>
	</div>
	<p></p>
	<table class="computers zebra-striped">
		<thead>
			<c:if
				test="${wrapper.message=='welcome' or wrapper.message=='welcomeDelete' or wrapper.message=='welcomeEdit' or wrapper.message==welcomeAdd}">
				<tr>
					<th><a
						href="/ProjetWeb/dashboard?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}&orderBy=${ wrapper.orderBy =='nameASC' ? 'nameDESC' : 'nameASC'}"
						title="<spring:message
				code="orderByName" text="default text" />" ><spring:message
				code="name" text="default text" /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}&orderBy=${ wrapper.orderBy =='introducedASC' ? 'introducedDESC' : 'introducedASC'}"
						title="<spring:message
				code="orderByIntroduced" text="default text" />"><spring:message
				code="introduced" text="default text" /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}&orderBy=${ wrapper.orderBy =='discontinuedASC' ? 'discontinuedDESC' : 'discontinuedASC'}"
						title="<spring:message
				code="orderByDiscontinued" text="default text" />"><spring:message
				code="discontinued" text="default text" /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}&orderBy=${ wrapper.orderBy =='companyASC' ? 'companyDESC' : 'companyASC'}"
						title="<spring:message
				code="orderByCompany" text="default text" />"><spring:message
				code="company" text="default text" /></a></th>
					<th></th>
				</tr>
			</c:if>
			<c:if
				test="${wrapper.message=='welcomeSelect'}">
				<tr>
					<th><a
						href="/ProjetWeb/dashboard?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}${!empty wrapper.searchComputer ? '&searchComputer='.concat(wrapper.searchComputer) : ''}${!empty wrapper.searchCompany ? '&searchCompany='.concat(wrapper.searchCompany) : ''}&orderBy=${ wrapper.orderBy =='nameASC' ? 'nameDESC' : 'nameASC'}"
						title="<spring:message
				code="orderByName" text="default text" />"><c:out value="Computer Name" /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}${!empty wrapper.searchComputer ? '&searchComputer='.concat(wrapper.searchComputer) : ''}${!empty wrapper.searchCompany ? '&searchCompany='.concat(wrapper.searchCompany) : ''}&orderBy=${ wrapper.orderBy =='introducedASC' ? 'introducedDESC' : 'introducedASC'}"
						title="<spring:message
				code="orderByIntroduced" text="default text" />"><c:out
								value="Introduced Date" /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}${!empty wrapper.searchComputer ? '&searchComputer='.concat(wrapper.searchComputer) : ''}${!empty wrapper.searchCompany ? '&searchCompany='.concat(wrapper.searchCompany) : ''}&orderBy=${ wrapper.orderBy =='discontinuedASC' ? 'discontinuedDESC' : 'discontinuedASC'}"
						title="<spring:message
				code="orderByDiscontinued" text="default text" />"><c:out
								value="Discontinued Date" /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}${!empty wrapper.searchComputer ? '&searchComputer='.concat(wrapper.searchComputer) : ''}${!empty wrapper.searchCompany ? '&searchCompany='.concat(wrapper.searchCompany) : ''}&orderBy=${ wrapper.orderBy =='companyASC' ? 'companyDESC' : 'companyASC'}"
						title="<spring:message
				code="orderByCompany" text="default text" />"><c:out value="Company" /></a></th>
					<th></th>
				</tr>
			</c:if>
		</thead>
		<tbody>
			<c:forEach items="${wrapper.listComputersDTO}" var="var">
				<tr>
					<td><a href="/ProjetWeb/editForm?id=${var.id}"
						title="<spring:message
				code="editComputer" text="default text" />"><c:out value="${var.name}" /></a></td>
					<td><c:out value="${var.introduced}" /></td>
					<td><c:out value="${var.discontinued}" /></td>
					<td><c:out value="${var.companyName} " /></td>
					<td><a class="btn danger" id="delete"
						href="/ProjetWeb/delete?id=${var.id}${!empty wrapper.currentPage ? '&currentPage='.concat(wrapper.currentPage) : ''}"><spring:message
				code="deleteComputer" text="default text" /></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<tags:pagination />
</section>

<jsp:include page="include/footer.jsp" />