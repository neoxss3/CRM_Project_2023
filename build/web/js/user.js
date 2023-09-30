/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    // Lưu các giá trị ban đầu của các thẻ span
    var initialValues = {};
    // Bắt sự kiện khi ấn nút "Sửa" hoặc "Lưu"
    $(".btn-update").click(function () {
        var buttonText = $(this).text();

        if (buttonText === "Sửa") {
            // Lưu các giá trị ban đầu của các thẻ span
            $(".personal span").each(function () {
                var id = $(this).attr("id");
                var value = $(this).text();
                initialValues[id] = value;
            });

            // Chuyển nút thành "Lưu"
            $(this).text("Lưu");

            // Hiển thị các thẻ input để chỉnh sửa trong div có class "personal"
            $(".personal span").each(function () {
                var inputField = $("<input type='text' id='" + $(this).attr("id") + "' value='" + $(this).text() + "' />");
                $(this).replaceWith(inputField);
            });
            // Đổi thẻ <span> có id="role" thành thẻ <select>
            var selectField = $("<select id='role'></select>");

            // Thêm các tùy chọn từ danh sách vai trò
            <c:forEach items="${LIST_ROLE}" var="lr">
                var option = $("<option value='${lr.getId()}'>${lr.getName()}</option>");
                selectField.append(option);
            </c:forEach>

            // Thay thế thẻ <span> bằng thẻ <select>
            $("#role").replaceWith(selectField);
        } else if (buttonText === "Lưu") {
            // Lấy giá trị từ các ô input sau khi người dùng đã chỉnh sửa
            var userId = $(".personal input#userID").val();
            var fullName = $(".personal input#fullName").val();
            var email = $(".personal input#email").val();
            var password = $(".personal input#password").val();
            var address = $(".personal input#address").val();
            var phone = $(".personal input#phone").val();
            var roleId = $(".personal input#role").val();
            // Gọi API để cập nhật giá trị
            var This = $(this)
            This.off('click')
            This.click(function () {
                $.ajax({
                    method: "PUT",
                    url: `http://localhost:8084/PROJECT_CRM/api/user/update?userid=${userId}&fullname=${fullName}&email=${email}&password=${password}&address=${address}&phone=${phone}&roleid=${roleId}`,
                })
                        .done(function (result) {
                            if (result.data === true) {
                                $(this).text("Sửa");

                                // Lấy giá trị từ các thẻ input và cập nhật lại các thẻ span trong div có class "personal"
                                $(".personal input").each(function () {
                                    var spanText = $(this).val();
                                    var spanElement = $("<span>" + spanText + "</span>");
                                    $(this).replaceWith(spanElement);
                                });
                            } else {
                                // Nếu apiResult là false, thay đổi nút thành "Sửa" và cập nhật lại các thẻ span với giá trị ban đầu
                                alert("update failed")
                                $(this).text("Sửa");
                                for (var id in initialValues) {
                                    $("#" + id).text(initialValues[id]);
                                }
                            }
                        });
            })


        }
    });
});