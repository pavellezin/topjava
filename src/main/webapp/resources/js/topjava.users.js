function enable(chkbox, id) {
    var enabled = chkbox.is(":checked");
    var userAjaxUrl = "ajax/admin/users/";
    var trId = "tr-" + id;
    var tr = document.getElementById(trId);
    var userName = tr.getElementsByTagName("td")[0].textContent;
    $.ajax({
        url: userAjaxUrl + id,
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        tr.setAttribute("data-userEnabled", enabled);
        successNoty(enabled ? userName + " enabled" : userName + " disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
}

$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            }),
            updateTable: function () {
                $.get("ajax/admin/users/", updateTableByData);
            }
        }
    );
});