let offset = 0;
let limit = 5;
let books = $('#books');

$(document).ready(()=> getBooks(offset, limit, showBooks, bookTableRowMapper, null));

function showBooks(bookList, mapper) {
    bookList.forEach(el => books.append(mapper(el)));
}

function bookTableRowMapper(el) {
    let tr = document.createElement('tr');
    tr.append(buildElement(el['isn'], 'td'));
    tr.append(buildElement(el['name'], 'td'));
    tr.append(buildElement(el['author'], 'td'));
    return tr;
}
