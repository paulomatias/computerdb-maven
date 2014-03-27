<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:if test="${wrapper.currentPage != 1}">
		<c:if
			test="${wrapper.message=='Welcome to your computer database !' or wrapper.message=='Computer deleted successfully !' or wrapper.message=='Computer edited successfully !' or wrapper.message=='Computer added successfully !'}">
			<td><a
				href="/computer-database/DashboardServlet?currentPage=${wrapper.currentPage - 1}">Previous</a></td>
		</c:if>
		<c:if test="${wrapper.message=='Computer(s) selected successfully !'}">
			<td><a
				href="/computer-database/DashboardServlet?searchComputer=${wrapper.searchComputer}&searchCompany=${wrapper.searchCompany}&currentPage=${wrapper.currentPage - 1}">Previous</a></td>
		</c:if>
	</c:if>
	<c:forEach begin="1" end="${wrapper.nbrOfPages}" var="i">
		<c:choose>
			<c:when test="${wrapper.currentPage eq i}">
				<td>${i}</td>
			</c:when>
			<c:otherwise>
				<c:if
					test="${wrapper.message=='Welcome to your computer database !' or wrapper.message=='Computer deleted successfully !' or wrapper.message=='Computer edited successfully !' or wrapper.message=='Computer added successfully !'}">
					<td><a
						href="/computer-database/DashboardServlet?currentPage=${i}">${i}</a></td>
				</c:if>
				<c:if
					test="${wrapper.message=='Computer(s) selected successfully !'}">
					<td><a
						href="/computer-database/DashboardServlet?searchComputer=${wrapper.searchComputer}&searchCompany=${wrapper.searchCompany}&currentPage=${i}">${i}</a></td>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${wrapper.currentPage lt wrapper.nbrOfPages}">
		<c:if
			test="${wrapper.message=='Welcome to your computer database !' or wrapper.message=='Computer deleted successfully !' or wrapper.message=='Computer edited successfully !' or wrapper.message=='Computer added successfully !'}">
			<td><a
				href="/computer-database/DashboardServlet?currentPage=${wrapper.currentPage + 1}">Next</a></td>
		</c:if>
		<c:if test="${wrapper.message=='Computer(s) selected successfully !'}">
			<td><a
				href="/computer-database/DashboardServlet?searchComputer=${wrapper.searchComputer}&searchCompany=${wrapper.searchCompany}&currentPage=${wrapper.currentPage + 1}">Next</a></td>
		</c:if>
	</c:if>