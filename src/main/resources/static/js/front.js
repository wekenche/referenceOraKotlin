function confirmDelete(url) {
  if (confirm('削除してもよろしいでしょうか？')) {
    location.href = url;
  }
  return false;
}

function confirmDeleteImage(url) {
    $("#deleteDialog").dialog({
        modal: true,
        title: "削除をしても宜しいでしょうか。",
        buttons: {
            "OK": function () {
                    $.ajax({
                        type : "DELETE",
                        url : url,
                        success: function (result) {
                            $("#deleteDialog").dialog("close");
                            $(".attachment").css('display', 'none');
                        },
                        error: function (e) {
                            console.log(e);
                        }
                    });
            },
            "キャンセル": function () {
                $(this).dialog("close");
            }
        },
    });

  return false
}
function confirmDeleteFile(url,element) {
    $("#deleteDialog").dialog({
        modal: true,
        title: "削除をしても宜しいでしょうか。",
        buttons: {
            "OK": function () {
                $.ajax({
                    type : "DELETE",
                    url : url,
                    success: function (result) {
                        $("#deleteDialog").dialog("close");
                        element.remove()
                    },
                    error: function (e) {
                        console.log(e);
                    }
                });
            },
            "キャンセル": function () {
                $(this).dialog("close");
            }
        },
    });

    return false
}

function confirmAction(url,title) {
    $("#confirmDialog").dialog({
        modal: true,
        title: title,
        buttons: {
            "OK": function () {
                location.href = url;
            },
            "キャンセル": function () {
                $(this).dialog("close");
            }
        },
    });

    return false
}
