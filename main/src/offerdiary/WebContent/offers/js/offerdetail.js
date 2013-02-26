var it = it || {};
it.offer = it.offer || {};
it.offer.detail = it.offer.detail || {};

it.offer.detail.getOfferHtml = function(offer) {

    var offerTemplate$ = $('.offer_detail_ui_template').clone().removeClass('offer_detail_ui_template hide').attr('id', 'offer_' + offer.id);
    //Setting values from offer vo to the template..
    if (offer.sourceVendor) {
        offerTemplate$.find('.sourceVendor-logoUrl').attr('alt', offer.sourceVendor.name).attr('src', 'images/stores/'+ (offer.sourceVendor.logoUrl || 'defaultVendor.jpg'));
        offerTemplate$.find('.vendorName').html(offer.sourceVendor.name);
        offerTemplate$.find('.vendorUrl').html(offer.sourceVendor.siteUrl).attr('href', offer.sourceVendor.siteUrl);
    } else {
        offerTemplate$.find('.sourceVendor-logoUrl').attr('src', 'images/stores/defaultVendor.jpg');
    }
    offerTemplate$.find('.offerExpiryDate').html(it.offer.getReadableDate(offer));
    offerTemplate$.find('.offerTrash').attr('id', 'offerTrash_' + offer.id);
    offerTemplate$.find('.offerShare').attr('id', 'offerShare_' + offer.id);
    offerTemplate$.find('.offerDetail').parent().attr('href', 'getOfferDetail.do?id=' + offer.uniqueId);

    offerTemplate$.find('.offerCodeVal').html(offer.offerCode);
    offerTemplate$.find('.offerDesc').html(offer.description);
    return offerTemplate$;
};


it.offer.detail.view = function () {

}();


it.offer.detail.plot = function (offer) {
    var offer$ = this.getOfferHtml(offer);
    $('#offerContainerFluid').html(offer$);
};

it.offer.detail.trashOffer = function(e) {
    var target = e.target;
    var targetId = target.id;
    var offerIdExtractRegex = /^offerTrash_(.*)/;
    var offerId = offerIdExtractRegex.exec(targetId)[1];
    var offerIds = [];
    offerIds.push(offerId);
    $.post('deleteOffers.do', {'offerIds': JSON.stringify(offerIds)}, function(data) {
        var ret = $.parseJSON(data);
        if (ret.success === true) {
            $('#'+targetId).tooltip('hide');
            $('#offer_'+offerId).remove();
            it.offer.searchOffers();
        } else {
            //Handle error case
        }
    });
};


it.offer.detail.addOneHandlers = function () {

};

it.offer.detail.addHandlers = function () {
    $('.offerTrash').click(it.offer.trashOffer).tooltip();
    $('.offerShare').click(it.offer.share.show).tooltip();
    $('.offerDetail').tooltip();
};

it.offer.detail.init = function (offers) {
    it.offer.detail.plot(offers);
    it.offer.detail.addOneHandlers();
    it.offer.detail.addHandlers();
   //it.offer.share.init();
};

