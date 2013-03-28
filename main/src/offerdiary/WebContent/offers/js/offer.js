var it = it || {};
it.offer = it.offer || {};

it.offer.appendOffers = function(offers, isOldAddition) {

    for (var i=0; i<offers.length;i++) {
        this.appendOffer(offers[i], isOldAddition);
    }
};

it.offer.getReadableDate = function (offer) {
    var expiryMillisUtc = offer.expiryDateInMillis;
    return $.datepicker.formatDate('dd M, yy', new Date(expiryMillisUtc));
};


it.offer.getDaysToExpire = function(offer) {
    var expiryMillisUtc = offer.expiryDateInMillis;
    var d = new Date();
    var currentMillisUtc = d.getTime();
    var millisToExpire = (expiryMillisUtc - currentMillisUtc);
    var daysToExpire = Math.floor(millisToExpire/(1000*60*60*24));
    return daysToExpire;
};

it.offer.getExpiryDateBasedClass = function(daysToExpire) {
    if(daysToExpire < 0 ) {
        return '';
    } else if(daysToExpire < 14) {
        return 'label-important';
    } else if (daysToExpire < 30) {
        return 'label-warning';
    } else if (daysToExpire < 90) {
        return 'label-info';
    } else if (daysToExpire < 120) {
        return 'label-inverse';
    } else {
        return 'label-success';
    }
};

it.offer.getFormattedDaysToExpire = function (daysToExpire) {
    return daysToExpire + (daysToExpire==1?' day':' days')+' to expire';
};

it.offer.getFormattedMonthsToExpire = function(daysToExpire) {
    var monthsToExpire = Math.floor(daysToExpire/30);
    return monthsToExpire + (monthsToExpire==1?' month, ':' months, ') + this.getFormattedDaysToExpire(Math.floor(daysToExpire%30));
};

it.offer.getFormattedYearsToExpire = function(daysToExpire) {
    var yearsToExpire = Math.floor(daysToExpire/365);
    return yearsToExpire + (yearsToExpire==1?' year, ':' years, ') + this.getFormattedMonthsToExpire(Math.floor(daysToExpire%365));
};

it.offer.getFormattedTimeToExpire = function(daysToExpire) {
    if (daysToExpire < 30) {
        return this.getFormattedDaysToExpire(daysToExpire);
    } else if (daysToExpire < 365) {
        return this.getFormattedMonthsToExpire(daysToExpire);
    } else {
        return this.getFormattedYearsToExpire(daysToExpire);
    }
};

it.offer.getCompactFormattedTimeToExpire = function(timeToExpireString) {
    if (timeToExpireString.length > 16) {
        var displayedTimeToExpire = timeToExpireString.substring(0, 12),
        undisplayedTimeToExpire = timeToExpireString.substring(16, timeToExpireString.length);
        displayedTimeToExpire += '<span class="hiddenTillAddSuccess" style="display:none" >';
        displayedTimeToExpire += timeToExpireString.substring(12,16);
        displayedTimeToExpire += '</span>';
        displayedTimeToExpire += '<span style="display:none" class="hiddenPartOfTime">';
        displayedTimeToExpire += undisplayedTimeToExpire;
        displayedTimeToExpire += '</span>';
        displayedTimeToExpire += '<a class="daysToExpireDetails" href="#" title="'+timeToExpireString +'">...</a>';
        return displayedTimeToExpire;
    } else {
        return timeToExpireString;
    }
};


it.offer.setOfferDataToOffer$ = function (offer, offerTemplate$) {
    //Setting values from offer vo to the template..
    if (offer.targetVendor) {
        offerTemplate$.find('.targetVendor-logoUrl').attr('alt', offer.targetVendor.name).attr('src', 'images/stores/'+ (offer.targetVendor.logoUrl || 'defaultVendor.jpg'));
        offerTemplate$.find('.vendorName').html(offer.targetVendor.name);
        offerTemplate$.find('.vendorUrl').html(offer.targetVendor.siteUrl).attr('href', offer.targetVendor.siteUrl);
    } else {
        offerTemplate$.find('.targetVendor-logoUrl').attr('src', 'images/stores/defaultVendor.jpg');
    }
    if (offer.expiryDateInMillis!== 0) {
        offerTemplate$.find('.offerExpiryDate').html(it.offer.getReadableDate(offer));
    } else {
        offerTemplate$.find('.expiryDate').hide();
    }

    if (typeof offer.source === 'string') {
        offerTemplate$.find('.offerSourceContainer').removeClass('hide').find('.offerSourceVal').html(offer.source);
    }
    offerTemplate$.find('.offerTrash').attr('id', 'offerTrash_' + offer.id);
    offerTemplate$.find('.offerShare').attr('id', 'offerShare_' + offer.id);
    offerTemplate$.find('.offerDetail').attr('id', 'offerDetail_' + offer.id);

    if (typeof offer.offerCode === 'string' && offer.offerCode !== '') {
        offerTemplate$.find('.offerCodeVal').html(offer.offerCode);
    } else {
        offerTemplate$.find('.offerCode').addClass('hide');
    }
    offerTemplate$.find('.offerDesc').html(offer.description);
    return offerTemplate$;
	
};

it.offer.getOfferHtml = function(offer) {

    var offerTemplate$ = $('.offer_ui_template').clone().removeClass('offer_ui_template hide').attr('id', 'offer_' + offer.id);
    return it.offer.setOfferDataToOffer$(offer, offerTemplate$);
};


it.offer.view = function () {

}();


it.offer.refreshOffers = function () {
	$.getJSON('getMyOffers.do', function (resp) {
            if (resp.success) {
                it.offer.plotAll(resp.result, true);
            } else {
                //TODO: Error..
            }
        });
};


it.offer.plotAll = function (offers, isOldAddition) {
    var offers$ = [],
        addRowOffer$ = $('.addOfferBlock');
    offers$.push(addRowOffer$);

    //TODO: add addOffer$ to the offers$
    for (var i=0; i< offers.length; i++) {
        offers$.push(this.getOfferHtml(offers[i]));
    }

    $('#offerContainerFluid').rowFluidAdder({ items$: offers$,
                                      itemRowContainerTemplate$: '<ul class="thumbnails row-fluid"></ul>' });
    it.offer.addHandlers();
};

it.offer.appendOffer = function (offer, isOldAddition) {

    /*
    var daysToExpire = this.getDaysToExpire(offer);
    var labelClass = this.getExpiryDateBasedClass(daysToExpire);
    var daysToExpireHtml = '<div class="daysToExpire label '+ labelClass + '" id="offerExpire_'+ offer.id + '">';
    var formattedTimeToExpire = this.getFormattedTimeToExpire(daysToExpire);
    if (daysToExpire < 0) {
        daysToExpireHtml += 'Offer Expired!!';
    } else {
        var compactFormattedTime = this.getCompactFormattedTimeToExpire(formattedTimeToExpire);
        daysToExpireHtml += compactFormattedTime;
    }
    daysToExpireHtml += '</div>';
       var offerHtml = '<li class="span3" id="offer_';
       offerHtml += offer.id;
       offerHtml += '" >';
       offerHtml += '<span class="addingDoneLabel label label-success">Done!</span>';
       offerHtml += daysToExpireHtml;
       offerHtml += '<div class="thumbnail"><span class="icon-trash icon-white pull-right offer-trash" title="Trash Me" id="offerTrash_';
       offerHtml += offer.id;
       offerHtml += '" ></span><h3>Details:';
       offerHtml += offer.description;
       offerHtml += '</h3><h5>Code:';
       offerHtml += offer.offerCode;
       offerHtml += '</h5><h5>Discount:';
       offerHtml += offer.discountValue;
       offerHtml +=  '</h5></div></li>';
       */

    var offer$ = this.getOfferHtml(offer);
    $(offer$).appendTo('#offerContainerFluid');

    /*
       $('#offerExpire_'+offer.id).position({
my: 'center top',
at: 'center top',
of: '#offer_' + offer.id,
offset: '0 0'
});
$('#offer_'+offer.id + ' .thumbnail').addClass('offer', 9000);
if (isOldAddition) {
$('#offer_'+offer.id+ ' span.addingDoneLabel').hide();
$('.hiddenTillAddSuccess').show();
} else {
$('#offer_'+offer.id+ ' span.addingDoneLabel').hide('highlight', 9000, function(){
$(this).parent().find('span.hiddenTillAddSuccess').show();
$(this).remove();
});
}
*/
};

it.offer.clearofferormVals = function () {
    $('.offerDetail').val('');
    $('#emailNotify').addClass('active');
    $('#fbNotify').removeClass('active');
};

it.offer.saveOfferFromWizard= function (offerData) {
    var offerVO = {
        offerCode: offerData.code,
        description: offerData.description,
        expiryDateInMillis: offerData.expiryDateInMillis,
        targetVendor: offerData.vendor
    },
    offers = [];
    offers.push(offerVO);

    $.post('saveOffers.do',
           {'offers': JSON.stringify(offers)},
           function (data) {
               var ret = $.parseJSON(data);
               if (ret.success === true) {
                   it.offer.searchOffers();
               } else {
                   //Handle error case
               }
               $('#addOfferModal').modal('hide');
           });

};

it.offer.addOffer = function () {
    var code = $('#code').val(),
    expiryDateInMillis = $('#expiryDate').datepicker('getDate').getTime(),
    detail = $('#discountDetails').val(),
    emailNotify = $('#emailNotify').hasClass('active'),
    fbNotify = $('#fbNotify').hasClass('active'),
    notifyVO = {
        emailNotify: emailNotify,
        fbNotify: fbNotify
        //Will include time to notify etc. here
    },
    offer = {
        offerCode: code,
        description: detail,
        expiryDateInMillis: expiryDateInMillis,
        notifyVO: notifyVO
    },		
    offers = [],
    vendorId;
    offers.push(offer);
    $.post('saveOffers.do', 
           {'offers': JSON.stringify(offers)},
           function (data) {
               var ret = $.parseJSON(data);
               if (ret.success === true) {
                   it.offer.appendOffers(ret.result);
                   it.offer.addHandlers();
               } else {
                   //Handle error case
               }
               $('#addOfferModal').modal('hide');
           });
};

it.offer.trashOffer = function(e) {
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


it.offer.addOneHandlers = function () {
    $('#searchOfferQuery').keyup(it.offer.searchOffers);
    $('.offerFilters>li').click(function() {
        it.offer.searchOffers();
    });

};

it.offer.addHandlers = function () {
    $('#addOfferWizardBtn').click(function(){
        it.offer.addwizard.getWizard().reInit();
        it.offer.addwizard.getWizard().show();
    });
    $('.offerTrash').click(it.offer.trashOffer);
    $('.offerShare').click(it.offer.share.show);
    $('.offerDetail').click(it.offer.detail.show);
    $('.offerAction').tooltip();
    
    /*
    $('.icon-trash').hover(function(e) {
        $(this).removeClass('icon-white');
    }, function(e){
        $(this).addClass('icon-white');
    });
    $('.offer-trash').click(it.offer.trashOffer).tooltip();
    $('.daysToExpireDetails').tooltip({
        placement: 'right'
    }).click(function(e){
        $(this).parent().find('.hiddenPartOfTime').show();
        $(this).tooltip('hide').remove();
    });
       $('.checkBoxSelected').live('click', function() {
       $(this).removeClass('checkBoxSelected').addClass('checkBoxUnSelected');
       });
       $('.checkBoxUnSelected').live('click', function(){
       $(this).removeClass('checkBoxUnSelected').addClass('checkBoxSelected');
       });
       */
};

it.offer.getUniqueFilter = function () {
    var classToUniqueFilterMap = {
        'allOffers': 'all',
        'validOffers': 'valid',
        'expiredOffers': 'expired',
        'expires7daysOffers': 'expiringInNext7Days',
        'expires1monthOffers': 'expiringInNext30Days',
        'addedLastweekOffers': 'addedInLast7Days',
        'addedLastmonthOffers': 'addedInLast30Days'
    }, 
    activeClasses = $('.offerFilters>.active').attr('class').split(' '),
    activeClass = activeClasses[0]==='active'? activeClasses[1]: activeClasses[0];
    return classToUniqueFilterMap[activeClass] || '';
};

it.offer.searchOffers = function (pageNo, isPaginationClick) {
    if (!isPaginationClick) {
        return it.offer.pagination.reInit();
    }
    var q = $('#searchOfferQuery').val(),
        uniqueFilter = it.offer.getUniqueFilter ();

    pageNo = pageNo || 1;

    $.post('searchOffers.do',
           {'searchCriteria': JSON.stringify ({q: q, uniqueFilter: uniqueFilter, pageNumber: pageNo, resultsPerPage: 7})},
           function(response) {
               var resp = $.parseJSON(response);
               if (resp.success) {
                   it.offer.plotAll(resp.result.offers, true);
               } else {
                   //TODO: Error..
               }
           });
};

it.offer.init = function (validOfferCount) {
    it.offer.pagination.init(validOfferCount);
    it.offer.addOneHandlers();
};


it.offer.pagination = function () {
    
    var handlePaginationClick = function (new_page_index) {
        it.offer.searchOffers(new_page_index+1, true);
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
        var q = $('#searchOfferQuery').val(),
            uniqueFilter = it.offer.getUniqueFilter ();

        $.post('searchOffers.do',
               {'searchCriteria': JSON.stringify ({q: q, uniqueFilter: uniqueFilter})},
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

