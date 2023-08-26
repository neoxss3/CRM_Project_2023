/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    $('.btn-delete').click(function () {
        var id = $(this).attr("role-id")
        var This = $(this)
        $.ajax({
            method: "GET",
            url: `http://localhost:8084/PROJECT_CRM/api/role/delete?role_id=${id}`,
            //data: {name: "John", location: "Boston"}
        })
                .done(function (result) {
                    if (result.data == true) {
                        This.closest('tr').remove();
                    }
                });
    })
});

