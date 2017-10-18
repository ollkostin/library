function buildTableData(value, mapper) {
    let td = $('<td></td>');
    if (mapper) {
        td.append(mapper(value));
    } else {
        td.append(value);
    }
    return td;
}

function getCurrentUser(success, error) {
    $.ajax({
        type: 'GET',
        url: 'api/user/currentUser',
        success: success,
        error: error
    });
}