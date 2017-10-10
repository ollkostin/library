let offset = 0;
let limit = 5;
let totalCount;
let books = $('#books');
let currentUsername;
books.on('click', '.take-book', onClickTakeBook);
books.on('click', '.return-book', onClickReturnBook);
$('#modal-window').on('hidden.bs.modal', clearModalValues);

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
    tr.append(buildTableData(el['isn'], buildLinkCell));
    tr.append(buildTableData(el['name']));
    tr.append(buildTableData(el['author']));
    tr.append(buildTableData(el['username'], buildBookUserCell));
    tr.append(buildTableData(null, buildBookDeleteCell));
    return tr;
}

function getMoreBooks() {
    getBooks(offset, limit, showBooks, bookTableRowMapper, null);
}

function buildLinkCell(element) {
    let a = $('<a href="" data-toggle="modal">' + element + '</a>');
    a.click(onClickShowModalEditBook);
    return a;
}

function buildBookUserCell(username) {
    let content;
    if (username) {
        if (username === currentUsername) {
            content = $('<button class="btn btn-success return-book"></button>');
            content.append('Вернуть');
        } else {
            content = $('<p></p>');
            content.append('Взял ' + username);
        }
    } else {
        content = $('<button class="btn btn-warning take-book"></button>');
        content.append('Взять');
    }
    return content;
}

function buildBookDeleteCell() {
    let button = $('<button class="btn btn-danger delete-book"></button>');
    button.append('Удалить');
    button.click(onDeleteButtonClick);
    return button;
}

function onClickTakeBook() {
    let currentRow = $(this).closest("tr");
    let isnCell = currentRow.find("td:eq(0)");
    let isn = isnCell.text();
    takeBook(isn, function (resp) {
        let buttonCell = currentRow.find("td:eq(3)");
        buttonCell.empty();
        buttonCell.append(buildBookUserCell(currentUsername));
    });
}

function onClickReturnBook() {
    let currentRow = $(this).closest("tr");
    let isnCell = currentRow.find("td:eq(0)");
    let isn = isnCell.text();
    returnBook(isn, function (resp) {
            let buttonCell = currentRow.find("td:eq(3)");
            buttonCell.empty();
            buttonCell.append(buildBookUserCell(null));
        },
        showErrorAlert);
}

function onDeleteButtonClick() {
    let currentRow = $(this).closest("tr");
    let isnCell = currentRow.find("td:eq(0)");
    let isn = isnCell.text();
    if (confirm("Удалить книгу?")) {
        deleteBook(isn, function (resp) {
            currentRow.remove();
        }, showErrorAlert);
    }
}

function onClickShowModalCreateBook() {
    $('#modal-action').on('click', onClickCreateBook);
    $('#modal-window')
        .modal('show');
}

function onClickCreateBook() {
    let book = buildBookDto($('#isn').val(), $('#name').val(), $('#author').val());
    createBook(book, function (resp) {
        alert('Книга успешно сохранена');
        $('#modal-window').modal('toggle');
        clearModalValues();
    }, bookError);
}

function clearModalValues() {
    $('#isn').val('');
    $('#name').val('');
    $('#author').val('');
}

function onClickShowModalEditBook() {
    $('#modal-action').on('click', onClickEditBook);
    let currentRow = $(this).closest("tr");
    let isn = currentRow.find("td:eq(0)").text();
    let name = currentRow.find("td:eq(1)").text();
    let author = currentRow.find("td:eq(2)").text();
    $('#isn').val(isn);
    $('#name').val(name);
    $('#author').val(author);
    $('#modal-window')
        .modal('show');
}

function buildBookDto(isn, name, author) {
    return {
        isn: isn,
        name: name,
        author: author
    };
}

function onClickEditBook() {
    let book =
        buildBookDto($('#isn').val(), $('#name').val(), $('#author').val());
    editBook(book, function (resp) {
        alert('Книга успешно отредактирована');
        // $('#modal-window').modal('toggle');
        // clearModalValues();
        location.reload();
    }, bookError);
}

function bookError(resp) {
    if (resp.responseJSON.message === 'isn')
        alert('ISN не указан или указан неверно');
    if (resp.responseJSON.message === 'book') {
        alert('Книга с указанным ISN уже существует');
    }
}