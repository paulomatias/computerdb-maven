<jsp:include page="include/header.jsp" />log
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %> 

<section id="main">
	<h1 id="homeTitle">${wrapper.message}</h1>
	<h2>
		<c:out value="${wrapper.nbrComputers}" />
		Computer(s) found
	</h2>
	<div id="actions">
		<form action="/computer-database/DashboardServlet" method="GET">
			<input type="search" id="searchbox" value="" name="searchComputer"
				placeholder="Search computer name"> <input type="search"
				id="searchbox" value="" name="searchCompany"
				placeholder="Search company name"> <input type="submit"
				id="searchbox" value="Search" class="btn primary">
		</form>
		<a class="btn success" id="add"
			href="/computer-database/AddComputerServlet">Add Computer</a>
	</div>
	<p></p>
	<table class="computers zebra-striped">
		<thead>
			<c:if
				test="${wrapper.message=='Welcome to your computer database !' or wrapper.message=='Computer deleted successfully !' or wrapper.message=='Computer edited successfully !' or wrapper.message=='Computer added successfully !'}">
				<tr>
					<th><a
						href="/computer-database/DashboardServlet?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}&orderBy=${ wrapper.orderBy =='nameASC' ? 'nameDESC' : 'nameASC'}"
						title="order by name"><c:out value="Computer Name" /></a></th>
					<th><a
						href="/computer-database/DashboardServlet?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}&orderBy=${ wrapper.orderBy =='introducedASC' ? 'introducedDESC' : 'introducedASC'}"
						title="order by introduced date"><c:out
								value="Introduced Date" /></a></th>
					<th><a
						href="/computer-database/DashboardServlet?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}&orderBy=${ wrapper.orderBy =='discontinuedASC' ? 'discontinuedDESC' : 'discontinuedASC'}"
						title="order by discontinued date"><c:out
								value="Discontinued Date" /></a></th>
					<th><a
						href="/computer-database/DashboardServlet?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}&orderBy=${ wrapper.orderBy =='companyASC' ? 'companyDESC' : 'companyASC'}"
						title="order by company"><c:out value="Company" /></a></th>
					<th></th>
				</tr>
			</c:if>
			<c:if
				test="${wrapper.message=='Computer(s) selected successfully !'}">
				<tr>
					<th><a
						href="/computer-database/DashboardServlet?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}${!empty wrapper.searchComputer ? '&searchComputer='.concat(wrapper.searchComputer) : ''}${!empty wrapper.searchCompany ? '&searchCompany='.concat(wrapper.searchCompany) : ''}&orderBy=${ wrapper.orderBy =='nameASC' ? 'nameDESC' : 'nameASC'}"
						title="order by name"><c:out value="Computer Name" /></a></th>
					<th><a
						href="/computer-database/DashboardServlet?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}${!empty wrapper.searchComputer ? '&searchComputer='.concat(wrapper.searchComputer) : ''}${!empty wrapper.searchCompany ? '&searchCompany='.concat(wrapper.searchCompany) : ''}&orderBy=${ wrapper.orderBy =='introducedASC' ? 'introducedDESC' : 'introducedASC'}"
						title="order by introduced date"><c:out
								value="Introduced Date" /></a></th>
					<th><a
						href="/computer-database/DashboardServlet?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}${!empty wrapper.searchComputer ? '&searchComputer='.concat(wrapper.searchComputer) : ''}${!empty wrapper.searchCompany ? '&searchCompany='.concat(wrapper.searchCompany) : ''}&orderBy=${ wrapper.orderBy =='discontinuedASC' ? 'discontinuedDESC' : 'discontinuedASC'}"
						title="order by discontinued date"><c:out
								value="Discontinued Date" /></a></th>
					<th><a
						href="/computer-database/DashboardServlet?${!empty wrapper.currentPage ? 'currentPage='.concat(wrapper.currentPage) : ''}${!empty wrapper.searchComputer ? '&searchComputer='.concat(wrapper.searchComputer) : ''}${!empty wrapper.searchCompany ? '&searchCompany='.concat(wrapper.searchCompany) : ''}&orderBy=${ wrapper.orderBy =='companyASC' ? 'companyDESC' : 'companyASC'}"
						title="order by company"><c:out value="Company" /></a></th>
					<th></th>
				</tr>
			</c:if>
		</thead>
		<tbody>
			<c:forEach items="${wrapper.listComputersDTO}" var="var">
				<tr>
					<td><a
						href="/computer-database/EditComputerServlet?id=${var.id}"
						title="edit this computer"><c:out value="${var.name}" /></a></td>
					<td><c:out value="${var.introduced}" /></td>
					<td><c:out value="${var.discontinued}" /></td>
					<td><c:out value="${var.companyName} " /></td>
					<td><a class="btn danger" id="delete"
						href="/computer-database/DeleteComputerServlet?id=${var.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<tags:pagination />
</section>

<jsp:include page="include/footer.jsp" />
