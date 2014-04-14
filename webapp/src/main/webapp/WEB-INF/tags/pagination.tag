<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<c:if test="${wrapper.currentPage != 1}">
		<c:if
			test="${wrapper.message=='welcome' or wrapper.message=='welcomeDelete' or wrapper.message=='welcomeEdit' or wrapper.message=='welcomeAdd'}">
			<td><a
				href="/ProjetWebExcilysMaven/dashboard?currentPage=${wrapper.currentPage - 1}"><spring:message code="previous" text="default text" /></a></td>
		</c:if>
		<c:if test="${wrapper.message=='welcomeSelect'}">
			<td><a
				href="/ProjetWebExcilysMaven/dashboard?searchComputer=${wrapper.searchComputer}&searchCompany=${wrapper.searchCompany}&currentPage=${wrapper.currentPage - 1}"><spring:message code="previous" text="previous" /></a></td>
		</c:if>
	</c:if>
	<c:forEach begin="1" end="${wrapper.nbrOfPages}" var="i">
		<c:choose>
			<c:when test="${wrapper.currentPage eq i}">
				<td>${i}</td>
			</c:when>
			<c:otherwise>
				<c:if
					test="${wrapper.message=='welcome' or wrapper.message=='welcomeDelete' or wrapper.message=='welcomeEdit' or wrapper.message=='welcomeAdd'}">
					<td><a
						href="/ProjetWebExcilysMaven/dashboard?currentPage=${i}">${i}</a></td>
				</c:if>
				<c:if
					test="${wrapper.message=='welcomeSelect'}">
					<td><a
						href="/ProjetWebExcilysMaven/dashboard?searchComputer=${wrapper.searchComputer}&searchCompany=${wrapper.searchCompany}&currentPage=${i}">${i}</a></td>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${wrapper.currentPage lt wrapper.nbrOfPages}">
		<c:if
			test="${wrapper.message=='welcome' or wrapper.message=='welcomeDelete' or wrapper.message=='welcomeEdit' or wrapper.message=='welcomeAdd'}">
			<td><a
				href="/ProjetWebExcilysMaven/dashboard?currentPage=${wrapper.currentPage + 1}"><spring:message code="next" text="next" /></a></td>
		</c:if>
		<c:if test="${wrapper.message=='welcomeSelect'}">
			<td><a
				href="/ProjetWebExcilysMaven/dashboard?searchComputer=${wrapper.searchComputer}&searchCompany=${wrapper.searchCompany}&currentPage=${wrapper.currentPage + 1}"><spring:message code="next" text="next" /></a></td>
		</c:if>
	</c:if>