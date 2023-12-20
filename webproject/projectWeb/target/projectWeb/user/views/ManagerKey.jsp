<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 12/9/2023
  Time: 7:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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

    <title>Quản lí khóa</title>
</head>
<body>


<%@include file="./components/header.jsp"%>


<div class="profile-wrapper">
    <main role="main">
        <main role="main">
            <div class="profile">
                <div class="container_profile">
                    <div class="content">


                        <jsp:include page="./components/box-left.jsp">
                            <jsp:param name="isCurrent" value="managerKey"/>
                        </jsp:include>

                        <div class="" style="margin-top: 20px;">
                            <div class="wrapper order" >
                                <main class="app-content ps-md-4">
                                    <div class="app-title">
                                        <ul class="app-breadcrumb breadcrumb side" style="justify-content: center;">
                                           <b>Quản lí Khóa</b>
                                        </ul>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-12">
                                            <%--            <span style="font-size: 1rem;color: red"><%=error==null?"":error%></span>--%>
                                            <div class="tile">
                                                <div class="tile-body">

                                                    <div class="row element-button">
                                                        <div class="col-sm-2 mb-3">

                                                            <a class="btn btn-outline-primary btn-sm" href="#" title="Thêm" onclick="generateRSAKey()">
                                                                <i class="fas fa-plus"></i> Tạo Key
                                                            </a>

                                                        </div>

                                                        <div class="col-sm-2 mb-3">

                                                            <a class="btn btn-outline-success btn-sm" href="#" title="Thêm"><i class="fas fa-plus"></i>
                                                                Nhập Key</a>
                                                        </div>


                                                        <%--              <div class="col-sm-2">--%>
                                                        <%--                <a class="btn btn-delete btn-sm" type="button" title="Xóa" onclick="myFunction(this)"><i--%>
                                                        <%--                    class="fas fa-trash-alt"></i> Xóa tất cả </a>--%>
                                                        <%--              </div>--%>
                                                    </div>
                                                    <table class="table table-hover table-bordered js-copytextarea" cellpadding="0" cellspacing="0" border="0"
                                                           id="sampleTable">
                                                        <thead>
                                                        <tr>
                                                            <th width="50">Ngày Tạo</th>
                                                            <th width="150">Key</th>
                                                            <th width="100">Trạng Thái</th>

                                                            <th width="100">Tính năng</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <%

                                                            for (int i = 0 ;i < 2;i++) {

                                                        %>
                                                        <tr>
                                                            <td>27-12-2023</td>
                                                            <td>ạasjdkadalkdkka ldsk ajsldksalkdjlkajl slajdlksa ldjalsj</td>
                                                            <td>Đang sử dụng</td>
                                                            <td class="table-td-center">
                                                                <form action="/projectWeb_war/admin/GuideTableData" id="form" method="post">
                                                                    <input style="display: none" name="guideId" value="">
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

                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </main>
    </main>
</div>

</body>
<script>
    function generateRSAKey() {
        // Tạo một cặp khóa RSA ngẫu nhiên
        var key = new RSAKey();
        key.generate(2048, '10001');

        // Trích xuất khóa công cộng và khóa riêng
        var publicKey = key.getPublicKey();
        var privateKey = key.getPrivateKey();

        // Hiển thị khóa hoặc gửi chúng đến máy chủ bằng AJAX
        alert('Khóa Công Cộng: ' + publicKey);
        alert('Khóa Riêng: ' + privateKey);

        $.ajax({
            type: 'POST',
            url: '/generate-key', // Thay đổi đường dẫn tới servlet mới tạo
            data: { publicKey: publicKey, privateKey: privateKey },
            success: function(response) {
                // Xử lý phản hồi từ máy chủ nếu cần
                alert('Keys sent to the server successfully');
            },
            error: function(error) {
                alert('Error sending keys to the server');
            }
        });

    }
</script>

<!-- Essential javascripts for application to work-->
<script src="https://kjur.github.io/jsrsasign/jsrsasign-latest-all-min.js"></script>

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
</html>
