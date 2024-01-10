<%--
  Created by IntelliJ IDEA.
  User: MyPC
  Date: 12/16/2022
  Time: 2:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>

<aside class="app-sidebar">
  <div class="app-sidebar__user">
    <p class="app-sidebar__user-designation">Trang quản trị admin</p>

  </div>
  <hr>
  <%
    String isCurrent = request.getParameter("isCurrent");
  %>
  <ul class="app-menu">
    <!-- <li><a class="app-menu__item haha" href="phan-mem-ban-hang.jsp"><i class='app-menu__icon bx bx-cart-alt'></i>
        <span class="app-menu__label">POS Bán Hàng</span></a></li> -->
    <li><a class="app-menu__item <%=isCurrent.equals("indexTable")?"active":""%>" href="<%=request.getContextPath()%>/admin/Index"><i class='app-menu__icon bx bx-tachometer'></i><span
            class="app-menu__label">Bảng điều khiển</span></a></li>
    <li><a class="app-menu__item <%=isCurrent.equals("guideTable")?"active":""%> " href="<%=request.getContextPath()%>/admin/GuideTableData"><i class='app-menu__icon bx bx-id-card'></i> <span
            class="app-menu__label">Quản lý hướng dẫn viên</span></a></li>
    <li><a class="app-menu__item <%=isCurrent.equals("customerTable")?"active":""%>" href="<%=request.getContextPath()%>/admin/CustomerDataTable"><i class='app-menu__icon bx bx-user-voice'></i><span
            class="app-menu__label">Quản lý khách du lịch</span></a></li>
    <li><a class="app-menu__item <%=isCurrent.equals("tourTable")?"active":""%>" href="<%=request.getContextPath()%>/admin/TourTableData"><i
            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý Tour</span></a>
    </li>
    <li><a class="app-menu__item <%=isCurrent.equals("orderTable")?"active":""%>" href="<%=request.getContextPath()%>/admin/BookingTableData"><i class='app-menu__icon bx bx-task'></i><span
            class="app-menu__label">Quản lý đơn đặt Tour</span></a></li>
    <li><a class="app-menu__item <%=isCurrent.equals("voucherTable")?"active":""%>" href="<%=request.getContextPath()%>/admin/VoucherTableData"><i class='app-menu__icon bx bx-run'></i><span
            class="app-menu__label">Quản lý Voucher
          </span></a></li>
    <!-- <li><a class="app-menu__item" href="table-data-money.jsp"><i class='app-menu__icon bx bx-dollar'></i><span
          class="app-menu__label">Bảng kê lương</span></a></li> -->
    <li><a class="app-menu__item <%=isCurrent.equals("blogTable")?"active":""%>" href="<%=request.getContextPath()%>/admin/BlogTableData"><i
            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý Blog</span></a>
    </li>

    <li><a class="app-menu__item <%=isCurrent.equals("khoaTable")?"active":""%>" href="<%=request.getContextPath()%>/admin/KeyTableData">
      <i class='app-menu__icon bx bx-key'></i>
      <span class="app-menu__label">Quản lý Khóa</span></a>
    </li>

    <li><a class="app-menu__item <%=isCurrent.equals("reportTable")?"active":""%>" href="<%=request.getContextPath()%>/admin/ReportTableData"><i
            class='app-menu__icon bx bx-pie-chart-alt-2'></i><span class="app-menu__label">Báo cáo doanh thu</span></a>
    </li>
    <li><a class="app-menu__item <%=isCurrent.equals("calendarTable")?"active":""%>" href="<%=request.getContextPath()%>/admin/Calendar"><i class='app-menu__icon bx bx-calendar-check'></i><span
            class="app-menu__label">Lịch công tác </span></a></li>
    <!-- <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài
          đặt hệ thống</span></a></li> -->
  </ul>
</aside>


</body>
</html>
