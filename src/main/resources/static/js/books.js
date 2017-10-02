let offset = 0;
let limit = 5;
let totalCount;
let books = $('#books');
let currentUsername;

$(document).ready(function () {
    getCurrentUserId(function (username) {
        currentUsername = username;
    });
    getMoreBooks();
});

function showBooks(response, mapper) {
    offset = response.offset;
    limit = response.limit;
    totalCount = response.totalCount;
    if (totalCount === (offset + 1) * limit)
        $('#show-more').hide();
    else
        offset++;
    response.data.forEach(function (el) {
        books.append(mapper(el));
    });
}

function bookTableRowMapper(el) {
    let tr = $('<tr></tr>');
    tr.append(buildTableData(el['isn']));
    tr.append(buildTableData(el['name']));
    tr.append(buildTableData(el['author']));
    tr.append(buildTableData(el['username'], buildBookUserCell));
    tr.append(buildTableData(null, buildBookDeleteCell));
    return tr;
}

function getMoreBooks() {
    getBooks(offset, limit, showBooks, bookTableRowMapper, null);
}

function buildBookUserCell(username) {
    let content;
    if (username) {
        if (username === currentUsername) {
            content = $('<button class="btn btn-success return-book"></button>');
            content.append('Вернуть');
            content.click(onTakeBookClick);
        } else {
            content = $('<p></p>');
            content.append('Взял ' + username);
        }
    } else {
        content = $('<button class="btn btn-warning take-book"></button>');
        content.append('Взять');
        content.click(onReturnBookClick);
    }
    return content;
}

function buildBookDeleteCell() {
    let button = $('<button class="btn btn-danger delete-book"></button>');
    button.append('Удалить');
    button.click(onDeleteButtonClick);
    return button;
}

function onTakeBookClick() {
    let currentRow = $(this).closest("tr");
    let isnCell = currentRow.find("td:eq(0)");
    let isn = isnCell.text();
    takeBook(isn, function (resp) {
        let buttonCell = currentRow.find("td:eq(3)");
        buttonCell.replaceWith(buildBookUserCell(currentUsername));
    });
}

function onReturnBookClick() {
    let currentRow = $(this).closest("tr");
    let isnCell = currentRow.find("td:eq(0)");
    let isn = isnCell.text();
    returnBook(isn, function (resp) {
        let buttonCell = currentRow.find("td:eq(3)");
        buttonCell.replaceWith(buildBookUserCell(null));
    });
}

function onDeleteButtonClick() {
    let currentRow = $(this).closest("tr");
    let isnCell = currentRow.find("td:eq(0)");
    let isn = isnCell.text();
    if (confirm("Удалить книгу?")) {
        deletBook(isn, function (resp) {
            currentRow.remove();
        })
    }
}