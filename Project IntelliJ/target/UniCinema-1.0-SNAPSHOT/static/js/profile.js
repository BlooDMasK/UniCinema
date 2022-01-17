/*
    Eventi
 */
$(document).ready(function () {
    generatePurchases(1);
})

$("#profile-edit-confirm").click(function(e) {
    let btnConfirm = document.getElementById("profile-edit-confirm");

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

    generatePurchases(1);
})

function generatePurchases(page) {
    let dataString = "page=" + page + "&accountId="+accountId;

    $.ajax({
        type: "Post",
        url: contextPath + "/purchase/list",
        async: true,
        data: dataString,
        dataType: 'json',
        success: function (response) {
            let string = "",
                tableBodyString = "",
                purchaseList = response['purchaseList'],
                ticketList,
                show = null,
                pages = response['pages'];


            if(purchaseList.length == 0)
                string = "Nessun acquisto effettuato.";
            else {
                for (let purchase of purchaseList) {

                    ticketList = purchase["ticketList"];
                    for (let ticket of ticketList) {
                        tableBodyString += "<tr>\
                                                <th scope='row'>" + ticket["id"] + "</th>\
                                                <th>" + ticket["rowLetter"] + "</th>\
                                                <th>" + ticket["seat"] + "</th>\
                                                <th>" + ticket["uniqueCode"] + "</th>\
                                                <th>" + ticket["price"] + "</th>\
                                            </tr>";

                        show = ticket["show"];
                    }


                    string += "<div class='card'>\
                                    <div class='card-header' id='heading" + purchase["id"] + "'>\
                                        <h5 class='mb-0'>\
                                            <button type='button' class='btn' onclick='collapseDynamicContent($(this))' data-toggle='collapse' data-target='#collapse" + purchase["id"] + "' aria-expanded='true' aria-controls='collapse" + purchase["id"] + "'>\
                                                Ordine #" + purchase["id"] + " in data " + purchase["datePurchase"] + "\
                                            </button>\
                                        </h5>\
                                    </div>\
                                    <div id='collapse" + purchase["id"] + "' class='collapse' aria-labelledby='heading" + purchase["id"] + "' data-parent='#accordion'>\
                                        <div class='card-body'>\
                                        Spettacolo #" + show["id"] +" '"+ show["filmTitle"] + "' in data " + show["date"] + ", alle ore " + show["time"] + "\
                                            <table class='table table-responsive table-light table-striped' style='width: 70%;'>\
                                                <thead class='table-light'>\
                                                    <tr>\
                                                        <th scope='col'>Id</th>\
                                                        <th scope='col'>Lettera</th>\
                                                        <th scope='col'>Poltrona</th>\
                                                        <th scope='col'>Codice Univoco</th>\
                                                        <th scope='col'>Prezzo</th>\
                                                    </tr>\
                                                </thead>\
                                                <tbody>" + tableBodyString + "</tbody>\
                                            </table>\
                                        </div>\
                                    </div>\
                                </div>";

                    tableBodyString = "";
                }
            }

            $("#accordion").html(string);
            /*
                Paginator
            */
            setActivePage(page);
            generatePaginator(pages, "generatePurchases");
        }
    })
}

$("#list-info-list").click(function() {
    $("#profile-edit-confirm").show();
})

$("#profile-edit-form").submit(function(e) {
    let dataString = $(this).serialize();

    $.ajax({
        type: "Post",
        url: contextPath+"/account/edit",
        async: true,
        data: dataString,
        dataType: 'json',
        success: function(response) {

            if(triggerAlert(response) == 'success') {
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