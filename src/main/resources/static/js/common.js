function buildTableData(value, mapper) {
    let td = $('<td></td>');
    if (mapper) {
        td.append(mapper(value));
    } else {
        td.append(value);
    }
    return td;
}

function getCurrentUserId(success, error) {
    $.ajax({
        type: 'GET',
        url: 'api/user/currentUsername',
        success: success,
        error: error
    });
}

function showErrorAlert(resp) {
    alert(resp);
}