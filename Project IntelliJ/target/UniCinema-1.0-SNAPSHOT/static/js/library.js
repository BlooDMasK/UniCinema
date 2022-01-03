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