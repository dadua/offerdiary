var it = it || {};
it.profile = it.profile || {};
it.profile.userInfo = it.profile.userInfo || {};

it.profile.userInfo.view = function(){
    var _getUserInfo$ = function (userInfo) {
        var _userInfo$ = $('.user_info_template').clone();
        userInfo = $.extend({
            name: '',
            age: '',
            dobMillis: '',
            city: '',
            emailId: '',
            nickName: '',
            mobileNumber: '',
            pinCode: ''
        }, userInfo);
        _userInfo$.removeClass('user_info_template').addClass('userInfo');
        _userInfo$.find('.userName').html(userInfo.name);
        _userInfo$.find('.userCity').html(userInfo.city);
        _userInfo$.find('.userEmail').html(userInfo.emailId);
        _userInfo$.find('.dob').html(userInfo.dobMillis);
        _userInfo$.find('.nickName').html(userInfo.nickName);
        _userInfo$.find('.pinCode').html(userInfo.pinCode);
        _userInfo$.find('.mobileNumber').html(userInfo.mobileNumber);
        return _userInfo$;
    };


    var _copyValsToEditCols = function () {
        $('.editColumn').each(function(){
            var this$ = $(this), 
                val = this$.parent().find('.valColumn').html();
            this$.find('input').val(val); 
        });

    };

    var _cancelHandler = function () {
        $('.editColumn').hide();
        $('.valColumn').show();
        $('.saveCancel').hide();
        $('.editBtn').show();
    };

    var _editHandler =  function () {
        $('.editColumn').show();
        $('.valColumn').hide();
        $('.saveCancel').show();
        $('.editBtn').hide();
        _copyValsToEditCols();
    };

    var _saveHandler = function () {


    };

    var _addHandlers = function () {
        $('.editBtn').click(_editHandler);
        $('.saveBtn').click(_saveHandler);
        $('.cancelBtn').click(_cancelHandler);
    };

    return {
        get$: _getUserInfo$,
        addHandlers: _addHandlers
    };
}();

it.profile.userInfo.refresh = function() {
    $.getJSON('getUserInfo.do', function(data){
        var userInfo = data.result;
        it.profile.userInfo.plot(userInfo);
    });
};

it.profile.userInfo.plot = function(userInfo) {
    var userInfo$ = this.view.get$(userInfo);
    $('#profileContainer').html(userInfo$);
    this.view.addHandlers();
};
