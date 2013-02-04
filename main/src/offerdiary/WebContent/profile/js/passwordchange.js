var it = it || {};
it.profile = it.profile || {};
it.profile.password= it.profile.password|| {};

it.profile.password.change = function () {

};


it.profile.password.view = function () {

    var _get$ = function () {
        var _changePassword$ = $('.change_password_template').clone();
        _changePassword$.removeClass('change_password_template').addClass('changePassword');
        return _changePassword$;
    },

    addHandlers = function () {
        $('.changePassword').find('#submitChangePassword').click(it.profile.password.change);
    };

    return {
        get$: _get$,
        addHandlers: addHandlers
    };
}();

it.profile.password.plotChangeUI = function () {

    var changePassword$ = this.view.get$();
    $('#profileContainer').html(changePassword$);
    this.view.addHandlers();
};
