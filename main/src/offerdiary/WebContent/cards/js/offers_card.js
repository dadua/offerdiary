var it = it || {};
it.offersoncard = it.offersoncard || {};

it.offersoncard.show = function () {
    $('.offerOnCard').show();
};

it.offersoncard.hide = function () {
    $('.offerOnCard').hide();
};

it.offersoncard.getCardName = function (offersOnCardLabelTarget) {
    return $(offersOnCardLabelTarget).parent().find('.cardName').html();
};

it.offersoncard.getCardId = function (offersOnCardLabelTarget) {
    var cardIdExtractRegex = /^offersOnCardLabel_(.*)/,
    cardId = cardIdExtractRegex.exec($(offersOnCardLabelTarget).attr('id'))[1];
    return cardId;
};

it.offersoncard.getOfferHtml = function (offer) {
    var offer$ = it.offer.getOfferHtml(offer);
    offer$.find('.offerFromCardAction').removeClass('hide');
    offer$.find('.trashOfferAction').addClass('hide');
    offer$.find('.offerAddToWallet').attr('id', 'offerAddToWallet_'+offer.id);
    if (offer.associatedWithLoggedInUser) {
        it.offersoncard.markAddedToWallet(offer$);
    }
    return offer$;
};

it.offersoncard.getOffer$ = function (offerId) {
    var offer$ = $('#offer_'+offerId);
    return offer$;
};

it.offersoncard.markAddedToWallet = function (offer$) {
    //TODO: Make this offer checked..
    //Probably add a popover
    offer$.find('.offerFromCardAction').addClass('hide');
    offer$.find('.offerAddedToWalletAction').removeClass('hide');
};


it.offersoncard.addOfferToWallet = function(e) {
    var target = e.target,
        targetId = target.id,
        offerIdExtractRegex = /^offerAddToWallet_(.*)/,
        offerId = offerIdExtractRegex.exec(targetId)[1];

    $.post('addOfferFromCardToWallet.do', {id: offerId}, function (respStr) {
        var resp = $.parseJSON(respStr);
        if (resp.success) {
            var offer$ = it.offersoncard.getOffer$(offerId);
            it.offersoncard.markAddedToWallet (offer$);
        } 
    });
};

it.offersoncard.addOfferHandlers = function() {
    $('.offerShare').click(it.offer.share.show);
    $('.offerDetail').click(it.offer.detail.show);
    $('.offerAction').tooltip();
    $('.offerAddToWallet').click(it.offersoncard.addOfferToWallet);
    
};

it.offersoncard.plotOffers = function (offers) {
    var offers$ = [];

    for (var i=0; i< offers.length; i++) {
        offers$.push(it.offersoncard.getOfferHtml(offers[i]));
    }

    $('#offerOnCardContainer').rowFluidAdder({ items$: offers$,
                                            itemRowContainerTemplate$: '<ul class="thumbnails row-fluid"></ul>' });
    it.offersoncard.addOfferHandlers();

};

it.offersoncard.getFilterCardId = function (target) {
    var cardIdExtractRegex = /^offersOnCardFilters_(.*)/,
        cardId = cardIdExtractRegex.exec($(target).parent().attr('id'))[1];
    return cardId;
};

it.offersoncard.filterClickHandler = function (e) {
    var target = e.target,
        cardId = it.offersoncard.getFilterCardId(target);

    it.offersoncard.plotAllOffersForCard(cardId);
    it.card.toggler.showOffersOnCard();
    //it.offersoncard.scrollTopTo();
};


it.offersoncard.plotFilterActive = function (cardId) {
    var cardFiltersList$ = $('#cardsFilters .nav-list');
    cardFiltersList$.find('li>a').parent().removeClass('active');
    cardFiltersList$.find('#offersOnCardFilters_'+ cardId).addClass('active');
};

it.offersoncard.plotFilter = function (myCard) {
    var cardFiltersList$ = $('#cardsFilters .nav-list');
    if (cardFiltersList$.find('#offersOnCardFilters_'+myCard.id).size() === 0) {
        cardFiltersList$.append('<li class="offersOnCardFilters active" id="offersOnCardFilters_'+ myCard.id + '"><a href="#">'+myCard.name + '</a></li>');
        it.offersoncard.setFilterClickHandler();
    }
    it.offersoncard.plotFilterActive(myCard.id);
};

it.offersoncard.plotFilters = function (mycards){
    for (var i=0;i<mycards.length;i++) {
        var card = mycards[i];
        this.plotFilter(card);
    }
};

it.offersoncard.removeFilter = function (cardId) {
    $('.offersOnCardFilters').filter(function(){
        var cardIdExtractRegex = /^offersOnCardFilters_(.*)/,
            cardId = cardIdExtractRegex.exec($(this).attr('id'))[1];
        if (iCardId === cardId) {
            return true;
        } else {
            return false;
        }
    }).remove();

};

it.offersoncard.setFilterClickHandler = function () {
    $('body').off('click', '.offersOnCardFilters', it.offersoncard.filterClickHandler);
    $('body').on('click', '.offersOnCardFilters', it.offersoncard.filterClickHandler);
    it.bsextends.filters.reInitClickHandler();
};

it.offersoncard.state = function () {
    var _lastClickedCardId = null,
        _offerPageNo = 1;
    return {
        getLastClickedCardId: function () {
            return _lastClickedCardId;
        },
        setLastClickedCardId: function(lastClickedCardId) {
            _lastClickedCardId = lastClickedCardId;
        },
        getOfferPageNo: function () {
            return _offerPageNo;
        },
        setOfferPageNo: function (offerPageNo) {
            _offerPageNo = offerPageNo;
        },
        resetOfferPageNo: function () {
            _offerPageNo = 1;
        }
    };
}();

it.offersoncard.pagination = function () {
    
    var handlePaginationClick = function (new_page_index) {
        it.offersoncard.plotAllOffersForCard(null, new_page_index+1);
        return false;
    };


    var _init = function (totalNoOfCards) {
        $('#offerOnCardPaginationContainer').pagination(totalNoOfCards, {
            items_per_page:10,
            callback:handlePaginationClick,
            num_edge_entries:1,
            num_display_entries: 5
        });
    };

    var _getTotalNoOfOffers = function (cardId) {
            var q = $('#searchOffersOnCards').val();

            $.post('searchOffers.do',
                   {'searchCriteria': JSON.stringify ({q: q,
                                                      cardId: cardId,
                                                      privateSearchOnly: false,
                                                      cardOffersOnly: true})
                   }, function(response) {
                       var resp = $.parseJSON(response);
                       if (resp.success) {
                           _init(resp.result.totalCount);
                       } else {
                           //TODO: Error..
                       }
                   });


    };

    return {
        initForCardWithId: _getTotalNoOfOffers
    };

 
}();

it.offersoncard.plotAllOffersForCard = function (cardId, pageNo) {

    if (typeof cardId !== 'string') {
        cardId = it.offersoncard.state.getLastClickedCardId();
    }

    var q = $('#searchOffersOnCards').val();
    pageNo = pageNo || 1;

    $.post('searchOffers.do',
           {'searchCriteria': JSON.stringify ({q: q,
                                              cardId: cardId,
                                              privateSearchOnly: false,
                                              cardOffersOnly: true,
                                              pageNumber: pageNo })}, function(response) {
               var resp = $.parseJSON(response);
               if (resp.success) {
                   it.offersoncard.plotOffers(resp.result.offers);
               } else {
                   //TODO: Error..
               }
           });
};

it.offersoncard.plotOffersForCard = function (e) {
    var offersOnCardLabelTarget = e.target,
        cardId = it.offersoncard.getCardId(offersOnCardLabelTarget),
        cardName = it.offersoncard.getCardName(offersOnCardLabelTarget);

    it.offersoncard.state.setLastClickedCardId(cardId);
    it.offersoncard.state.resetOfferPageNo();
    it.offersoncard.plotFilter({id:cardId, name: cardName});
    it.offersoncard.pagination.initForCardWithId(cardId);
    //it.offersoncard.plotAllOffersForCard(cardId);
    it.card.toggler.showOffersOnCard();
};

it.offersoncard.scrollTopTo = function () {
    //Have to account for the default body margin given..
    $('html, body').animate({scrollTop:$('#offerOnCardContainer').position().top}, 'slow');
};

it.offersoncard.addHandlers = function () {
    $('.offersOnCardLabel').click (it.offersoncard.plotOffersForCard);
    it.offersoncard.setFilterClickHandler();
    $('#searchOffersOnCards').keyup(it.offersoncard.plotAllOffersForCard);
};
