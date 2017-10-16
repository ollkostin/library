let totalCount;
let ascAuthor = true;
let ascName = false;
let params;
let currentUsername;
$('#books').on('click', '.take-book', onClickTakeBook);
$('#books').on('click', '.return-book', onClickReturnBook);
$('#modal-window').on('hidden.bs.modal', clearBookModalValues);

$(document).ready(function () {
    getCurrentUserId(function (username) {
        currentUsername = username;
    });
    params = {
        offset: 0,
        limit: 5
    };
    getMoreBooks(params);
});

function showBooks(response, mapper) {
    params.offset = response.offset;
    params.limit = response.limit;
    totalCount = response.totalCount;
    if (totalCount === (params.offset + 1) * params.limit)
        $('#show-more').hide();
    else
        params.offset++;
    response.data.forEach(function (el) {
        $('#books').append(mapper(el));
    });
}

function bookTableRowMapper(el) {
    let tr = $('<tr></tr>');
    tr.append(buildTableData(el['isn'], buildBookLinkCell));
    tr.append(buildTableData(el['name']));
    tr.append(buildTableData(el['author']));
    tr.append(buildTableData(el['username'], buildBookUserCell));
    tr.append(buildTableData(buildBookDeleteCell));
    return tr;
}

function getMoreBooks(params) {
    getBooks(params, showBooks, bookTableRowMapper);
}

function buildBookLinkCell(isn) {
    let a = $('<a href="" data-toggle="modal">' + isn + '</a>');
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
    button.click(onDeleteBookButtonClick);
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
    }, bookError);
}

function onClickReturnBook() {
    let currentRow = $(this).closest("tr");
    let isnCell = currentRow.find("td:eq(0)");
    let isn = isnCell.text();
    returnBook(isn, function (resp) {
        let buttonCell = currentRow.find("td:eq(3)");
        buttonCell.empty();
        buttonCell.append(buildBookUserCell(null));
    }, bookError);
}

function onDeleteBookButtonClick() {
    let currentRow = $(this).closest("tr");
    let isnCell = currentRow.find("td:eq(0)");
    let isn = isnCell.text();
    if (confirm("Удалить книгу?")) {
        deleteBook(isn, function (resp) {
            currentRow.remove();
        }, bookError);
    }
}

function onClickShowModalCreateBook() {
    $('#modal-action').on('click', onClickCreateBook);
    $('#modal-window').modal('show');
}

function onClickCreateBook() {
    let book = buildBookDto($('#isn').val(), $('#name').val(), $('#author').val());
    createBook(book, function (resp) {
        alert('Книга успешно сохранена');
        $('#modal-window').modal('toggle');
        clearBookModalValues();
    }, bookError);
}

function clearBookModalValues() {
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
    $('#modal-window').modal('show');
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
        location.reload();
    }, bookError);
}

function bookError(resp) {
    if (resp.responseJSON.code === "400" && resp.responseJSON.message === 'user') {
        alert('Пользователь не был найден');
    } else if (resp.responseJSON.code === "409") {
        if (resp.responseJSON.message === 'isn')
            alert('ISN не указан или указан неверно');
        else if (resp.responseJSON.message === 'book')
            alert('Книга с указанным ISN уже существует');
    } else if (resp.responseJSON.code === '500') {
        alert(resp.responseJSON.message);
    }
}

function onClickSortByAuthor() {
    params = {
        offset: 0,
        limit: 5,
        order: 'author',
        desc: ascAuthor
    };
    $('#td-name').removeClass('sort-desc sort-asc');
    $('#td-author').removeClass('sort-desc sort-asc')
        .addClass(ascAuthor ? 'sort-desc' : 'sort-asc');
    $('#books').empty();
    getMoreBooks(params, showBooks);
    ascAuthor = !ascAuthor;
}

function onClickSortByName() {
    params = {
        offset: 0,
        limit: 5,
        order: 'name',
        desc: ascName
    };
    $('#td-author').removeClass('sort-desc sort-asc');
    $('#td-name').removeClass('sort-desc sort-asc')
        .addClass(ascName ? 'sort-desc' : 'sort-asc');
    $('#books').empty();
    getMoreBooks(params, showBooks);
    ascName = !ascName;
}