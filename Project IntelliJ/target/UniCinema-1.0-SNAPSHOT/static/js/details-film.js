$(document).ready(function () {
    if(accountIsAdministrator) {

        $(".film-title").append("<a href='"+contextPath+"/film-manager/remove?filmId="+filmId+"' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                                    <img src='"+contextPath+"/static/icons/minus.svg'> Rimuovi\
                                 </a>\
                                 <a href='"+contextPath+"/film-manager/update?filmId="+filmId+"' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                                    <img src='"+contextPath+"/static/icons/edit.svg'> Modifica\
                                 </a>");

        $("#showForm").append("<hr class='text-light'>\
                                        <p class='fs-3' id='showFormTitle'></p>\
                                        <input type='hidden' name='film-length' value='"+filmLength+"'>\
                                        <input type='hidden' name='filmId' value='"+filmId+"'>\
                                        <div class='mb-3'>\
                                            <label for='room' class='form-label text-light fs-1-5 fw-light'>Sala</label>\
                                            <select name='room' class='form-select form-control rounded-3 fs-1-5 fw-light' id='room' required>\
                                                <option value='1' selected>Sala 1</option>\
                                                <option value='2'>Sala 2</option>\
                                                <option value='3'>Sala 3</option>\
                                                <option value='4'>Sala 4</option>\
                                                <option value='5'>Sala 5</option>\
                                            </select>\
                                            <div class='custom-feedback'>\
                                            </div>\
                                        </div>\
                \
                                        <div class='mb-3'>\
                                            <label for='date' class='form-label text-light fs-1-5 fw-light'>Data dello spettacolo</label>\
                                            <input name='date' type='date' min='"+dateNow+"' class='form-control rounded-3 fs-1-5 fw-light' id='date' required>\
                                            <div class='custom-feedback'>\
                                            </div>\
                                        </div>\
                \
                                        <div class='mb-3'>\
                                            <label for='time' class='form-label text-light fs-1-5 fw-light'>Orario</label>\
                                            <select name='time' class='form-select form-control rounded-3 fs-1-5 fw-light' id='time' required>\
                \
                                            </select>\
                                            <div class='custom-feedback'>\
                                            </div>\
                                        </div>\
                \
                                        <button type='submit' class='btn btn-light' id='showFormButton'>Aggiungi Spettacolo</button>\
                                    ");

        $(".film-spectacle").append("<button type='button' id='buttonAdd' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1' data-bs-toggle='collapse' data-bs-target='#showForm' aria-expanded='false' aria-controls='showForm'>\
                                        <img src='"+contextPath+"/static/icons/plus.svg'> Aggiungi\
                                     </button>");

        if(modifyShow) {
            $(".film-spectacle").append("<button type='button' id='buttonEdit' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                                            <img src='" + contextPath + "/static/icons/edit.svg'> Modifica\
                                         </button>");

            $(".film-spectacle").append("<button type='button' id='buttonRemove' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                                            <img src='" + contextPath + "/static/icons/minus.svg'> Rimuovi\
                                         </button>");
        }
    }
});

//Clicco sul pulsante di rimozione
let isAdminRemovingShow = false;
$(document).on("click", "#buttonRemove", function() {
    if(isAdminRemovingShow) {
        $("#removing-info").remove();
        $("#buttonAdd, #buttonEdit").prop("disabled", false);
        isAdminRemovingShow = false;
    } else {
        $("#buttonAdd, #buttonEdit").prop("disabled", true);
        $(".film-spectacle").after("<small id='removing-info' class='text-warning'>Seleziona lo spettacolo da rimuovere</small>");
        isAdminRemovingShow = true;
    }
})

//Clicco sul pulsante di modifica
let isAdminEditingShow = false;
let editingShowButton;
let editingShowId = 0;
$(document).on("click", "#buttonEdit", function() {
    if(!isAdminEditingShow) {
        $("#buttonAdd, #buttonRemove").prop("disabled", true);
        $("#showFormTitle, #showFormButton").text("Modifica Spettacolo");
        $(".film-spectacle").after("<small id='editing-info' class='text-warning'>Seleziona lo spettacolo da modificare</small>");
        isAdminEditingShow = true;
    } else {
        if(elementExists(editingShowButton))
            editingShowButton.removeClass("btn-warning")

        $("#showForm")
            .collapse("hide")
            .attr("action", contextPath+"/show-manager/add");

        $("#buttonAdd, #buttonRemove").prop("disabled", false);
        $("#editing-info").remove();
        editingShowId = 0;
        isAdminEditingShow = false;
    }
})

$(".btn-show").click(function(e) {
    //Voglio rimuovere lo spettacolo cliccato
    if(isAdminRemovingShow) {
        isAdminRemovingShow = false;
        e.preventDefault();
        $("#removing-info").remove();

        let showButton = $(this);
        let showId = showButton.children("input").val();
        let dataString = "showId="+showId;
        $.ajax({
            type: 'post',
            url: contextPath + "/show-manager/remove",
            async: false,
            data: dataString,
            dataType: 'json',
            success: function (response) {
                if(response["result"] === 'success')
                    location.reload();
            }
        });
    }

    //Voglio modificare lo spettacolo cliccato
    if(isAdminEditingShow) {
        e.preventDefault();

        if(editingShowId === 0) {
            $("#editing-info").remove();

            editingShowButton = $(this);
            editingShowId = editingShowButton.children("input").val();
            editingShowButton.addClass("btn-warning");

            $.ajax({
                type: "post",
                url: contextPath + "/show-manager/get-show",
                async: false,
                data: "showId="+editingShowId,
                dataType: 'json',
                success: function(response) {
                    let timeList = response["timeList"],
                        roomId = response["roomId"],
                        date = response["date"];

                    $("#room")
                        .append("<option value='"+roomId+"' selected>Sala "+roomId+"</option>")
                        .prop("disabled", true);

                    $("#time").html(getOptionFromTimeList(timeList));

                    $("#date").val(date);

                    $("#showFormTitle, #showFormButton").text("Modifica Spettacolo");

                    $("#showForm")
                        .collapse("show")
                        .attr("action", contextPath+"/show-manager/update?showId="+editingShowId);
                }
            })
        }
    }
})

$(document).on("change", "#room", function(){
    $("#date").change();
})

$(document).on("change", "#date", function() {
    let dataString = $("#showForm").serialize();

    $.ajax({
        type: "post",
        url: contextPath + (isAdminEditingShow ? "/show-manager/get-show" : "/show-manager/get-all-show"),
        async: true,
        data: dataString,
        dataType: 'json',
        success: function (response) {

            let timeList = response["timeList"];
            $("#time").html(getOptionFromTimeList(timeList));
        }
    })
})

$(document).on("click", "#buttonAdd", function() {
    $("#buttonEdit, #buttonRemove").prop("disabled", true);
    $("#showFormTitle, #showFormButton").text("Aggiungi Spettacolo");
})

$(document).on("hidden.bs.collapse", "#showForm", function() {
    $("#buttonEdit, #buttonRemove, #buttonAdd").prop("disabled", false);
})

function getOptionFromTimeList(timeList) {
    let string = "";
    let flag = false;

    for (let time of timeList) {
        string += "<option value="+time+(flag === false ? " selected" : "")+">"+time+"</option>";
        flag = true;
    }

    return string;
}