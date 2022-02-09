/*
       Validazione frontend
 */
(function () {
    'use strict'

    let forms = document.querySelectorAll('.needs-validation')

    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                form.classList.remove('validation-valid', 'validation-invalid');

                $(" textarea.form-control", form).each(function() {
                    let textarea = $(this);
                    let inputVal = textarea.val();

                    let expression = "";
                    switch(textarea.attr("id"))
                    {
                        case "reviewWriteDescription":
                            expression = "^[A-Za-z0-9\\W]{5,500}$";
                            break;

                        case "plot":
                            expression = "^.[^\\[\\]\\{\\}\\@\\=\\_\\?]{10,1000}$";
                            break;
                    }

                    console.log(expression);
                    const regex = new RegExp(expression);
                    console.log("test: " + regex.test(inputVal));
                    if (!regex.test(inputVal)) {
                        event.preventDefault()
                        event.stopPropagation()

                        $("+ .custom-feedback", textarea).html("Formato non valido.<br>");
                        $("+ .custom-feedback", textarea).addClass("d-block");
                        inputSetInvalid(textarea);
                    } else {
                        $("+ .custom-feedback", textarea).removeClass("d-block");
                        inputSetValid(textarea);
                    }
                })

                //se la validazione delle non textarea fallisce
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()

                    //Per ogni input faccio i controlli su cosa è andato storto e cosa no
                    $(" .form-control", form).each(function() {
                        let input = $(this);
                        let inputVal = input.val();
                        let required = input.attr("required"),
                            pattern = input.attr("pattern"),
                            minlength = input.attr("minlength"),
                            maxlength = input.attr("maxlength"),
                            min = input.attr("min"),
                            max = input.attr("max"),
                            isValid = true,
                            feedback = "",
                            inputType = input.attr("type");

/*
                        if(inputType === "email") {
                            const regex = new RegExp("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
                            isValid = isValid && regex.test(inputVal);
                            if (!regex.test(inputVal))
                                feedback = "Formato email non valido.";
                        }*/

                        if (pattern !== undefined) {
                            const regex = new RegExp(pattern);
                            isValid = isValid && regex.test(inputVal);
                            if (!regex.test(inputVal))
                                feedback = "Formato non valido.";
                        }

                        //MIN E MAX
                        if (min !== undefined && max !== undefined) {
                            if(inputType === "date") {

                                let dateMin = new Date(min),
                                    dateMax = new Date(max),
                                    date = new Date(inputVal);

                                isValid = isValid && (dateMin <= date && date <= dateMax);
                                if(!(dateMin <= date && date <= dateMax))
                                    feedback = "La data deve essere compresa tra " + min + " e " + max + ".";

                            } else {
                                isValid = isValid && (min <= inputVal && inputVal <= max)
                                if (!(min <= inputVal && inputVal <= max))
                                    feedback = "Il valore deve essere compreso tra " + min + " e " + max + ".";

                            }
                        }

                        if (min !== undefined && max === undefined) {
                            isValid = isValid && (min <= inputVal);
                            if (!(min <= inputVal))
                                feedback = "Il valore deve essere minimo " + min + ".";
                        }

                        if (min === undefined && max !== undefined) {
                            isValid = isValid && (inputVal >= max);
                            if (!(inputVal >= max))
                                feedback = "Il valore deve essere massimo " + max + ".";
                        }

                        //MINLENGTH E MAX LENGTH
                        if (minlength !== undefined && maxlength !== undefined) {
                            isValid = isValid && (minlength <= inputVal.length && inputVal.length <= maxlength);
                            if (!(minlength <= inputVal.length && inputVal.length <= maxlength))
                                feedback = "La lunghezza deve essere compresa tra " + minlength + " e " + maxlength + ".";
                        }

                        if (minlength !== undefined && maxlength === undefined) {
                            isValid = isValid && (minlength <= inputVal);
                            if (!(minlength <= inputVal))
                                feedback = "La lunghezza deve essere minimo di " + minlength + ".";
                        }

                        if (minlength === undefined && maxlength !== undefined) {
                            isValid = isValid && (inputVal >= maxlength);
                            if (!(inputVal >= maxlength))
                                feedback = "Il valore deve essere massimo " + maxlength + ".";
                        }

                        //REQUIRED
                        if (required !== undefined) {
                            isValid = isValid && (inputVal.length > 0);
                            if (inputVal.length === 0)
                                feedback = "Il campo non pu&#242 essere vuoto.";
                        }

                        if(!isValid) {
                            $("+ .custom-feedback", this).html(feedback + "<br>");
                            $("+ .custom-feedback", this).show();
                            inputSetInvalid(input);
                        } else {
                            $("+ .custom-feedback", this).hide();
                            inputSetValid(input);
                        }
                    })

                } else {

                    $(" .form-control", form).each(function() { //Per ogni input

                        $(this).removeClass("is-valid is-invalid");
                        $("+ .custom-feedback", this).hide();

                    })

                }
            }, false)
        })
})()

const urlObject = new URL(window.location.href);

$.fn.isValid = function() {
    return this[0].checkValidity();
}

function inputSetValid(input) {
    input.removeClass("is-invalid");
    input.addClass("is-valid");
}

function inputSetInvalid(input) {
    input.removeClass("is-valid");
    input.addClass("is-invalid");
}

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