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