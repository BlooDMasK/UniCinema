/*
    Variabili
 */
let seatMap = new Map();

/*
    Eventi
 */

$(document).ready(function() {
    $("#purchaseWarning").hide();
})

/*
    Chiamate Ajax
 */

$("#purchaseTicket").click(function () {
    let seatCount = seatMap.size,
        purchaseWarning = $("#purchaseWarning");

    purchaseWarning.hide();
    if(seatCount == 0) {//Se nessun posto è selezionato

        purchaseWarning.text("Devi selezionare almeno un posto.");
        purchaseWarning.show(); //Mostro l'errore

    } else { //Se almeno un posto è selezionato

        for(let key in seatMap) //Controllo se tutti i posti sono effettivamente ancora liberi
            if(isSeatOccupied(key)) { //Se almeno uno è occupato, devo fermare l'acquisto.

                purchaseWarning.text("Il posto "+key+" è occupato.");
                purchaseWarning.show();

                seatMap.delete(key); //lo rimuovo dai selezionati

                if($(".btn-seat").text() == key) {
                    let button = $(this);
                    resetColorClass(button); //setto il colore a iniziale
                    button.addClass("btn-outline-light");
                }
                return;
            }

        let ct = 1;
        for(let key of seatMap.keys())
            $("#ticket" + (ct++)).val(key);

        seatMap.clear();
        $("#purchaseForm").submit();
    }
})

$(".btn-seat").click(function () {
    $("#purchaseWarning").hide();
    let button = $(this),
        key = button.text(),
        seatCount = seatMap.size;

    if(seatMap.has(key)) { //Se clicco su un posto che ho già preso

        seatMap.delete(key); //lo rimuovo dai selezionati
        resetColorClass(button); //setto il colore a iniziale
        button.addClass("btn-outline-light");

    } else { //Se clicco un posto vuoto

        if(seatCount < 4) { //Se non ho già preso 4 posti
            //Verifico che quel posto non sia già stato preso
            if(isSeatOccupied(key)) //Se il posto è occupato
                button.addClass("btn-danger");
            else { //Se il posto è libero
                button.addClass("btn-success");
                seatMap.set(key, true);
            }
        }
    }
})

/*
    Funzioni
 */

function resetColorClass(button) {
    button.removeClass("btn-outline-light btn-danger btn-success");
}

function isSeatOccupied(key) {
    let dataString = "key="+key+"&showId="+showId,
        occupied = false;

    $.ajax({
        type: "Post",
        url: contextPath + "/purchase/seat-check",
        async: false,
        data: dataString,
        dataType: 'json',
        success: function (response) {
            occupied = response["occupied"]; //Se il posto è occupato
        }
    })

    return occupied;
}