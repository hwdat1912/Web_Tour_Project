<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.bean.PublicKey" %>
<%@ page import="vn.edu.hcmuaf.fit.services.KeyService" %>
<%@ page import="java.io.OutputStream" %><%--
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
    <link rel="stylesheet" type="text/css" href="./assets/css//box.css">
    <link rel="stylesheet" type="text/css" href="./assets/css//toast.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">

    <!-- or -->
    <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">

    <link rel="icon" href="./assets/images/favicon.png" type="image/gif" sizes="20x20">
    <link rel="stylesheet" type="text/css" href="./assets/css/bootstrap.min.css" >

    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.13.7/css/jquery.dataTables.min.css" >

    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v6.2.0/css/all.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">

    <title>Quản lí khóa</title>
</head>
<body>

<div class="preloader">
    <div class="loader">
        <span></span>
        <span></span>
        <span></span>
        <span></span>
    </div>
</div>


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
                        <%
                            String success = (String) request.getAttribute("success");

                            if(success != null){
                                response.sendRedirect(request.getContextPath()+"/user/views/ManagerKey");
                            }

                        %>

                        <div class="" style="margin-top: 20px;">
                            <div class="wrapper order" >
                                <main class="app-content ps-md-4" style="padding-left: 0px !important; margin-left: 60px !important;">
                                    <div class="app-title">
                                        <ul class="app-breadcrumb breadcrumb side" style="justify-content: center;">
                                           <b>Quản lí Khóa</b>
                                        </ul>
                                    </div>
                                    <div class="alert alert-success" style="display: none;">
                                        <strong>Success!</strong> Indicates a successful or positive action.
                                    </div>

                                    <%
                                        String error = (String) request.getAttribute("error");
                                    %>
                                    <div class="alert alert-danger" style="<%=error ==null ? "display:none":""%>">
                                        <strong>Lỗi!</strong> Có một public key đang được sử dụng không thể tạo mới
                                    </div>

                                    <div class="row">
                                        <div class="col-md-12">
                                            <%--            <span style="font-size: 1rem;color: red"><%=error==null?"":error%></span>--%>
                                            <div class="tile">
                                                <div class="tile-body">

                                                    <div class="row element-button" style="margin-left: 0px;">
                                                        <div class="col-sm-2 mb-3">
                                                            <form action="<%=request.getContextPath()%>/user/views/CreateKey" method="post">
                                                                <button class="btn btn-outline-primary btn-sm"  onclick="thanks()" title="Thêm"><i class="fas fa-plus"></i>
                                                                Tạo Key</button>
                                                            </form>
                                                        </div>

                                                        <div class="col-sm-2 mb-3">

                                                            <a class="btn btn-outline-success btn-sm" href="#my-dialog" title="Thêm"><i class="fas fa-plus"></i>
                                                                Nhập Key</a>
                                                        </div>

                                                        <div class="col-sm-2 mb-3">

                                                            <a class="btn btn-outline-info btn-sm" href="#dialog" title="Thêm"><i class="fas fa-plus"></i>
                                                                Tạo Public Key Từ Private Key</a>
                                                        </div>


                                                        <%--              <div class="col-sm-2">--%>
                                                        <%--                <a class="btn btn-delete btn-sm" type="button" title="Xóa" onclick="myFunction(this)"><i--%>
                                                        <%--                    class="fas fa-trash-alt"></i> Xóa tất cả </a>--%>
                                                        <%--              </div>--%>
                                                    </div>
                                                    <table  class="table table-hover table-bordered js-copytextarea" cellpadding="0" cellspacing="0" border="0"
                                                           id="sampleTable">
                                                        <thead>
                                                        <tr>
                                                            <th width="50">Ngày Tạo</th>
                                                            <th width="50">Ngày Báo</th>
                                                            <th width="150">Key</th>
                                                            <th width="100">Trạng Thái</th>

                                                            <th width="100">Tính năng</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <%
                                                            List<PublicKey> list = (List<PublicKey>) request.getAttribute("listKey");;
                                                            if(list !=null){
                                                                PublicKey item;
                                                                String status;
                                                                String badge = "";
                                                                for (int i = 0 ;i < list.size();i++) {
                                                                    item = list.get(i);
                                                                    if(item.getStatus() == KeyService.WARNING){
                                                                        status = "Key Đang Yêu Cầu ENABLE";
                                                                        badge = "bg-warning";
                                                                    }else if(item.getStatus() == KeyService.ENABLE){
                                                                        status = "Key Đang Sử Dụng";
                                                                        badge = "bg-success";
                                                                    }else {
                                                                        status = "Key Đã Bị ENABLE";
                                                                        badge ="bg-danger";
                                                                    }

                                                        %>
                                                        <tr style=" text-align: center;">
                                                            <td style="vertical-align: middle"><%=item.getDate_create()%></td>
                                                            <td style="vertical-align: middle"><%=item.getDate_report()%></td>
                                                            <td style="word-wrap: break-word;max-width: 100px; text-align: left;"><%=item.getP_key()%></td>
                                                            <td class="badge <%=badge%>" style="margin-top: 25%;"><%=status%></td>
                                                            <td class="table-td-center" style="vertical-align: middle">
                                                                <form action="<%=request.getContextPath()%>/user/views/LostKey" id="form" method="post">
                                                                    <input style="display: none" name="guideId" value="">
                                                                    <input type="hidden" name="publicId" value="<%=item.getPublic_id()%>">
                                                                    <button class="btn btn-primary btn-sm bullseye" type="submit"name="option" value="view" title="Xem"
                                                                    ><i class="fa-solid fa-eye"></i>
                                                                    </button>
                                                                    <button class="btn btn-danger btn-sm " onclick="lostKeyFunction()" name="option" value="warning" type="submit" title="Thông báo lộ Key"
                                                                    ><i class="fa-solid fa-triangle-exclamation"></i>
                                                                    </button>
                                                                </form>
                                                            </td>
                                                        </tr>
                                                        <%}}%>

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

<%--    <div>--%>
        <div id="toast_message">

        </div>
<%--    </div>--%>



    <div>
        <div class="dialog overlay" id="my-dialog">
            <a href="#" class="overlay-close"></a>

            <div class="dialog-body">
                <a class="dialog-close-btn" href="#">&times;</a>
                <h3 class="title">Nhập Public Key</h3>
                <div class="group-input">
                    <label>Nhập Key:</label><input class="input-key" id="input-key" type="text" name="">

                </div>
                <div id="message">


                </div>
                <div class="container-button">
                    <button class="btn btn-success" onclick="importKey()" style="margin-right: 10px;">Nhập key</button>
                    <a class="btn btn-secondary" href="#" }>Trở về</a>
                </div>
            </div>
        </div>
    </div>

    <div>
        <div class="dialog overlay" id="dialog">
            <a href="#" class="overlay-close"></a>

            <div class="dialog-body">
                <a class="dialog-close-btn" href="#">&times;</a>
                <h3 class="title">Tạo Public Key</h3>
                <div class="group-input">
                    <label>Chọn File Private Key:</label><input id="file" class="input-key" type="file" name="file" enctype='multipart/form-data' accept=".bin"  required >
                </div>
                <div id="messgae-private">


                </div>
                <div class="container-button">
                    <button class="btn btn-success" onclick="createPublicKey()"  style="margin-right: 10px;">Tạo key</button>
                    <a class="btn btn-secondary" href="#">Trở về</a>
                </div>
            </div>
        </div>
    </div>

</div>

</body>

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
<script type="text/javascript">$('#sampleTable').DataTable();</script>

<script data-cfasync="false" src="/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script>
<script src="./assets/js/jquery-3.6.0.min.js"></script>
<script src="./assets/js/chain_fade.js"></script>
<script src="./assets/js/bootstrap.bundle.min.js"></script>
<script src="./assets/js/owl.carousel.min.js"></script>
<script src="./assets/js/swiper-bundle.min.js"></script>
<script src="./assets/js/jquery.fancybox.min.js"></script>
<script src="./assets/js/select2.min.js"></script>
<script src="./assets/js/jquery-ui.js"></script>

<script src="./assets/js/main.js"></script>
<script src="./assets/js/custom-swiper.js"></script>
<script src="//cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
<script src="https://unpkg.com/boxicons@2.1.4/dist/boxicons.js"></script>

<script>
    function toast(title,message){
        const main = document.getElementById('toast_message');
        if(main){
            const toast = document.createElement('div');
            toast.classList.add('toast-item');
            toast.style.animation = ` fadeIn ease 0.3s,fadeOut linear 1s 2s forwards`;
            toast.innerHTML =`
					<div class="toast__icon"><i class="fa-solid fa-bug icon-error"></i></div>
					<div class="toast__body">
						<h3 class="toast__title"> ${title} </h3>
						<p class="toast__msg">${message}</p>
					</div>
					<div class="toast__close"><i class="fa-solid fa-xmark"></i></div>
				`;
            main.appendChild(toast);
            setTimeout(() => {
                main.removeChild(toast);
            }, 2000);
        }



    }


    function importKey(){
        var key = document.getElementById("input-key");

        $.ajax({
            url : "<%=request.getContextPath()+"/user/views/ImportKey"%>",
            type:"POST",
            data:{
                key:key.value
            },
            success: function(data){
                    console.log(data)

                console.log(typeof  data)
                console.log(data == 0)
                if(data == 0) {
                    toast("Lỗi","Key không phù hợp hoặc có key đang sử dụng");

                }else {
                    location.href = "<%=request.getContextPath()%>/user/views/ManagerKey";
                }
                // toast();

            }
        });


    }

    function createPublicKey(){
        // var key = document.getElementById("fileKey");

        var fileInput = document.getElementById('file');
        var file = fileInput.files[0];

        var formData = new FormData();
        formData.append('file', file);

        if(file == undefined){
            toast("Lỗi","Vui lòng chọn File")
        }else {
            $.ajax({
                url : "<%=request.getContextPath()+"/user/views/CreateByPrivate"%>",
                type:"POST",
                encType : "multipart/form-data",
                data: formData,
                contentType: false,
                processData: false,
                success: function(data){
                    console.log(data)

                    console.log(typeof  data)
                    console.log(data == 0)
                    if(data == 0) {
                        toast("Lỗi","Có lỗi xảy ra vui lòng thử lại sau");

                    }else if(data == 2) {
                        toast("Lỗi","Có Key đang được sử dụng");
                    }else {
                        location.href = "<%=request.getContextPath()%>/user/views/ManagerKey";
                    }
                    // toast();

                }
            });
        }




    }


    function thanks() {
        setTimeout(function () {
            document.location.pathname = "<%=request.getContextPath()%>/user/views/ManagerKey";
        }, 500);
    }

    function createKey(){
        $.ajax({
            url : "<%=request.getContextPath()+"/user/views/CreateKey"%>",
            type:"POST",
            data:{

            },
            success: function(data){
                console.log(data)

                console.log(typeof  data)
                console.log(data == 0)
                if(data == 0) {
                    toast("Lỗi","Có key đang được sử dụng");
                    console.log("Đaya")

                }else {
                    <%--location.href = "<%=request.getContextPath()%>/user/views/ManagerKey";--%>
                }
                // toast();

            }
        });
    }

    function lostKeyFunction() {
        alert("Bạn muốn thông báo mất key!");
    }
</script>

</script>
<script>



</script>
</html>
