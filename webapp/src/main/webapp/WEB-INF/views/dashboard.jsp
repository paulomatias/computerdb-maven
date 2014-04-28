<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="container-fluid">
	<h1  class="text-center">
		<spring:message code="${pageWrapper.message}"  />
	</h1>
	<h2 class="text-center">
		<c:out value="${pageWrapper.nbrComputers}" />
		<spring:message code="nbrComputer"  />
	</h2>
	<div id="actions">
		<form action="/ProjetWeb/dashboard" method="GET">
			<input type="text" id="searchbox"
				value="${pageWrapper.searchComputer }" name="searchComputer"
				placeholder="<spring:message	code="searchComputer"  />">
			<input type="text" id="searchbox"
				value="${pageWrapper.searchCompany }" name="searchCompany"
				placeholder="<spring:message
				code="searchCompany" />"> <input
				type="submit" id="searchbox"
				value="<spring:message
				code="search" />" class="btn btn-primary">
		</form>
		<a id="add" href="/ProjetWeb/addForm"><button type="button"
				class="btn btn-success">
				<spring:message code="addComputer"  />
			</button> </a>
	</div>
	<p></p>
	<table class="table table-hover table-striped table-bordered">
		<thead>
			<c:if
				test="${pageWrapper.message=='welcome' or pageWrapper.message=='welcomeDelete' or pageWrapper.message=='welcomeEdit' or pageWrapper.message==welcomeAdd}">
				<tr>
					<th><a
						href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}&orderBy=${ pageWrapper.orderBy =='nameASC' ? 'nameDESC' : 'nameASC'}" class="lienCpt"
						title="<spring:message
				code="orderByName"  />"><spring:message
								code="name"  /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}&orderBy=${ pageWrapper.orderBy =='introducedASC' ? 'introducedDESC' : 'introducedASC'}" class="lienCpt"
						title="<spring:message
				code="orderByIntroduced"  />"><spring:message
								code="introduced"  /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}&orderBy=${ pageWrapper.orderBy =='discontinuedASC' ? 'discontinuedDESC' : 'discontinuedASC'}" class="lienCpt"
						title="<spring:message
				code="orderByDiscontinued"  />"><spring:message
								code="discontinued"  /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}&orderBy=${ pageWrapper.orderBy =='companyASC' ? 'companyDESC' : 'companyASC'}" class="lienCpt"
						title="<spring:message
				code="orderByCompany"  />"><spring:message
								code="company"  /></a></th>
					<th></th>
				</tr>
			</c:if>
			<c:if test="${pageWrapper.message=='welcomeSelect'}">
				<tr>
					<th><a
						href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}${!empty pageWrapper.searchComputer ? '&searchComputer='.concat(pageWrapper.searchComputer) : ''}${!empty pageWrapper.searchCompany ? '&searchCompany='.concat(pageWrapper.searchCompany) : ''}&orderBy=${ pageWrapper.orderBy =='nameASC' ? 'nameDESC' : 'nameASC'}" class="lienCpt"
						title="<spring:message
				code="orderByName"  />"><c:out
								value="Computer Name" /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}${!empty pageWrapper.searchComputer ? '&searchComputer='.concat(pageWrapper.searchComputer) : ''}${!empty pageWrapper.searchCompany ? '&searchCompany='.concat(pageWrapper.searchCompany) : ''}&orderBy=${ pageWrapper.orderBy =='introducedASC' ? 'introducedDESC' : 'introducedASC'}" class="lienCpt"
						title="<spring:message
				code="orderByIntroduced"  />"><c:out
								value="Introduced Date" /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}${!empty pageWrapper.searchComputer ? '&searchComputer='.concat(pageWrapper.searchComputer) : ''}${!empty pageWrapper.searchCompany ? '&searchCompany='.concat(pageWrapper.searchCompany) : ''}&orderBy=${ pageWrapper.orderBy =='discontinuedASC' ? 'discontinuedDESC' : 'discontinuedASC'}" class="lienCpt"
						title="<spring:message
				code="orderByDiscontinued"  />"><c:out
								value="Discontinued Date" /></a></th>
					<th><a
						href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}${!empty pageWrapper.searchComputer ? '&searchComputer='.concat(pageWrapper.searchComputer) : ''}${!empty pageWrapper.searchCompany ? '&searchCompany='.concat(pageWrapper.searchCompany) : ''}&orderBy=${ pageWrapper.orderBy =='companyASC' ? 'companyDESC' : 'companyASC'}" class="lienCpt"
						title="<spring:message
				code="orderByCompany"  />"><c:out
								value="Company" /></a></th>
					<th></th>
				</tr>
			</c:if>
		</thead>
		<tbody>
			<c:forEach items="${dtoWrapper.listComputersDTO}" var="var">
				<tr>
					<td><a href="/ProjetWeb/editForm?id=${var.id}" class="lienCpt"
						title="<spring:message
				code="editComputer"  />"><c:out
								value="${var.name}" /></a></td>
					<td><c:out value="${var.introduced}" /></td>
					<td><c:out value="${var.discontinued}" /></td>
					<td><c:out value="${var.companyName} " /></td>
					<td><a id="delete"
						href="/ProjetWeb/delete?id=${var.id}${!empty pageWrapper.currentPage ? '&currentPage='.concat(pageWrapper.currentPage) : ''}"><button
								type="button" class="btn btn-danger">
								<spring:message code="deleteComputer"  />
							</button></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<tags:pagination />
</div>
<jsp:include page="include/footer.jsp" />