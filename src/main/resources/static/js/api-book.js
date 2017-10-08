function getBooks(offset, limit, success, mapper, error) {
    $.ajax({
        type: 'GET',
        url: 'api/book/',
        data: {offset, limit},
        success: function (resp) {
            success(resp, mapper);
        },
        error: error
    });
}

function takeBook(isn, success, error) {
    $.ajax({
        type: 'POST',
        url: 'api/book/' + isn + '/take',
        success: success,
        error: error
    });
}

function returnBook(isn, success, error) {
    $.ajax({
        type: 'POST',
        url: 'api/book/' + isn + '/return',
        success: success,
        error: error
    });
}

function deleteBook(isn, success, error) {
    $.ajax({
        type: 'DELETE',
        url: 'api/book/' + isn,
        success: success,
        error: error
    });
}

function createBook(book, success, error) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'api/book/',
        data: JSON.stringify(book),
        success: success,
        error: error
    });
}

function editBook(book, success, error) {
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: 'api/book/',
        data: JSON.stringify(book),
        success: success,
        error: error
    });
}