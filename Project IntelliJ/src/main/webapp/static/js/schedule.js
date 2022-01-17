$(document).ready(function () {
    if(accountIsAdministrator) {

        let string = "<a href='"+contextPath+"/film-manager/add' class='btn btn-warning btn-outline-dark position-fixed rounded-pill' style='bottom: 5%;right: 5%;'>"+
                        "<img src='"+contextPath+"/static/icons/plus.svg'> Aggiungi"+
                     "</a>";

        $("#schedule-container").after(string);

        $(".film-title").each(function() {

            string = "<a href='"+contextPath+"/film-manager/remove?filmId="+$(this).attr("id")+"' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                        <img src='"+contextPath+"/static/icons/minus.svg'> Rimuovi\
                     </a>\
                     <a href='"+contextPath+"/film-manager/update?filmId="+$(this).attr("id")+"' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                        <img src='"+contextPath+"/static/icons/edit.svg'> Modifica\
                    </a>";

            $(this).append(string);
        })

    }
});