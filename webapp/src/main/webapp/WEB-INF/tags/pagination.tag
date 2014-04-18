<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<c:if test="${pageWrapper.currentPage != 1}">
		<c:if
			test="${pageWrapper.message=='welcome' or pageWrapper.message=='welcomeDelete' or pageWrapper.message=='welcomeEdit' or pageWrapper.message=='welcomeAdd'}">
			<td><a
				href="/ProjetWeb/dashboard?currentPage=${pageWrapper.currentPage - 1}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}"><spring:message code="previous" text="default text" /></a></td>
		</c:if>
		<c:if test="${pageWrapper.message=='welcomeSelect'}">
			<td><a
				href="/ProjetWeb/dashboard?searchComputer=${pageWrapper.searchComputer}&searchCompany=${pageWrapper.searchCompany}&currentPage=${pageWrapper.currentPage - 1}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}"><spring:message code="previous" text="previous" /></a></td>
		</c:if>
	</c:if>
	<c:forEach begin="1" end="${pageWrapper.nbrOfPages}" var="i">
		<c:choose>
			<c:when test="${pageWrapper.currentPage eq i}">
				<td>${i}</td>
			</c:when>
			<c:otherwise>
				<c:if
					test="${pageWrapper.message=='welcome' or pageWrapper.message=='welcomeDelete' or pageWrapper.message=='welcomeEdit' or pageWrapper.message=='welcomeAdd'}">
					<td><a
						href="/ProjetWeb/dashboard?currentPage=${i}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}">${i}</a></td>
				</c:if>
				<c:if
					test="${pageWrapper.message=='welcomeSelect'}">
					<td><a
						href="/ProjetWeb/dashboard?searchComputer=${pageWrapper.searchComputer}&searchCompany=${pageWrapper.searchCompany}&currentPage=${i}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}">${i}</a></td>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${pageWrapper.currentPage lt pageWrapper.nbrOfPages}">
		<c:if
			test="${pageWrapper.message=='welcome' or pageWrapper.message=='welcomeDelete' or pageWrapper.message=='welcomeEdit' or pageWrapper.message=='welcomeAdd'}">
			<td><a
				href="/ProjetWeb/dashboard?currentPage=${pageWrapper.currentPage + 1}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}"><spring:message code="next" text="next" /></a></td>
		</c:if>
		<c:if test="${pageWrapper.message=='welcomeSelect'}">
			<td><a
				href="/ProjetWeb/dashboard?searchComputer=${pageWrapper.searchComputer}&searchCompany=${pageWrapper.searchCompany}&currentPage=${pageWrapper.currentPage + 1}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}"><spring:message code="next" text="next" /></a></td>
		</c:if>
	</c:if>