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
    });
    $('.btn-update').click(function () {
        var This = $(this);
        const tdElements = This.closest('tr').find('td');
        const currentId = tdElements.eq(0).text();
        const currentName = tdElements.eq(1).text();
        const currentDesc = tdElements.eq(2).text();

        const nameInput = document.createElement('input');
        nameInput.type = 'text';
        nameInput.value = currentName;

        const descInput = document.createElement('input');
        descInput.type = 'text';
        descInput.value = currentDesc;

        // Thay thế td bằng input
        tdElements.eq(1).html('');
        tdElements.eq(1).append(nameInput);

        tdElements.eq(2).html('');
        tdElements.eq(2).append(descInput);

        // Thay nút "Sửa" thành nút "Lưu"
        This.text('Lưu');
        This.off('click'); // Tắt sự kiện click cũ để tránh gọi nhiều lần

        This.click(function () {
            const newName = nameInput.value;
            const newDesc = descInput.value;

            // Gọi API để cập nhật giá trị
            $.ajax({
                method: "PUT",
                url: `http://localhost:8084/PROJECT_CRM/api/role/update?role_id=${currentId}&role_name=${newName}&role_desc=${newDesc}`,
            })
                    .done(function (result) {
                        if (result.data == true) {
                            // Cập nhật thành công, thay thế input bằng các giá trị mới
                            tdElements.eq(1).html(newName);
                            tdElements.eq(2).html(newDesc);

                            // Thay nút "Lưu" thành nút "Sửa"
                            This.text('Sửa');
                            This.off('click');
                            This.click(function () {
                                // Gọi lại hàm để chỉnh sửa
                                // ...
                            });
                        }
                    });
        });
    });
})
