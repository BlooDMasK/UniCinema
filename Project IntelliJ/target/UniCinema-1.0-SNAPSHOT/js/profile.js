$("#profile-edit-confirm").click(function(e) {
    var btnConfirm = document.getElementById("profile-edit-confirm");

    if(btnConfirm.getAttribute("value") == 'Modifica') {
        $(".profile-info-label").each(function () {
            $(this).hide();
        })

        $(".profile-info-edit").each(function () {
            $(this).show();
        })

        $("#profile-edit-cancel").show();

        $("#profile-edit-form").get(0).reset();

        btnConfirm.setAttribute("value", "Conferma");
    } else
        btnConfirm.setAttribute("type", "submit");
})

$("#profile-edit-cancel, #list-purchases-list, #list-info-list").click(function() {

    $(".profile-info-label").each(function() {
        $(this).show();
    })

    $(".profile-info-edit").each(function() {
        $(this).hide();
    })

    $("#profile-edit-cancel").hide();

    $(".alert").hide();

    document.getElementById("profile-edit-confirm").setAttribute("value", "Modifica");
    document.getElementById("profile-edit-confirm").setAttribute("type", "button");
})

$("#list-purchases-list").click(function() {
    $("#profile-edit-cancel, #profile-edit-confirm").hide();
})

$("#list-info-list").click(function() {
    $("#profile-edit-confirm").show();
})

$(".need-validation").submit(function(e) {
    var dataString = $(this).serialize();
    console.log(contextPath);

    $.ajax({
        type: "Post",
        url: contextPath+"/account/edit",
        async: true,
        data: dataString,
        dataType: 'json',
        success: function(response) {
            console.log(response["alert"].type);

            let alert = response["alert"];

            $(".alert").removeClass("alert-danger alert-success alert-");
            $(".alert").addClass("alert-"+alert.type);

            $("#alert-close, .alert-item").remove();

            let string = "";

            for(let i = 0; i < alert.messages.length; i++)
                string += "<li class='.alert-item'>"+alert.messages[i]+"</li>";

            $(".alert ol").html(string);
            $(".alert").show();

            if(alert.type == 'success') {
                let formElements = $("#profile-edit-form").serializeArray();

                if (formElements[0].value)
                    $("#profile-info-firstname, #profile-info-title-firstname").html(formElements[0].value+"&nbsp;");

                if (formElements[1].value)
                    $("#profile-info-lastname, #profile-info-title-lastname").html(formElements[1].value);

                if (formElements[2].value)
                    $("#profile-info-email").html(formElements[2].value);

                $("#profile-edit-form").get(0).reset();
            }
        }
    });

    e.preventDefault();
})

let purchases = urlObject.searchParams.get("purchases");

if(purchases == 1) {
    $("#list-purchases-list").click();

    $("#list-info").removeClass("show active");
    $("#list-purchases").addClass("show active");

    $("#list-info-list").removeClass("active");
    $("#list-purchases-list").addClass("active");
}

let edit = urlObject.searchParams.get("edit");

if(edit == 1) {
    $("#profile-edit-confirm").click();
}