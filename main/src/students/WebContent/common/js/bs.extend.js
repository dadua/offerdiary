var it = it || {};
it.bsextends = it.bsextends || {};

it.bsextends.filters = function () {

    //This would fix adding class on nav-lists on clicking an item..
    var _setClickedItemActive = function(){
        var liParent$=$(this).parent(),
        navListParent$=liParent$.parent();
        navListParent$.find('li').removeClass('active');
        liParent$.addClass('active');
    }, 
    _setItemActiveHandler = function () {
        $('.nav>li>a').click(_setClickedItemActive);
    },
    _unSetItemActiveHandler = function () {
        $('body').off('click', '.nav>li>a', _setClickedItemActive);
    };

    return {
        initClickHandler: _setItemActiveHandler,
        resetClickHandler: _unSetItemActiveHandler,
        reInitClickHandler: function () {
            _unSetItemActiveHandler();
            _setItemActiveHandler();
        }
    };
}();

$(function() {
    it.bsextends.filters.initClickHandler ();
});
