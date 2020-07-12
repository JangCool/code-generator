<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header-base.jsp" %>
<c:set var="browserTitle" value=""/>
<%@ include file="/WEB-INF/views/common/header-layout.jsp" %>

<!-- 내용 구현 -->

<script src="${Const.URL_CDN_STATIC_PATH}/js/biz/${business}/${jspName}/${jspName}-main.js?${resVer}"></script>
<%@ include file="/WEB-INF/views/common/footer-layout.jsp" %>
