<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actFol" value="${ForwardConst.ACT_FOL.getValue()}" />
<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCreate" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<c:set var="commFoledIdx"
    value="${ForwardConst.CMD_FOLLOWED_INDEX.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>フォロー状態一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>フォロー状態</th>
                    <th>変更</th>
                    <th>日報一覧</th>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <td><c:choose>
                                <c:when test="${followed_ids.contains(employee.id)}">
                                    フォロー中
                                </c:when>
                                <c:otherwise>
                                    フォローしていない
                                </c:otherwise>
                            </c:choose></td>
                        <td><c:choose>
                                <c:when test="${followed_ids.contains(employee.getId())}">
                                    <!-- <a href="<c:url value='?action=${actFol}&command=${commDestroy}&id=${employee.id}' />">解除する</a> -->
                                    <form
                                        action="<c:url value='?action=${actFol}&command=${commDestroy}' />"
                                        method="post">
                                        <input type="submit" value="解除する"> <input
                                            type="hidden" value="${employee.id}" name="id">
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <!--<a href="<c:url value='?action=${actFol}&command=${commCreate}&id=${employee.id}' />">フォローする</a>-->
                                    <form
                                        action="<c:url value='?action=${actFol}&command=${commCreate}' />"
                                        method="post">
                                        <input type="submit" value="フォローする"> <input
                                            type="hidden" value="${employee.id}" name="id">
                                    </form>
                                </c:otherwise>
                            </c:choose></td>
                        <td><c:choose>
                                <c:when test="${followed_ids.contains(employee.getId())}">
                                    <a
                                        href="<c:url value='?action=${actRep}&command=${commFoledIdx}&id=${employee.id}' />">表示</a>
                                </c:when>
                                <c:otherwise>
                                    フォローしてください
                                </c:otherwise>
                            </c:choose></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${employees_count} 件）<br />
            <c:forEach var="i" begin="1"
                end="${((employees_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a
                            href="<c:url value='?action=${actFol}&command=${commIdx}&page=${i}' />"><c:out
                                value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

    </c:param>
</c:import>