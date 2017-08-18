<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
    
<div class="pagination">
	<ul>
		<%--如果是首页 那么没有点击首页--%>
			<li>
			<tt:if test="${mp.curpage>1}">
				<a href="article?action=queryall&rootid=0&curpage=1">首页</a>
			</tt:if></li>

			<%--如果是第一页 那么没有点击前一页--%>
			<li>
				<tt:if test="${mp.curpage>1}">
				<a href="article?action=queryall&rootid=0&curpage=${mp.curpage-1}">前一页</a>
			</tt:if></li>

		

			 <tt:forEach var="i" begin="1" end="${mp.maxpage}">
			<li>
				<a href="article?action=queryall&rootid=0&curpage=${mp.curpage}">${i}</a>
			</li>
			 </tt:forEach>



			<%--如果是最后一页 那么没有点击下一页--%>
			<li>
				<tt:if test="${mp.curpage<mp.maxpage}">
					<a href="article?action=queryall&rootid=0&curpage=${mp.curpage+1}">下一页</a>
				</tt:if></li>

			<%--如果是最后一页 那么没有点击尾页--%>

				<li><tt:if test="${mp.curpage<mp.maxpage}">
					<a href="article?action=queryall&rootid=0&curpage=${mp.maxpage}">尾页</a>
				</tt:if></li>

	</ul>
</div>