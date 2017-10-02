let offset = 0;
let limit = 5;
let totalCount;
let books = $('#books');

$(document).ready(() => {
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
    response.data.forEach(el => books.append(mapper(el)));
}

function bookTableRowMapper(el) {
    let tr = document.createElement('tr');
    tr.append(buildElement(el['isn'], 'td'));
    tr.append(buildElement(el['name'], 'td'));
    tr.append(buildElement(el['author'], 'td'));
    return tr;
}


function getMoreBooks() {
    getBooks(offset, limit, showBooks, bookTableRowMapper, null);
}