<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="topbar">
		<h1 class="fill">
			<a href="index.jsp"> <spring:message code="application"
					text="default text" />
			</a> <span class="language"> <a
				href="?language=en${!empty wrapper.computerDTO.id ? '&id='.concat(wrapper.computerDTO.id) : ''}${!empty wrapper.currentPage ? '&currentPage='.concat(wrapper.currentPage) : ''}${!empty wrapper.searchComputer ? '&searchComputer='.concat(wrapper.searchComputer) : ''}${!empty wrapper.searchCompany ? '&searchCompany='.concat(wrapper.searchCompany) : ''}${!empty wrapper.orderBy ? '&orderBy='.concat(wrapper.orderBy) : ''}">English</a>
				| <a
				href="?language=fr${!empty wrapper.computerDTO.id ? '&id='.concat(wrapper.computerDTO.id) : ''}${!empty wrapper.currentPage ? '&currentPage='.concat(wrapper.currentPage) : ''}${!empty wrapper.searchComputer ? '&searchComputer='.concat(wrapper.searchComputer) : ''}${!empty wrapper.searchCompany ? '&searchCompany='.concat(wrapper.searchCompany) : ''}${!empty wrapper.orderBy ? '&orderBy='.concat(wrapper.orderBy) : ''}">Français</a>
			</span>
		</h1>
	</header>