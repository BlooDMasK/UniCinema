function triggerAlert(response) {
    let alert = response["alert"];

    $(".alert").removeClass("alert-danger alert-success alert-");
    $(".alert").addClass("alert-"+alert.type);

    $("#alert-close, .alert-item").remove();

    let string = "";

    for(let i = 0; i < alert.messages.length; i++)
        string += "<li class='.alert-item'>"+alert.messages[i]+"</li>";

    $(".alert ol").html(string);
    $(".alert").show();

    return alert.type;
}