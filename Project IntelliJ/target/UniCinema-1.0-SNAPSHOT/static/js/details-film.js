$(document).ready(function () {
    console.log("readyAdmn: " + accountIsAdministrator)
    if(accountIsAdministrator) {

        $(".film-title").append("<a href='"+contextPath+"/film-manager/remove?filmId="+filmId+"' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                        <img src='"+contextPath+"/static/icons/minus.svg'> Rimuovi\
                     </a>\
                     <a href='"+contextPath+"/film-manager/update?filmId="+filmId+"' class='btn btn-warning btn-outline-dark rounded-pill pt-0 pb-0 ms-1'>\
                        <img src='"+contextPath+"/static/icons/edit.svg'> Modifica\
                    </a>");
    }
});