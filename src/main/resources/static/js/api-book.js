function getBooks(offset, limit, success, mapper, error) {
    $.ajax({
        type: 'GET',
        url: 'api/book/',
        data: {offset, limit},
        success: resp => success(resp, mapper),
        error: error
    });
}