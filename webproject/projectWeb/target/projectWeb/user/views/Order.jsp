<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.bean.Booking" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html >

<head>
    <title>TravelLab - Khám phá đất nước của chúng ta</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="./assets/images/favicon.png" type="image/gif" sizes="20x20">

    <link rel="stylesheet" href="./assets/css/select2.min.css">

    <link rel="stylesheet" href="./assets/css/jquery.fancybox.min.css">

    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

    <link rel="stylesheet" href="./assets/css/swiper-bundle.min.css">

    <link rel="stylesheet" href="./assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="./assets/css/animate.min.css">

    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet" href="./assets/css/responsive.css">
    <link rel="stylesheet" href="./assets/css/profile.css">
    <link rel="stylesheet" href="./assets/css/box.css">
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
    <style>
        .nav-link.active{
            background-color: var(--c-primary);
            color: white;
            border-radius: 15px;
        }
    </style>
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
                                <jsp:param name="isCurrent" value="order"/>
                            </jsp:include>

                            <% List<Booking> listBooking = request.getAttribute("listBooking")==null?null:(List<Booking>) request.getAttribute("listBooking");
                                List<Booking> listCancelBooking = new ArrayList<Booking>();
                                List<Booking> listWaitBooking = new ArrayList<Booking>();
                                List<Booking> listCompleteBooking = new ArrayList<Booking>();
                                for (Booking b:
                                     listBooking) {
                                    if (b.getTRANGTHAI() ==0 ) listWaitBooking.add(b);
                                    if (b.getTRANGTHAI() ==-1 ) listCancelBooking.add(b);
                                    if (b.getTRANGTHAI() ==1 ) listCompleteBooking.add(b);
                                }

                            %>
    
                            <div class="ps-md-4">
                                <div class="wrapper order">
                                    <div class="tab">
                                        <ul class="nav py-3 tab flex-sm-row mb-3" role="tablist">
                                            <li class="nav-item flex-sm-fill text-sm-center" style="cursor: pointer;" role="presentation">
                                                <a class="nav-link active" id="pills-all-tab"  data-bs-toggle="pill" data-bs-target="#pills-all" role="tab" aria-controls="pills-all" aria-selected="true">Tất cả</a>
                                            </li>
                                            <li class="nav-item flex-sm-fill text-sm-center" style="cursor: pointer;" role="presentation">
                                                <a class="nav-link " id="pills-destroy-tab"  data-bs-toggle="pill" data-bs-target="#pills-destroy" role="tab" aria-controls="pills-destroy" aria-selected="false">Đã hủy</a>
                                            </li>
                                            
                                            <li class="nav-item flex-sm-fill text-sm-center" style="cursor: pointer;" role="presentation">
                                                <a class="nav-link" id="pills-wait-tab" data-bs-toggle="pill" data-bs-target="#pills-wait" role="tab" aria-controls="pills-wait" aria-selected="false">Chờ xác nhận</a>
                                            </li>
                                            <li class="nav-item flex-sm-fill text-sm-center" style="cursor: pointer;" role="presentation">
                                                <a class="nav-link" id="pills-complete-tab" data-bs-toggle="pill" data-bs-target="#pills-complete" role="tab" aria-controls="pills-complete" aria-selected="false">Đã đặt</a>
                                            </li>
                                        </ul>
                                        <div class="form-search mb-4">
                                            <form action="#" method="get">
                                                <input id="myInput" onkeyup="myFunctionSearch()" class="form-control px-5" type="text" placeholder="Tìm kiếm theo tên tour/ tourCode hoặc số booking" aria-label="default input example" />
                                                <i class="icon icon--search"></i>
                                            </form>
                                        </div>
                                        <div class="tab-content" id="pills-tabContent">
                                            <div class="tab-pane result fade show active " id="pills-all" role="tabpanel" aria-labelledby="pills-all-tab">
                                               <div class="tour-list" >
                                                   <%
                                                       for (Booking b :
                                                               listCompleteBooking) {
                                                           String[] beginDateArr= b.getNgayKhoiHanh().toString().split("-");
                                                           String beginDate = "Ngày "+beginDateArr[2]+" Tháng "+beginDateArr[1]+", "+beginDateArr[0];
                                                           String[] endDateArr= b.getNgayKetThuc().toString().split("-");
                                                           String endDate = "Ngày "+endDateArr[2]+" Tháng "+endDateArr[1]+", "+endDateArr[0];
                                                           Locale locale = new Locale("vi");
                                                           NumberFormat format =  NumberFormat.getCurrencyInstance(locale);
                                                           String thanhTienString = format.format(b.getTongTien()).split(",")[0];

                                                   %>

                                                   <div class="tour-item">
                                                       <div class="tour-item-first-side">
                                                           <span>Id Booking: <span><%=b.getBOOKING_ID()%></span></span>
                                                           <span><%=b.getTOUR_ID()+": "+b.getTourName()%></span>
                                                       </div>
                                                       <div class="tour-item-second-side">
                                                           <div class="tour-item-second-side-start" style="margin-top: 15px">
                                                               <span >Bắt đầu chuyến đi</span>
                                                               <p class="time" ><%=beginDate%></p>
                                                           </div>
                                                           <div class="tour-item-second-side-end">
                                                               <span >Kết thúc chuyến đi</span>
                                                               <p class="time" ><%=endDate%></p>
                                                           </div>
                                                       </div>
                                                       <div class="tour-item-third-side">
                                                           <p class="add-more"style="font-size: 12px;" ><span ><%=b.getSOLUONG_VENGUOILON()%> người lớn</span> <span ><%=b.getSOLUONG_VETREEM()%> trẻ em</span></p>
                                                           <span>Thành tiền: <span><%=thanhTienString%></span><span>₫</span></span>
                                                       </div>
                                                       <div class="tour-item-four-side">
                                                           <div class="bound " >
                                                               <span style="font-size: 14px;color: green;font-weight: bold">Đã đặt</span>
                                                           </div>
                                                       </div>
                                                   </div>
                                                   <%}%>

                                                   <%
                                                       for (Booking b :
                                                               listWaitBooking) {
                                                           String[] beginDateArr= b.getNgayKhoiHanh().toString().split("-");
                                                           String beginDate = "Ngày "+beginDateArr[2]+" Tháng "+beginDateArr[1]+", "+beginDateArr[0];
                                                           String[] endDateArr= b.getNgayKetThuc().toString().split("-");
                                                           String endDate = "Ngày "+endDateArr[2]+" Tháng "+endDateArr[1]+", "+endDateArr[0];
                                                           Locale locale = new Locale("vi");
                                                           NumberFormat format =  NumberFormat.getCurrencyInstance(locale);
                                                           String thanhTienString = format.format(b.getTongTien()).split(",")[0];

                                                   %>

                                                   <div class="tour-item">
                                                       <div class="tour-item-first-side">
                                                           <span>Id Booking: <span><%=b.getBOOKING_ID()%></span></span>
                                                           <span><%=b.getTOUR_ID()+": "+b.getTourName()%></span>
                                                       </div>
                                                       <div class="tour-item-second-side">
                                                           <div class="tour-item-second-side-start" style="margin-top: 15px">
                                                               <span >Bắt đầu chuyến đi</span>
                                                               <p class="time" ><%=beginDate%></p>
                                                           </div>
                                                           <div class="tour-item-second-side-end">
                                                               <span >Kết thúc chuyến đi</span>
                                                               <p class="time" ><%=endDate%></p>
                                                           </div>
                                                       </div>
                                                       <div class="tour-item-third-side">
                                                           <p class="add-more"style="font-size: 12px;" ><span ><%=b.getSOLUONG_VENGUOILON()%> người lớn</span> <span ><%=b.getSOLUONG_VETREEM()%> trẻ em</span></p>
                                                           <span>Thành tiền: <span><%=thanhTienString%></span><span>₫</span></span>
                                                           <div class="message-status"></div>
                                                       </div>
                                                       <div class="tour-item-four-side">

                                                           <span style="font-size: 12px;color: blue;font-weight: bold">Chờ xác nhận</span>

                                                           <input class="bookingId-current" type="hidden" name="bookingId" value="<%=b.getBOOKING_ID()%>">
                                                           <a class="btn btn-primary btn-sm" style="margin: 5px 0 5px 0; border-radius: 5px;" href="#dialog" onclick="openModal('<%=b.getBOOKING_ID()%>')" title="Thêm">
                                                           Ký hoá đơn</a>

                                                           <a class="bound dahuy" style="width: 90px; border-radius: 5px" href="/projectWeb_war/user/views/order?cancel=<%=b.getBOOKING_ID()%>">
                                                               <span>Hủy đơn</span>
                                                           </a>

                                                       </div>
                                                   </div>
                                                   <%}%>
                                                   <%
                                                       for (Booking b :
                                                               listCancelBooking) {
                                                           String[] beginDateArr= b.getNgayKhoiHanh().toString().split("-");
                                                           String beginDate = "Ngày "+beginDateArr[2]+" Tháng "+beginDateArr[1]+", "+beginDateArr[0];
                                                           String[] endDateArr= b.getNgayKetThuc().toString().split("-");
                                                           String endDate = "Ngày "+endDateArr[2]+" Tháng "+endDateArr[1]+", "+endDateArr[0];
                                                           Locale locale = new Locale("vi");
                                                           NumberFormat format =  NumberFormat.getCurrencyInstance(locale);
                                                           String thanhTienString = format.format(b.getTongTien()).split(",")[0];

                                                   %>

                                                   <div class="tour-item">
                                                       <div class="tour-item-first-side">
                                                           <span>Id Booking: <span><%=b.getBOOKING_ID()%></span></span>
                                                           <span><%=b.getTOUR_ID()+": "+b.getTourName()%></span>
                                                       </div>
                                                       <div class="tour-item-second-side">
                                                           <div class="tour-item-second-side-start" style="margin-top: 15px">
                                                               <span >Bắt đầu chuyến đi</span>
                                                               <p class="time" ><%=beginDate%></p>
                                                           </div>
                                                           <div class="tour-item-second-side-end">
                                                               <span >Kết thúc chuyến đi</span>
                                                               <p class="time" ><%=endDate%></p>
                                                           </div>
                                                       </div>
                                                       <div class="tour-item-third-side">
                                                           <p class="add-more"style="font-size: 12px;" ><span ><%=b.getSOLUONG_VENGUOILON()%> người lớn</span> <span ><%=b.getSOLUONG_VETREEM()%> trẻ em</span></p>
                                                           <span>Thành tiền: <span><%=thanhTienString%></span><span>₫</span></span>
                                                       </div>
                                                       <div class="tour-item-four-side">
                                                           <div class="bound " >
                                                               <span style="font-size: 14px;color: red;font-weight: bold">Đã Hủy</span>
                                                           </div>
                                                       </div>
                                                   </div>
                                                   <%}%>
                                                   
                                               </div>
                                            </div>
                                            <div class="tab-pane  fade  " id="pills-destroy" role="tabpanel" aria-labelledby="pills-destroy-tab">
                                                <div class="tour-list" >

                                                    <%
                                                        for (Booking b :
                                                                listCancelBooking) {
                                                            String[] beginDateArr= b.getNgayKhoiHanh().toString().split("-");
                                                            String beginDate = "Ngày "+beginDateArr[2]+" Tháng "+beginDateArr[1]+", "+beginDateArr[0];
                                                            String[] endDateArr= b.getNgayKetThuc().toString().split("-");
                                                            String endDate = "Ngày "+endDateArr[2]+" Tháng "+endDateArr[1]+", "+endDateArr[0];
                                                            Locale locale = new Locale("vi");
                                                            NumberFormat format =  NumberFormat.getCurrencyInstance(locale);
                                                            String thanhTienString = format.format(b.getTongTien()).split(",")[0];

                                                    %>

                                                    <div class="tour-item">
                                                        <div class="tour-item-first-side">
                                                            <span>Id Booking: <span><%=b.getBOOKING_ID()%></span></span>
                                                            <span><%=b.getTOUR_ID()+": "+b.getTourName()%></span>
                                                        </div>
                                                        <div class="tour-item-second-side">
                                                            <div class="tour-item-second-side-start" style="margin-top: 15px">
                                                                <span >Bắt đầu chuyến đi</span>
                                                                <p class="time" ><%=beginDate%></p>
                                                            </div>
                                                            <div class="tour-item-second-side-end">
                                                                <span >Kết thúc chuyến đi</span>
                                                                <p class="time" ><%=endDate%></p>
                                                            </div>
                                                        </div>
                                                        <div class="tour-item-third-side">
                                                            <p class="add-more"style="font-size: 12px;" ><span ><%=b.getSOLUONG_VENGUOILON()%> người lớn</span> <span ><%=b.getSOLUONG_VETREEM()%> trẻ em</span></p>
                                                            <span>Thành tiền: <span><%=thanhTienString%></span><span>₫</span></span>
                                                        </div>
                                                        <div class="tour-item-four-side">
                                                            <div class="bound " >
                                                                <span style="font-size: 14px;color: red;font-weight: bold">Đã Hủy</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <%}%>




                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="pills-wait" role="tabpanel" aria-labelledby="pills-wait-tab">
                                                <div class="tour-list" >
                                                    <%
                                                        for (Booking b :
                                                                listWaitBooking) {
                                                            String[] beginDateArr= b.getNgayKhoiHanh().toString().split("-");
                                                            String beginDate = "Ngày "+beginDateArr[2]+" Tháng "+beginDateArr[1]+", "+beginDateArr[0];
                                                            String[] endDateArr= b.getNgayKetThuc().toString().split("-");
                                                            String endDate = "Ngày "+endDateArr[2]+" Tháng "+endDateArr[1]+", "+endDateArr[0];
                                                            Locale locale = new Locale("vi");
                                                            NumberFormat format =  NumberFormat.getCurrencyInstance(locale);
                                                            String thanhTienString = format.format(b.getTongTien()).split(",")[0];

                                                    %>

                                                    <div class="tour-item">
                                                        <div class="tour-item-first-side">
                                                            <span>Id Booking: <span><%=b.getBOOKING_ID()%></span></span>
                                                            <span><%=b.getTOUR_ID()+": "+b.getTourName()%></span>
                                                        </div>
                                                        <div class="tour-item-second-side">
                                                            <div class="tour-item-second-side-start" style="margin-top: 15px">
                                                                <span >Bắt đầu chuyến đi</span>
                                                                <p class="time" ><%=beginDate%></p>
                                                            </div>
                                                            <div class="tour-item-second-side-end">
                                                                <span >Kết thúc chuyến đi</span>
                                                                <p class="time" ><%=endDate%></p>
                                                            </div>
                                                        </div>
                                                        <div class="tour-item-third-side">
                                                            <p class="add-more"style="font-size: 12px;" ><span ><%=b.getSOLUONG_VENGUOILON()%> người lớn</span> <span ><%=b.getSOLUONG_VETREEM()%> trẻ em</span></p>
                                                            <span>Thành tiền: <span><%=thanhTienString%></span><span>₫</span></span>
                                                        </div>
                                                        <div class="tour-item-four-side">

                                                            <span style="font-size: 12px;color: blue;font-weight: bold">Chờ xác nhận</span>

                                                            <input class="bookingId-current" type="hidden" name="bookingId" value="<%=b.getBOOKING_ID()%>">
                                                            <a class="btn btn-primary btn-sm" style="margin: 5px 0 5px 0; border-radius: 5px;" href="#dialog" onclick="openModal('<%=b.getBOOKING_ID()%>')" title="Thêm">
                                                                Ký hoá đơn</a>

                                                            <a class="bound dahuy" style="width: 90px; border-radius: 5px;" href="/projectWeb_war/user/views/order?cancel=<%=b.getBOOKING_ID()%>" >
                                                                <span>Hủy đơn</span>
                                                            </a>

                                                        </div>
                                                    </div>
                                                    <%}%>


                                                   
                                                    
                                                   
                                               </div>
                                            </div>

                                            <div class="tab-pane fade" id="pills-complete" role="tabpanel" aria-labelledby="pills-complete-tab">
                                                <div class="tour-list" >
                                                    <%
                                                        for (Booking b :
                                                             listCompleteBooking) {
                                                            String[] beginDateArr= b.getNgayKhoiHanh().toString().split("-");
                                                            String beginDate = "Ngày "+beginDateArr[2]+" Tháng "+beginDateArr[1]+", "+beginDateArr[0];
                                                            String[] endDateArr= b.getNgayKetThuc().toString().split("-");
                                                            String endDate = "Ngày "+endDateArr[2]+" Tháng "+endDateArr[1]+", "+endDateArr[0];
                                                            Locale locale = new Locale("vi");
                                                            NumberFormat format =  NumberFormat.getCurrencyInstance(locale);
                                                            String thanhTienString = format.format(b.getTongTien()).split(",")[0];

                                                        %>

                                                    <div class="tour-item">
                                                        <div class="tour-item-first-side">
                                                            <span>Id Booking: <span><%=b.getBOOKING_ID()%></span></span>
                                                            <span><%=b.getTOUR_ID()+": "+b.getTourName()%></span>
                                                        </div>
                                                        <div class="tour-item-second-side">
                                                            <div class="tour-item-second-side-start" style="margin-top: 15px">
                                                                <span >Bắt đầu chuyến đi</span>
                                                                <p class="time" ><%=beginDate%></p>
                                                            </div>
                                                            <div class="tour-item-second-side-end">
                                                                <span >Kết thúc chuyến đi</span>
                                                                <p class="time" ><%=endDate%></p>
                                                            </div>
                                                        </div>
                                                        <div class="tour-item-third-side">
                                                            <p class="add-more"style="font-size: 12px;" ><span ><%=b.getSOLUONG_VENGUOILON()%> người lớn</span> <span ><%=b.getSOLUONG_VETREEM()%> trẻ em</span></p>
                                                            <span>Thành tiền: <span><%=thanhTienString%></span><span>₫</span></span>
                                                        </div>
                                                        <div class="tour-item-four-side">
                                                            <div class="bound " >
                                                                <span style="font-size: 14px;color: green;font-weight: bold">Đã đặt</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <%}%>

                                               </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
    
                        </div>
                    </div>
                </div>
            </main>
        </main>

        <div id="toast_message">

        </div>

        <div id="pop-dialog" data-booking-id="">
            <div class="dialog overlay" id="dialog">
                <a href="#" class="overlay-close"></a>

                <div class="dialog-body">
                    <a class="dialog-close-btn" href="#">&times;</a>
                    <h3 class="title">Ký hoá đơn</h3>
                    <div class="group-input">
                        <label>Chọn File Private Key:</label><input id="file" class="input-key" type="file" name="file" enctype='multipart/form-data' accept=".bin"  required >
                    </div>
<%--                    <div id="message-status"></div>--%>
                    <div class="container-button">
                        <button class="btn btn-success" onclick="sendBookingIdAndVerifyKey()" style="margin-right: 10px;">Ký hoá đơn</button>
                        <a class="btn btn-secondary" href="#">Trở về</a>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <%@include file="./components/footer.jsp"%>



    <script data-cfasync="false" src="/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script>
    <script src="./assets/js/jquery-3.6.0.min.js"></script>
    <script src="./assets/js/bootstrap.bundle.min.js"></script>
    <script src="./assets/js/chain_fade.js"></script>
    <script src="./assets/js/owl.carousel.min.js"></script>
    <script src="./assets/js/swiper-bundle.min.js"></script>
    <script src="./assets/js/jquery.fancybox.min.js"></script>
    <script src="./assets/js/select2.min.js"></script>
    <script src="./assets/js/jquery-ui.js"></script>

    <script src="./assets/js/main.js"></script>
    <script src="./assets/js/profile.js"></script>
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

        function openModal(bookingId) {
            $('#pop-dialog').attr('data-booking-id', bookingId);
        }

        function sendBookingIdAndVerifyKey() {
            var bookingId = $('#pop-dialog').attr('data-booking-id');

            // Create FormData object and append file and bookingId
            var formData = new FormData();
            formData.append("bookingId", bookingId);
            formData.append("file", $("#file")[0].files[0]);

            $.ajax({
                type: 'POST',
                url: '<%=request.getContextPath()%>/user/views/VerifyKey',
                encType: "multipart/form-data",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    if (response.status === 'success') {
                        console.log('ID Booking and file sent successfully');
                        toast("OK", "Ký hoá đơn thành công");
                        $(".message-status").text("Ký thành công").removeClass("error-message");
                    } else if (response.status === 'error') {
                        console.error('Error sending ID Booking and file:', response.error);
                        toast("Lỗi", "Ký hoá đơn không thành công");
                        $(".message-status").text("Ký không thành công").addClass("error-message");
                    } else {
                        console.error('Unexpected response:', response);
                        toast("Lỗi", "Ký hoá đơn không thành công");
                        $(".message-status").text("Lỗi không thể ký").addClass("error-message");
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.error("AJAX Error:", textStatus, errorThrown);
                    console.error("Server Response:", jqXHR.responseText);
                    toast("Lỗi", "Server đang gặp vấn đề");
                    $(".message-status").text("Lỗi server").addClass("error-message");
                }
            });
        }


    </script>
</body>

</html>