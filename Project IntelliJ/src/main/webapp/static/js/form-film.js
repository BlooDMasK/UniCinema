$(document).ready(function() {

    switch(formType)
    {
        case "add":
        {
            $("#collapseActors .accordion-body, #collapseDirectors .accordion-body, #collapseProduction .accordion-body").each(function () {
                $(this).append(generateFirstLastNameForm());
            })

            $("#collapseHouseProduction .accordion-body").append(generateNameForm());

            break;
        }
        case "update":
        {
            let actorList = film["actorList"],
                directorList = film["directorList"],
                productionList = film["productionList"],
                houseProductionList = film["houseProductionList"];

            //Attori
            for(let actor of actorList)
                $("#collapseActors .accordion-body").append(generateFirstLastNameForm(actor["firstname"], actor["lastname"], actor["id"]));

            //Registi
            for(let director of directorList)
                $("#collapseDirectors .accordion-body").append(generateFirstLastNameForm(director["firstname"], director["lastname"], director["id"]));

            //Produzione
            for(let production of productionList)
                $("#collapseProduction .accordion-body").append(generateFirstLastNameForm(production["firstname"], production["lastname"], production["id"]));

            //Casa di produzione
            for(let houseProduction of houseProductionList)
                $("#collapseHouseProduction .accordion-body").append(generateNameForm(houseProduction["name"], houseProduction[["id"]]));

            break;
        }
    }

    $("#collapseActors .accordion-body, #collapseDirectors .accordion-body, #collapseProduction .accordion-body").each(function () {
        $(this).append("<div class='d-flex'>\
            <button type='button' class='btn btn-success btn-first-last-name'><img src='" + contextPath + "/static/icons/plus.svg' alt='...'></button>\
            </div>");
    });

    $("#collapseHouseProduction .accordion-body").append("<div class='d-flex'>\
            <button type='button' class='btn btn-success btn-name'><img src='" + contextPath + "/static/icons/plus.svg' alt='...'></button>\
            </div>");
})

$(document).on("click", "#collapseActors .accordion-body .btn-success, #collapseDirectors .accordion-body .btn-success, #collapseProduction .accordion-body .btn-success", function() {
    $(this).parent().before(generateFirstLastNameForm());
})

$(document).on("click", "#collapseHouseProduction .accordion-body .btn-success", function() {
    $(this).parent().before(generateNameForm());
})

$(document).on("click", ".first-last-name-div .btn-danger, .name-div .btn-danger", function() {
    let parentDiv = $(this).parent();
    if(!parentDiv.is(":first-child"))
        parentDiv.remove();
})

$("form").submit(function () {
    specifyInputNameArray("Actors");
    specifyInputNameArray("Directors");
    specifyInputNameArray("Production");
    specifyInputNameArray("HouseProduction");
})

function specifyInputNameArray(inputName) {
    let selector = $('#collapse'+inputName+' input');

    switch(inputName) {
        case "Actors":
        case "Directors":
        case "Production":
            selector.each(function() {
                let input = $(this);
                if(input.hasClass("input-firstname"))
                    input.attr("name", inputName+"Firstname");

                if(input.hasClass("input-lastname"))
                    input.attr("name", inputName+"Lastname");

                if(input.hasClass("input-id-first"))
                    input.attr("name", inputName+"Id");
            })
            break;

        case "HouseProduction":
            selector.each(function () {
                let input = $(this);
                if(input.hasClass("input-name"))
                    input.attr("name", inputName+"Name");

                if(input.hasClass("input-id-second"))
                    input.attr("name", inputName+"Id");
            })
            break;
    }
}

function generateFirstLastNameForm(firstname = "", lastname = "", id="") {
    return "<div class='first-last-name-div d-flex mb-2'>\
             <button type='button' class='btn btn-danger me-1 height-2-4'><img src='"+contextPath+"/static/icons/minus.svg' alt='...'></button>\
             <div class='d-flex flex-column flex-grow-1'>\
                 <input name='Firstname' type='text' class='input-firstname form-control rounded-pill fs-1-5 fw-light me-1' value='"+firstname+"' placeholder='Nome...' required minlength='2' maxlength='30' pattern='^[A-Za-z\\W]{2,30}$'>\
                 <div class='custom-feedback'></div>\
             </div>\
             <div class='d-flex flex-column flex-grow-1'>\
                 <input name='Lastname' type='text' class='input-lastname form-control rounded-pill fs-1-5 fw-light' value='"+lastname+"' placeholder='Cognome...' required minlength='2' maxlength='30' pattern='^[A-Za-z\\W]{2,30}$'>\
                 <div class='custom-feedback'></div>\
             </div>\
              <input type='hidden' class='input-id-first' name='Id' value='"+id+"'>\
            </div>";
}

function generateNameForm(name = "", id = "") {
    return "<div class='name-div d-flex mb-2'>\
              <button type='button' class='btn btn-danger me-1 height-2-4 '><img src='"+contextPath+"/static/icons/minus.svg' alt='...'></button>\
              <div class='d-flex flex-column flex-grow-1'>\
                  <input name='Name' type='text' class='input-name form-control rounded-pill fs-1-5 fw-light' value='"+name+"' placeholder='Nome...' required minlength='5' maxlength='50' pattern='^[A-Za-z0-9\\W]{5,50}$'>\
                  <div class='custom-feedback'></div>\
              </div>\
              <input type='hidden' class='input-id-second' name='Id' value='"+id+"'>\
            </div>";
}