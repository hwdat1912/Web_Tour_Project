<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.bean.PublicKey" %>
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
    <jsp:param name="isCurrent" value="khoaTable"/>
</jsp:include>

<% List<PublicKey> listKeys = request.getAttribute("listKeys")==null?null:(List<PublicKey>) request.getAttribute("listKeys") ;%>
<main class="app-content">
    <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
            <li class="breadcrumb-item active"><a href="#"><b>Danh sách khóa</b></a></li>
        </ul>
        <div id="clock"></div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="tile-body">

                    <div class="row element-button">



                        <%--                            <div class="col-sm-2">--%>
                        <%--                              <a class="btn btn-delete btn-sm" type="button" title="Xóa" onclick="myFunction(this)"><i--%>
                        <%--                                  class="fas fa-trash-alt"></i> Xóa tất cả </a>--%>
                        <%--                            </div>--%>
                    </div>
                    <table class="table table-hover table-bordered js-copytextarea" cellpadding="0" cellspacing="0" border="0"
                           id="sampleTable">
                        <thead>
                        <tr>
                            <th width="10"><input type="checkbox" id="all"></th>
                            <th width="50">ID key</th>
                            <th width="150">ID User</th>
                            <th width="300" class="text-center">Khóa</th> <!-- Adjusted width for the "Khóa" column -->
                            <th width="150">Thời gian tạo khóa</th>
                            <th width="150">Thời gian report</th>
                            <th width="100">Trạng thái</th>
                            <th width="100">Tính năng</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <% for (int i = 0; i < listKeys.size(); i++) {
                                int j = i + 1;
                            %>
                            <td width="10"><input type="checkbox" name="check<%=j%>" value="<%=listKeys.get(i).getPublic_id()%>"
                                                  data-key="<%=listKeys.get(i).getPublic_id()%>"></td>
                            <td><%=listKeys.get(i).getPublic_id() == 0 ? "Chưa có" : listKeys.get(i).getPublic_id()%></td>
                            <td><%=listKeys.get(i).getUserId()==null?"Chưa có":listKeys.get(i).getUserId()%></td>
                            <td class="wrap-text"><%=listKeys.get(i).getP_key()==null?"Chưa có":listKeys.get(i).getP_key()%></td>
                            <td><%=listKeys.get(i).getDate_create()==null?"Chưa có":listKeys.get(i).getDate_create().toString()%></td>
                            <td><%=listKeys.get(i).getDate_report()==null?"Chưa có":listKeys.get(i).getDate_report().toString()%></td>
                            <td>
                                <% Integer status = listKeys.get(i).getStatus(); %>
                                <%= (status != null && status == 0) ? "Disabled" : "Hoạt động" %>
                            </td>
                            <td class="table-td-center">

                                <div class="col-sm-2">
                                    <a class="btn btn-primary btn-sm disable-key-btn"
                                       href="#"
                                       data-toggle="modal"
                                       data-target="#disableKeyModal"
                                       onclick="disableKey('<%= listKeys.get(i).getPublic_id() %>')"
                                       title="Disabled">
                                        <i class="fas fa-ban"></i>
                                    </a>
                                </div>
                                <button class="btn btn-primary btn-sm trash" type="submit" name="option" value="delete" title="Xóa">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </td>



                        </tr>
                        <% } %>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>
    </div>
</main>







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
<script type="text/javascript">$('#sampleTable').DataTable();</script>

<script>

    jQuery(function () {
        jQuery(".trash").click(function () {
            var row = jQuery(this).closest("tr");

            swal({
                title: "Cảnh báo",
                text: "Bạn có chắc chắn là muốn xóa khóa này?",
                buttons: ["Hủy bỏ", "Đồng ý"],
            }).then((willDelete) => {
                if (willDelete) {
                    // Remove the row if the user confirms
                    row.remove();

                    swal("Đã xóa thành công!", {
                        icon: "success",
                    });
                }
            });
        });
    });
    oTable = $('#sampleTable').dataTable();
    $('#all').click(function (e) {
        $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
        e.stopImmediatePropagation();
    });


    function disableKey(publicKeyId) {
        // Ask for confirmation using SweetAlert
        swal({
            title: "Cảnh báo",
            text: "Bạn có chắc chắn muốn tắt khóa này?",
            icon: "warning",
            buttons: ["Hủy", "Đồng ý"],
            dangerMode: true,
        }).then((confirmed) => {
            if (confirmed) {
                // User confirmed, proceed to disable the key
                $.ajax({
                    type: "POST",
                    url: "/projectWeb_war/admin/DisableKeyServlet",
                    data: { publicId: publicKeyId },
                    success: function(response) {
                        // Check if the key was disabled successfully
                        if (response.trim() === "success") {
                            swal("Thành công", "Key đã được tắt thành công!", "success");
                        }
                    },
                    error: function(error) {
                        swal("Lỗi", "Không thể tắt key. Vui lòng thử lại!", "error");
                    }
                });
            } else {
                // User canceled, do nothing
                swal("Hủy bỏ", "Không có thay đổi nào được thực hiện!", "info");
            }
        });
    }



    //EXCEL
    // $(document).ready(function () {
    //   $('#').DataTable({

    //     dom: 'Bfrtip',
    //     "buttons": [
    //       'excel'
    //     ]
    //   });
    // });



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
    //In dữ liệu
    var myApp = new function () {
        this.printTable = function () {
            var tab = document.getElementById('sampleTable');
            var win = window.open('', '', 'height=700,width=700');
            win.document.write(tab.outerHTML);
            win.document.close();
            win.print();
        }
    }




</script>
<style>
    .wrap-text {
        max-width: 300px; /* Adjust the max-width as per your design */
        word-wrap: break-word;
        white-space: normal;
    }
</style>
</body>

</html>

z