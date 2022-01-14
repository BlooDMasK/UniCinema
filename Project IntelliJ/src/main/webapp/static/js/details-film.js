$(document).ready(function () {
    if(accountIsAdministrator) {

        $(".film-title").append("<a href='"+contextPath+"/film-manager/remove?filmId="+filmId+"' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                        <img src='"+contextPath+"/static/icons/minus.svg'> Rimuovi\
                     </a>\
                     <a href='"+contextPath+"/film-manager/update?filmId="+filmId+"' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                        <img src='"+contextPath+"/static/icons/edit.svg'> Modifica\
                    </a>");

        $(".film-spectacle").append("<button type='button' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                        <img src='"+contextPath+"/static/icons/plus.svg'> Aggiungi\
                     </button>\
                     <button type='button' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                        <img src='"+contextPath+"/static/icons/edit.svg'> Modifica\
                    </button>\
                    <button type='button' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                        <img src='"+contextPath+"/static/icons/minus.svg'> Rimuovi\
                    </button>");
    }
});