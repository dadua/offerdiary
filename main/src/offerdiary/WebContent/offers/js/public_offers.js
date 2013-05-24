var it = it || {};
it.publicOffers = it.publicOffers || {};

it.publicOffers.getOfferHtml = function (offer) {
    var offer$ = it.offer.getOfferHtml(offer);
    offer$.find('.offerFromCardAction').removeClass('hide');
    offer$.find('.trashOfferAction').addClass('hide');
    offer$.find('.offerAddToWallet').attr('id', 'offerAddToWallet_'+offer.id);
    if (offer.associatedWithLoggedInUser) {
        it.publicOffers.markAddedToWallet(offer$);
    }
    return offer$;
};

it.publicOffers.getOffer$ = function (offerId) {
    var offer$ = $('#offer_'+offerId);
    return offer$;
};

it.publicOffers.markAddedToWallet = function (offer$) {
    offer$.find('.offerFromCardAction').addClass('hide');
    offer$.find('.offerAddedToWalletAction').removeClass('hide');
};

it.publicOffers.addOfferToWallet = function(e) {
    var target = e.target,
        targetId = target.id,
        offerIdExtractRegex = /^offerAddToWallet_(.*)/,
        offerId = offerIdExtractRegex.exec(targetId)[1];

    //TODO: The add url needs to change
    $.post('addOfferFromCardToWallet.do', {id: offerId}, function (respStr) {
        var resp = $.parseJSON(respStr);
        if (resp.success) {
            var offer$ = it.publicOffers.getOffer$(offerId);
            it.publicOffers.markAddedToWallet (offer$);
        } 
    });
};

it.publicOffers.addOfferHandlers = function() {
    //TODO: Check the right specific classes 
    $('.offerShare').click(it.offer.share.show);
    //$('.offerDetail').click(it.offer.detail.show);
    $('.offerAction').tooltip();
    $('.offerAddToWallet').click(it.publicOffers.addOfferToWallet);
    
};

it.publicOffers.plotOffers = function (offers) {
    var offers$ = [];

    for (var i=0; i< offers.length; i++) {
        offers$.push(it.publicOffers.getOfferHtml(offers[i]));
    }

    $('#offerContainerFluid').rowFluidAdder({ items$: offers$,
                                            itemRowContainerTemplate$: '<ul class="thumbnails row-fluid"></ul>' });
    it.publicOffers.addOfferHandlers();

};

it.publicOffers.init = function (publicOfferCount) {
    it.publicOffers.pagination.init(publicOfferCount);
    it.offer.share.init();
    $('#searchOfferQuery').keyup(it.publicOffers.searchOffers);
};


it.publicOffers.searchOffers = function (pageNo, isPaginationClick) {
    if (!isPaginationClick) {
        return it.publicOffers.pagination.reInit();
    }
    var q = $('#searchOfferQuery').val();

    pageNo = pageNo || 1;

    $.post('searchOffers.do',
           {'searchCriteria': JSON.stringify ({q: q, pageNumber: pageNo, resultsPerPage: 10, privateSearchOnly: false}), 'public': true},
           function(response) {
               var resp = $.parseJSON(response);
               if (resp.success) {
                   it.publicOffers.plotOffers(resp.result.offers);
               } else {
                   //TODO: Error..
               }
           });
};

it.publicOffers.pagination = function () {
        
    var handlePaginationClick = function (new_page_index) {
        it.publicOffers.searchOffers(new_page_index+1, true);
        return false;
    };

    var _init = function (totalNoOfCards) {
        $('#offerPaginationContainer').pagination(totalNoOfCards, {
            items_per_page:7,
            callback:handlePaginationClick,
            num_edge_entries:1,
            num_display_entries: 5
        });
    };

    var _getTotalNoOfOffers = function () {
        var q = $('#searchOfferQuery').val();

        $.post('searchOffers.do',
               {'searchCriteria': JSON.stringify ({q: q}), 'public': true},
               function(response) {
                       var resp = $.parseJSON(response);
                       if (resp.success) {
                           _init(resp.result.totalCount);
                       } else {
                           //TODO: Error..
                       }
                   });


    };

    return {
        reInit: _getTotalNoOfOffers,
        init: _init
    };

}();

