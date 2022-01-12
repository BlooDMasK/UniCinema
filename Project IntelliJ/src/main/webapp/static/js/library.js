/*
       Validazione frontend
 */
(function () {
    'use strict'

    var forms = document.querySelectorAll('.needs-validation')

    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})()

const urlObject = new URL(window.location.href);

function elementExists(element) {
    if (typeof (element) != 'undefined' && element != null)
        return 1;
    else
        return 0;
}

/*
    Maxlength textarea
 */
$(function() {
    $("textarea[maxlength]").bind('input propertychange', function() {
        let maxLength = $(this).attr('maxlength');
        if ($(this).val().length > maxLength) {
            $(this).val($(this).val().substring(0, maxLength));
        }
    })
});

function collapseDynamicContent(collapseTrigger){
    /**
     * Manually add collapse on click event.
     *
     * Because dynamically added Bootstrap collapse elements don't
     * work automatically for me most of the time.
     *
     * 'data-target' is a selector for the collapsing element as per
     * the Bootstrap documentation.
     * https://getbootstrap.com/docs/4.3/components/collapse/#via-data-attributes
     *
     * @param {jQuery} collapseTrigger Trigger element for the collapse.
     *
     */
    let target = collapseTrigger.attr("data-target")
    $(target).collapse('toggle')
}

let searchBar = $("#search-bar");
let searchDropdown = $("#search-dropdown");

if(elementExists(searchBar)) {

    searchBar.keyup(function(event) {
        let title = event.target.value,
            dataString = "title="+title;

        $.ajax({
            type: "Post",
            url: contextPath + "/film/search",
            async: true,
            data: dataString,
            dataType: 'json',
            success: function (response) {
                let filmList = response["filmList"],
                    string = "";

                if(title.length == 0) {
                    if(searchError == true)
                        setSearchIconError(false);

                    searchDropdown.hide();
                }
                else {

                    if (filmList.length == 0) {
                        if(searchError == false)
                            setSearchIconError(true);
                        searchDropdown.hide();
                    } else {
                        if(searchError == true)
                            setSearchIconError(false);

                        for (let i in filmList)
                            string += "<li><a class='dropdown-item' href='" + contextPath + "/film/details?filmId=" + filmList[i]["id"] + "'>" + filmList[i]["title"] + "</a></li>";

                        searchDropdown.html(string);
                        searchDropdown.show();
                    }
                }
            }
        });
    });
}

let searchError = false;
function setSearchIconError(error) {
    searchError = error;
    let searchIcon = $("#search-icon");
    if(error)
        searchIcon.html("<img src='"+contextPath+"/static/icons/x-circle.svg'>")
    else
        searchIcon.html("<img src='"+contextPath+"/static/icons/search.svg'>")
}