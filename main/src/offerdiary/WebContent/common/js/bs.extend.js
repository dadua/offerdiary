$(function() {
    //This would fix adding class on nav-lists on clicking an item..
    $('.nav>li>a').click(function(){
        var liParent$=$(this).parent(),
        navListParent$=liParent$.parent();
        navListParent$.find('li').removeClass('active');
        liParent$.addClass('active');
    });
});
