<%@ page import="vn.edu.hcmuaf.fit.bean.Blog" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html >

    <head>
        <title>Danh sách nhân viên | Quản trị Admin</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
      
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v6.2.0/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
      
      </head>

<body onload="time()" class="app sidebar-mini rtl">
   <!-- Navbar-->
   <%@include file="header.jsp"%>
   <!-- Sidebar menu-->
   <div class="app-sidebar__overlay" data-toggle="sidebar"></div>

   <jsp:include page="leftSideBar.jsp">
       <jsp:param name="isCurrent" value="blogTable"/>
   </jsp:include>

   <%
       List<Blog> blogList = (List<Blog>) request.getAttribute("blogList");
       String error = request.getAttribute("error") ==null?null:(String) request.getAttribute("error");
   %>
    <main class="app-content">
        <div class="app-title">
            <ul class="app-breadcrumb breadcrumb side">
                <li class="breadcrumb-item active"><a href="#"><b>Danh sách Blog</b></a></li>
            </ul>
            <div id="clock"></div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <span style="font-size: 1rem;color: red"><%=error==null?"":error%></span>
                <div class="tile">
                    <div class="tile-body">
                        <div class="row element-button">
                            <div class="col-sm-2">
              
                              <a class="btn btn-add btn-sm" href="<%=request.getContextPath()%>/admin/CRUDBlogData" title="Thêm"><i class="fas fa-plus"></i>
                                Tạo mới Blog</a>
                            </div>
                           
                           
<%--                            <div class="col-sm-2">--%>
<%--                              <a class="btn btn-delete btn-sm" type="button" title="Xóa" onclick="myFunction(this)"><i--%>
<%--                                  class="fas fa-trash-alt"></i> Xóa tất cả </a>--%>
<%--                            </div>--%>
                        </div>
                        <table class="table table-hover table-bordered" id="sampleTable">
                            <thead>
                                <tr>
                                    <th width="10"><input type="checkbox" id="all"></th>
                                    <th>Mã BLog</th>
                                    <th>Người viết bài</th>
                                    <th>Tên Blog</th>
                                    <th>Ngày viết bài</th>
                                    <th>Nội dung</th>
                                    <th>Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%
                                for (int i = 0; i < blogList.size(); i++) {
                                    int j =i +1;
                                %>
                                <tr>
                                    <td width="10"><input type="checkbox" name="check<%=j%>" value="<%=j%>"></td>
                                    <td><%=blogList.get(i).getBLOG_ID()%></td>
                                    <td><%=blogList.get(i).getFullName()%></td>
                                    <td><%=blogList.get(i).getBLOG_TITLE()%></td>
                                    <td><%=blogList.get(i).getNgayVietBai().toString()%></td>
                                    
                                    <td><textarea name="noidung" id="" cols="30" rows="10" disabled ><%=blogList.get(i).getDescription()%></textarea></td>
                                    <td>
                                        <form action="<%=request.getContextPath()%>/admin/BlogTableData" id="form" method="post">
                                            <input style="display: none" name="blogId" value="<%=blogList.get(i).getBLOG_ID()%>">
                                            <button class="btn btn-primary btn-sm trash" type="submit"name="option" value="delete" title="Xóa"
                                            ><i class="fas fa-trash-alt"></i>
                                            </button>
                                            <button class="btn btn-primary btn-sm edit" name="option" value="edit" type="submit" title="Sửa"
                                            ><i class="fas fa-edit"></i>
                                            </button>
                                        </form>
                                                         
                                    </td>
                                </tr>
                            <%}%>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </main>

<!--
  MODAL
-->
<div class="modal fade" id="ModalUP" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
data-keyboard="false">
<div class="modal-dialog modal-dialog-centered" role="document">
  <div class="modal-content">

    <div class="modal-body">
      <div class="row">
        <div class="form-group  col-md-12">
          <span class="thong-tin-thanh-toan">
            <h5>Chỉnh sửa thông tin Blog</h5>
          </span>
        </div>
      </div>
      <div class="row">
        <div class="form-group col-md-6">
            <label class="control-label">Mã Blog </label>
            <input class="form-control" type="number" value="blog001" disabled>
          </div>
        <div class="form-group col-md-6">
            <label class="control-label">Mã hướng dẫn viên</label>
          <input class="form-control" type="text" required value="giude011">
        </div>
        <div class="form-group col-md-6">
          <label class="control-label">Tên Blog</label>
        <input class="form-control" type="text" required value="giude011">
      </div>
      <div class="form-group col-md-6">
        <label class="control-label">Ngày viết bài</label>
        <input class="form-control" type="date" required >
      </div>
      <div class="form-group  col-md-6">
        <label class="control-label">Nội dung</label>
        <textarea class="form-control" ></textarea>
      </div>  
       
        
      </div>
      
      <BR>
      <button class="btn btn-save" type="button">Lưu lại</button>
      <a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
      <BR>
    </div>
    <div class="modal-footer">
    </div>
  </div>
</div>
</div>
<!--
MODAL
-->

    <!-- Essential javascripts for application to work-->
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="src/jquery.table2excel.js"></script>
    <script src="js/main.js"></script>
    <!-- The javascript plugin to display page loading on top-->
    <script src="js/plugins/pace.min.js"></script>
    <!-- Page specific javascripts-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
    <!-- Data table plugin-->
    <script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript">
        $('#sampleTable').DataTable();
        //Thời Gian
    function time() {
      var today = new Date();
      var weekday = new Array(7);
      weekday[0] = "Chủ Nhật";
      weekday[1] = "Thứ Hai";
      weekday[2] = "Thứ Ba";
      weekday[3] = "Thứ Tư";
      weekday[4] = "Thứ Năm";
      weekday[5] = "Thứ Sáu";
      weekday[6] = "Thứ Bảy";
      var day = weekday[today.getDay()];
      var dd = today.getDate();
      var mm = today.getMonth() + 1;
      var yyyy = today.getFullYear();
      var h = today.getHours();
      var m = today.getMinutes();
      var s = today.getSeconds();
      m = checkTime(m);
      s = checkTime(s);
      nowTime = h + " giờ " + m + " phút " + s + " giây";
      if (dd < 10) {
        dd = '0' + dd
      }
      if (mm < 10) {
        mm = '0' + mm
      }
      today = day + ', ' + dd + '/' + mm + '/' + yyyy;
      tmp = '<span class="date"> ' + today + ' - ' + nowTime +
        '</span>';
      document.getElementById("clock").innerHTML = tmp;
      clocktime = setTimeout("time()", "1000", "Javascript");

      function checkTime(i) {
        if (i < 10) {
          i = "0" + i;
        }
        return i;
      }
    }
    </script>
    <script>
        // function deleteRow(r) {
        //     var i = r.parentNode.parentNode.rowIndex;
        //     document.getElementById("sampleTable").deleteRow(i);
        // }
        // jQuery(function () {
        //
        //     jQuery(".trash").click(function (e) {
        //
        //         swal({
        //             title: "Cảnh báo",
        //             text: "Bạn có chắc chắn là muốn xóa sản phẩm này?",
        //             buttons: ["Hủy bỏ", "Đồng ý"],
        //         })
        //             .then((willDelete) => {
        //
        //                 if (willDelete) {
        //
        //                   var i = e.currentTarget.parentNode.parentNode.rowIndex;
        //
        //                  document.getElementById("sampleTable").deleteRow(i);
        //                     swal("Đã xóa thành công.!", {
        //
        //                     });
        //                 }
        //             });
        //     });
        // });
        oTable = $('#sampleTable').dataTable();
        $('#all').click(function (e) {
            $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
            e.stopImmediatePropagation();
        });
    </script>
</body>

</html>