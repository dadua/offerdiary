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

it.offersoncard.plotAllOffersForCard = function (cardId) {

    var q = $('#searchOffersOnCards').val();

    $.post('searchOffers.do',
           {'searchCriteria': JSON.stringify ({q: q, cardId: cardId, offersOnMyCardsOnly: true, privateSearchOnly: false})},
           function(response) {
               var resp = $.parseJSON(response);
               if (resp.success) {
                   //it.offer.plotAll(resp.result.offers, true);
                   it.offersoncard.plotOffers(resp.result.offers);
               } else {
                   //TODO: Error..
               }
           });

           /*
    $.getJSON('getOffersOnCard.do', {cardIdKey: cardId}, function (resp) {

        if (resp.success) {
            it.offersoncard.plotOffers(resp.result.offers);
        } else {
            //TODO: Error case
        }
    });
   */
};

it.offersoncard.plotOffersForCard = function (e) {
    var offersOnCardLabelTarget = e.target,
        cardId = it.offersoncard.getCardId(offersOnCardLabelTarget),
        cardName = it.offersoncard.getCardName(offersOnCardLabelTarget);

    it.offersoncard.plotFilter({id:cardId, name: cardName});
    it.offersoncard.plotAllOffersForCard(cardId);
    it.card.toggler.showOffersOnCard();
};

it.offersoncard.scrollTopTo = function () {
    //Have to account for the default body margin given..
    $('html, body').animate({scrollTop:$('#offerOnCardContainer').position().top}, 'slow');
};

it.offersoncard.addHandlers = function () {
    $('.offersOnCardLabel').click (it.offersoncard.plotOffersForCard);
    it.offersoncard.setFilterClickHandler();
};
