let activePage = 1;

function setActivePage(value) {
    activePage = value;
}

function generatePaginator(pages, onClickMethod) {
    let paginatorString = "", method = "";
    for(let i = 1; i <= pages; i++) {
        method = onClickMethod+"("+i+")";
        paginatorString += "<li class='page-item "+(i==activePage ? "active" : "")+"' onclick='"+method+"'>";
        paginatorString += "<a class='page-link' href='#'>"+i+"</a>";
        paginatorString += "</li>";
    }

    $(".pagination").html(paginatorString);
}