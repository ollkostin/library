function getUsers(success, mapper, error) {
    $.ajax({
        type: 'GET',
        url: 'api/user/',
        success: resp => success(resp, mapper),
        error: error
    });
}