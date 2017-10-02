let users = $('#users');

$(document).ready(() => {
    getUsers(showUsers, userTableRowMapper, null);
});

function showUsers(response, mapper) {
    response.forEach(el => users.append(mapper(el)));
}

function userTableRowMapper(el) {
    let tr = document.createElement('tr');
    tr.append(buildElement(el['id'], 'td'));
    tr.append(buildElement(el['username'], 'td'));
    return tr;
}