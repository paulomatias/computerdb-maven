<jsp:include page="include/header.jsp" />
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="container-fluid">
	<h1 class="text-center">
		<spring:message code="${pageWrapper.message}" />
	</h1>
	<h2 class="text-center">
		<c:out value="${pageWrapper.nbrComputers}" />
		<spring:message code="nbrComputer" />
	</h2>
	<div>

<!-- 		<form action="/ProjetWeb/dashboard" method="GET"> -->
<!-- 			<input type="text" class="class-form" -->
<%-- 				value="${pageWrapper.searchComputer }" name="searchComputer" --%>
<%-- 				placeholder="<spring:message	code="searchComputer"  />"> <input --%>
<%-- 				type="text" class="class-form" value="${pageWrapper.searchCompany }" --%>
<!-- 				name="searchCompany" -->
<%-- 				placeholder="<spring:message --%>
<%-- 				code="searchCompany" />"> --%>
<!-- 			<button class="btn btn-primary " type="submit"> -->
<!-- 				<i class="fa fa-search"></i> -->
<%-- 				<spring:message code="search" /> --%>
<!-- 			</button> -->
<!-- 			<a href="/ProjetWeb/addForm"><button type="button" -->
<!-- 					class="btn btn-success"> -->
<!-- 					<i class="fa fa-floppy-o"></i> -->
<%-- 					<spring:message code="addComputer" /> --%>
<!-- 				</button> </a> -->
<!-- 		</form> -->

	</div>
	<table class="table table-hover table-striped table-bordered">
		<thead>
			<tr>
				<th ><form action="/ProjetWeb/dashboard" method="GET">
						<input type="text" class="class-form"
							value="${pageWrapper.searchComputer }" name="searchComputer"
							placeholder="<spring:message	code="searchComputer"  />">
						<input type="text" class="class-form"
							value="${pageWrapper.searchCompany }" name="searchCompany"
							placeholder="<spring:message
				code="searchCompany" />">
						<button class="btn btn-primary " type="submit">
							<i class="fa fa-search"></i>
							<spring:message code="search" />
						</button>
					</form></th>
				<th colspan="4" class="text-right"><a href="/ProjetWeb/addForm"><button type="button"
							class="btn btn-success">
							<i class="fa fa-floppy-o"></i>
							<spring:message code="addComputer" />
						</button> </a></th>
			</tr>
			<tr>
				<th class="text-center"><a
					href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}${!empty pageWrapper.searchComputer ? '&searchComputer='.concat(pageWrapper.searchComputer) : ''}${!empty pageWrapper.searchCompany ? '&searchCompany='.concat(pageWrapper.searchCompany) : ''}&orderBy=${ pageWrapper.orderBy =='nameASC' ? 'nameDESC' : 'nameASC'}"
					class="lienCpt" title="<spring:message
				code="orderByName"  />">
						<spring:message code="name" /> <i
						class="${pageWrapper.orderBy=='nameDESC' ? 'fa fa-sort-desc' : pageWrapper.orderBy =='nameASC' ? 'fa fa-sort-asc' : ''}"></i>
				</a></th>
				<th class="text-center"><a
					href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}${!empty pageWrapper.searchComputer ? '&searchComputer='.concat(pageWrapper.searchComputer) : ''}${!empty pageWrapper.searchCompany ? '&searchCompany='.concat(pageWrapper.searchCompany) : ''}&orderBy=${ pageWrapper.orderBy =='introducedASC' ? 'introducedDESC' : 'introducedASC'}"
					class="lienCpt"
					title="<spring:message
				code="orderByIntroduced"  />"><spring:message
							code="introduced" /> <i
						class="${pageWrapper.orderBy=='introducedDESC' ? 'fa fa-sort-desc' : pageWrapper.orderBy =='introducedASC' ? 'fa fa-sort-asc' : ''}"></i>
				</a></th>
				<th class="text-center"><a
					href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}${!empty pageWrapper.searchComputer ? '&searchComputer='.concat(pageWrapper.searchComputer) : ''}${!empty pageWrapper.searchCompany ? '&searchCompany='.concat(pageWrapper.searchCompany) : ''}&orderBy=${ pageWrapper.orderBy =='discontinuedASC' ? 'discontinuedDESC' : 'discontinuedASC'}"
					class="lienCpt"
					title="<spring:message
				code="orderByDiscontinued"  />"><spring:message
							code="discontinued" /> <i
						class="${pageWrapper.orderBy=='discontinuedDESC' ? 'fa fa-sort-desc' : pageWrapper.orderBy =='discontinuedASC' ? 'fa fa-sort-asc' : ''}"></i>
				</a></th>
				<th class="text-center"><a
					href="/ProjetWeb/dashboard?${!empty pageWrapper.currentPage ? 'currentPage='.concat(pageWrapper.currentPage) : ''}${!empty pageWrapper.searchComputer ? '&searchComputer='.concat(pageWrapper.searchComputer) : ''}${!empty pageWrapper.searchCompany ? '&searchCompany='.concat(pageWrapper.searchCompany) : ''}&orderBy=${ pageWrapper.orderBy =='companyASC' ? 'companyDESC' : 'companyASC'}"
					class="lienCpt"
					title="<spring:message
				code="orderByCompany"  />"><spring:message
							code="company" /><i
						class="${pageWrapper.orderBy=='companyDESC' ? 'fa fa-sort-desc' : pageWrapper.orderBy =='companyASC' ? 'fa fa-sort-asc' : ''}"></i>
				</a></th>
				<th class="text-center"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dtoWrapper.listComputersDTO}" var="var">
				<tr>
					<td class="text-center"><a
						href="/ProjetWeb/editForm?id=${var.id}" class="lienCpt"
						title="<spring:message
				code="editComputer"  />"><i
							class="fa fa-pencil-square"></i> <c:out value="${var.name}" /></a></td>
					<td class="text-center"><c:out value="${var.introduced}" /></td>
					<td class="text-center"><c:out value="${var.discontinued}" /></td>
					<td class="text-center"><c:out value="${var.companyName} " /></td>
					<td class="text-center"><a
						href="/ProjetWeb/delete?id=${var.id}${!empty pageWrapper.currentPage ? '&currentPage='.concat(pageWrapper.currentPage) : ''}"><button
								type="button" class="btn btn-danger">
								<i class="fa fa-trash-o "></i>
								<spring:message code="deleteComputer" />
							</button></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="text-center">
	<tags:pagination />
	</div>
</div>
<jsp:include page="include/footer.jsp" />