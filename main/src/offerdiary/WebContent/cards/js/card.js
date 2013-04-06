/**
 *
 */
var it = it || {};
it.card = it.card || {};

//Utility function
it.card.getParentCardId = function(actionElem) {
    var cardIdExtractRegex = /^card_(.*)/,
        parentCardElem$ = $(actionElem).closest('.card'),
        cardId = cardIdExtractRegex.exec($(parentCardElem$).attr('id'))[1];
    return cardId;
};

//Event handler functions
it.card.dismiss = function(e) {
    $(this).addClass('disable');
    e.preventDefault();
    var target = e.target,
        cardId = it.card.getParentCardId(target),
        card = {id:cardId};
    $.post('removeCardFromWallet.do', {'offerCardJson': JSON.stringify(card)}, function(data) {
        var ret = $.parseJSON(data);
        if (ret.success === true) {
            it.card.refreshAll();
            //it.offersoncard.removeFilter(cardId);
        } else {
            //Handle error case
        }
    });
};

it.card.associateWithUser = function(e) {
    var target = e.target,
        cardId = it.card.getParentCardId(target),
        card = {id:cardId};
    $.post('addCardToWallet.do', {'offerCardJson': JSON.stringify(card)}, function(data) {
        var ret = $.parseJSON(data);
        if (ret.success === true) {
            it.card.refreshAll();
        } else {
            //Handle error case
        }
    });
};

it.card.discoverRefreshHandler = function() {
    $('#cardFullName').keyup(it.card.refreshAddableCards);
    //$('#cardFullName').focus();
};

it.card.addMyCardHandlers = function () {
    $('.card-dismiss').click(it.card.dismiss);
    it.offersoncard.addHandlers();
};

it.card.addAddableCardHandlers = function () {
    $('.card-associate').click(it.card.associateWithUser);
};

it.card.addHandlers = function () {
    it.card.addMyCardHandlers();
    it.card.addAddableCardHandlers();
};

it.card.setupCardFiltersHandlers = function () {
    $('.discoverNewCards').click (function() {
        it.card.toggler.showDiscoverCards();
        $('html, body').animate({
            scrollTop: $(".discoverNewCards").offset().top
        }, 2000);
        $('#cardFullName').tooltip({title: 'Search your card', placement:'top'}).tooltip('show');
        setTimeout(function () {
            $('#cardFullName').tooltip('hide');
        }, 4000);
    }); 
    $('.myCardsFilter').click(function() {
        it.card.toggler.showDiscoverCards();
    });
};

//Card UI views Plotting/refreshing funcitons
it.card.view = function () {

    var _getMyCard$ = function (card) {
        var myCard$ = $('.my_card_template').clone();
        myCard$.removeClass('my_card_template').attr('id', 'card_' + card.id).find('.cardName').html(card.name);
        myCard$.find('.offersOnCardLabel').attr('id', 'offersOnCardLabel_' + card.id);
        myCard$.find('.providerImg').attr('src', 'images/cards/providers/' + (card.providerImgUrl || 'DEFAULT_BANK.jpg'));
        return myCard$;
    };

    var _getAddableCard$ = function (card) {
        var addableCard$ = $('.addable_card_template').clone();
        addableCard$.removeClass('addable_card_template').attr('id', 'card_' + card.id).find('.cardName').html(card.name);
        addableCard$.find('.providerImg').attr('src', 'images/cards/providers/' + (card.providerImgUrl || 'DEFAULT_BANK.jpg'));
        return addableCard$;
    };

    return {
        'getMyCard$': _getMyCard$,
        'getAddableCard$': _getAddableCard$
    };
}();

/**
 * options = {
 *              my: true,
 *              addable: false
 *          };
 */
it.card.plotCards = function(containerElemSelector, cards, options) {
    var cards$ = [], 
        settings = $.extend({my: false, addable: false}, options);

    for (var i=0; i < cards.length; i++) {
        if (settings.my) {
            cards$.push(it.card.view.getMyCard$(cards[i]));
        } else {
            cards$.push(it.card.view.getAddableCard$(cards[i]));
        }
    }

    $(containerElemSelector).rowFluidAdder({
        items$: cards$,
        itemRowContainerTemplate$: '<ul class="thumbnails"></ul>'
    });

    if (settings.my) {
        this.addMyCardHandlers ();
    } else {
        this.addAddableCardHandlers ();
    }
};


it.card.plotMyCards = function(cards) {
    this.plotCards('#myCardsContainer', cards, {my: true});
};

it.card.plotAddableCards = function(query, page_no) {
    var searchCriteriaData = {
        pageNumber : page_no || 1,
        resultsPerPage : 9,
        searchString : query
    };
    $.getJSON('searchOfferCards.do', {searchCriteria:JSON.stringify(searchCriteriaData)}, function(data){
        var result = data.result,
            totalCount = result.totalCount,
            cards = result.cards;
        $('#cardsContainer').html('');
        it.card.plotCards('#cardsContainer', cards, {addable: true});
        it.card.pagination.init(totalCount);
    });
};

it.card.refreshAddableCards = function(e) {
    var query = $('#cardFullName').val();
    it.card.plotAddableCards(query);
};

it.card.refreshMyCards = function() {
    $.getJSON('getMyCards.do', function (resp) {
        if (resp.success) {
            it.card.plotMyCards(resp.result);
        } else {
            //TODO: Handle error case
        }
    });
};

it.card.refreshAll = function () {
    it.card.refreshMyCards ();
    it.card.refreshAddableCards();
};


it.card.plotAll = function(myCardJson) {
    var myCards = JSON.parse(myCardJson);
    this.plotMyCards(myCards);
    this.plotAddableCards('');//Empty String for default set of cards..
};


it.card.pagination = (function () {
    var handlePaginationClick = function (new_page_index) {
        var query = $('#cardFullName').val();
        it.card.plotAddableCards(query, new_page_index+1);
        return false;
    };

    var _lastTotalNoOfCards = 0;

    var _init = function (totalNoOfCards) {
        if (totalNoOfCards !== _lastTotalNoOfCards) {
            $('#cardPaginationContainer').pagination(totalNoOfCards, {
                items_per_page:9,
                callback:handlePaginationClick,
                num_edge_entries:1,
                num_display_entries: 5
            });
            _lastTotalNoOfCards = totalNoOfCards;
        }
    };

    return {
        init: _init
    };

})();
 

//This would toggle between content in the container below..
//  - discoverCards 
//  - offersoncard
//  - any further features..
it.card.toggler = function () {
    return {
        showDiscoverCards: function () {
            it.offersoncard.hide();
            it.card.showDiscoverCards();
        },
        showOffersOnCard: function () {
            it.offersoncard.show();
            it.card.hideDiscoverCards();
        }
    };
}();

it.card.hideDiscoverCards = function () {
    $('.discoverCards').hide();
};

it.card.showDiscoverCards = function () {
    $('.discoverCards').show();
};
