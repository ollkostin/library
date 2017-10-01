function getBooks(offset, limit, success, mapper, error) {
    let options = offset || limit ? '?' : '';
    if (offset)
        options += 'offset=' + offset;
    if (offset && limit)
        options += '&';
    if (limit)
        options += 'limit=' + limit;
    $.ajax({
        type: 'GET',
        url: 'api/book/' + options,
        success: resp => success(resp, mapper),
        error: error
    });
}