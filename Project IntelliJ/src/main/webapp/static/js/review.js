let starNumber;

/*
    Eventi
 */
$(document).ready(function () {
    resetNumberStar();
    generateReviews(1);
    $(".alert").hide();
})

$('#collapseReviewBox').on('hidden.bs.collapse', function () { //Chiudi
    $("#collapseReviewButton").text("Scrivi una recensione");

    resetNumberStar();
    resetReviewForm();
})

$('#collapseReviewBox').on('shown.bs.collapse', function () { //Apri
    $("#collapseReviewButton").text("Nascondi box recensione");
})

$(".fa-plus").click(function () {
    if(starNumber < 5) {
        incrementNumberStar();
    }
})

$(".fa-minus").click(function () {
    if(starNumber > 1) {
        decrementNumberStar();
    }
})

/*
    Chiamate Ajax
 */
$("#collapseReviewBox").submit(function(e) {
    let dataString = $(this).serialize()+"&filmId="+filmId;

    $.ajax({
        type: 'post',
        url: contextPath + "/review/add",
        async: true,
        data: dataString,
        dataType: 'json',
        success: function(response) {
           if(triggerAlert(response) == 'success') {
               resetNumberStar();
               resetReviewForm();
               generateReviews(1);
           }
        }
    })

    e.preventDefault();
})

//oggetto.click non va bene se il contenuto è creato dinamicamente. Bisogna usare on("click"...
$(document).on ("click", ".button-remove-review", function () {
    const dataString = "accountId="+$(this).val();
    console.log(dataString);

    $.ajax({
        type: "Post",
        url: contextPath + "/review/remove",
        async: true,
        data: dataString,
        dataType: 'json',
        success: function (response) {
            generateReviews(1);
            alert(response["result"]);
        }
    })
});

/*
    Funzioni
 */
function resetReviewForm() {
    $("#reviewWriteTitle").val('');
    $("#reviewWriteDescription").val('');
    $(".alert").hide();
}

function resetNumberStar() {
    starNumber = 1;
    setNumberStar(starNumber);
}

function incrementNumberStar() {
    setNumberStar(++starNumber);
}

function decrementNumberStar() {
    setNumberStar(--starNumber);
}

function setNumberStar(value) {
    $(".review-star-text").text(value);
    $("#reviewWriteStars").prop("value", value);
}

function generateReviews(page) {
    let dataString = "page=" + page + "&filmId=" + filmId; //page=1&filmId=1

    $(".page-item").removeClass("active");
    $(this).parent().addClass("active");

    $.ajax({
        type: "Post",
        url: contextPath + "/review/list",
        async: true,
        data: dataString,
        dataType: 'json',
        success: function (response) {

            /*
                    Istanzio le variabili tramite il JSON di response
             */
            let reviewList = response["reviewList"],
                pages = response["pages"],
                reviewAvg = response["reviewAverage"],
                reviewAvgRounded = response["reviewAverageRounded"],
                reviewAvgPercentage1 = response["reviewPercentage1"],
                reviewAvgPercentage2 = response["reviewPercentage2"],
                reviewAvgPercentage3 = response["reviewPercentage3"],
                reviewAvgPercentage4 = response["reviewPercentage4"],
                reviewAvgPercentage5 = response["reviewPercentage5"],
                reviewCount = response["reviewCount"];

            /*
                    Stats recensioni
             */
            let starsAvgString = "";
            for (let i = 0; i < reviewAvgRounded; i++)
                starsAvgString += "<i class='fa fa-star text-warning'></i>";

            $("#reviewStatsStars").html(starsAvgString);
            $("#reviewStatsAvg").text(reviewAvg + " su 5");
            $("#reviewStatsCount").text(reviewCount + " recensioni");

            $("#reviewStatsPercentage5").text(reviewAvgPercentage5);
            $("#reviewStatsBar5").css("width", reviewAvgPercentage5);

            $("#reviewStatsPercentage4").text(reviewAvgPercentage4);
            $("#reviewStatsBar4").css("width", reviewAvgPercentage4);

            $("#reviewStatsPercentage3").text(reviewAvgPercentage3);
            $("#reviewStatsBar3").css("width", reviewAvgPercentage3);

            $("#reviewStatsPercentage2").text(reviewAvgPercentage2);
            $("#reviewStatsBar2").css("width", reviewAvgPercentage2);

            $("#reviewStatsPercentage1").text(reviewAvgPercentage1);
            $("#reviewStatsBar1").css("width", reviewAvgPercentage1);

            /*
                    Lista recensioni
             */
            $(".review").remove();

            if(reviewList.length == 0)
                $(".review-div").html("<h5>Ancora nessuna recensione.</h5>");
            else {

                let reviewString = "";
                for (let i = 0; i < reviewList.length; i++) {
                    let starString = "";
                    for (let stars = 0; stars < reviewList[i]["stars"]; stars++, starString += "<i class='fa fa-star text-warning'></i>") ;

                    let iconString;

                    if (reviewList[i]["isAdministratorReview"]) //Deve uscire shield.svg
                        iconString = "<img src='" + contextPath + "/static/icons/shield.svg'>";
                    else //Deve uscire user.svg
                        iconString = "<img src='" + contextPath + "/static/icons/user.svg'>";

                    if (reviewList[i]["accountId"] == accountId || accountIsAdministrator) //Deve uscire trash.svg
                        iconString += " <button name='removeReviewButton' value='" + reviewList[i]["accountId"] + "' class='bg-none border-none button-remove-review'><img src='" + contextPath + "/static/icons/trash.svg'></button>";

                    reviewString += "\
                <div class='mb-4 pb-4 border-bottom review'>\
                    <div class='d-flex mb-3 align-items-center'>\
                        <img src='" + contextPath + "/static/images/user-avatar.png' alt='' class='rounded-circle avatar-lg' style='width: 50px;height: 50px;margin-right: .6rem;background-color: white;'>\
                        <div class='ml-2'>\
                            <h5 class='mb-1'>\
                            " + reviewList[i]["firstname"] + " " + reviewList[i]["lastname"] + iconString + "\
                            </h5>\
                            <p class='font-12 mb-0'>\
                           <span class='fw-bold'>" + reviewList[i]["dateDayOfWeek"] + "</span> <span>" + reviewList[i]["dateDayMonth"] + "</span> \
                            </p>\
                        </div>\
                    </div>\
                    <div class='mb-2'>\
                        <span class='font-14 mr-2'>\
                        " + starString + "\
                        </span>\
                        <span class='h5'>" + reviewList[i]["title"] + "</span>\
                    </div>\
                    <p>\
                        " + reviewList[i]["description"] + "\
                    </p>\
                </div>"
                }

                $(".review-div").html(reviewString);

                /*
                    Paginator
                */
                setActivePage(page);
                generatePaginator(pages, "generateReviews");
            }

            let top = document.getElementById("reviewTitle").offsetTop;
            window.scrollTo(0, top);
        }
    })
}