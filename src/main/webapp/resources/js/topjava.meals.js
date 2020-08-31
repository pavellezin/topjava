// var filter = $("#filter-form").serialize();
function applyFilter(){
        var dataSet = $("#filter-form").serialize();
        $.ajax({
            type: "GET",
            url: "ajax/profile/meals/filter",
            data: dataSet
            // ,success: updateTable()
        }).done(function () {
            updateTable();
            successNoty("Updated");
        });
}
$(function () {
    makeEditable({
            ajaxUrl: "ajax/profile/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": false,
                "searching":false,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
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
                        "desc"
                    ]
                ]
            })
        }
    );
});