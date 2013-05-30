/**
 *
 */

var it = it || {};
it.vendor = it.vendor || {};
it.vendor.view = it.vendor.view || {};

//This object has all the html related data and methods
it.vendor.view = function () {
    var threeVendorsContainerHtml = '<ul class="thumbnails currentSearchedVendors row-fluid"></ul>';
    var vendorHtml = '<li data-trigger="hover" class="span4 vendorSearch unselected" title="Default Vendor" ><div class="vendorImage thumbnail"><img src="images/stores/blankVendor.png" /></div><div class="vendorNameVal hide"></div></li>',
        vendorSearchContainerId = 'searchedVendors',
        containerId$;

    var fourVendorsIncarouselItemHtml = '<div class="item"><ul class="thumbnails"></ul></div>',
        eachVendorInCarouselHtml = '<li data-trigger="hover" class="thumbnail vendorCarouselItem" title="Default Vendor" ><div class="vendorImage thumbnail"><img src="images/stores/blankVendor.png" /></div><div class="vendorNameVal hide"></div></li>';

    return {
        getHtmlTemplate: function () {
            return vendorHtml;
        },
        getVendorSearchedContainerId : function () {
            return vendorSearchContainerId;
        },
        getContainer$: function () {
            return containerId$ || (containerId$ = $('#'+ vendorSearchContainerId));
        },
        getVendorsHtml: function () {
            return threeVendorsContainerHtml;
        },
        getEachVendorInCarouselHtml: function () {
            return eachVendorInCarouselHtml;
        },
        getvendorsInCarouselItemHtml: function () {
            return fourVendorsIncarouselItemHtml;
        }
    };
}();

it.vendor.setImage = function (vendor, vendor$) {
    if (vendor.logoUrl) {
        vendor$.find('img').attr('src', 'images/stores/' + vendor.logoUrl).error(function(){

            $(this).attr('src', 'images/stores/blankVendor.png');
            $(this).parent().parent().find('.vendorNameVal').html(vendor.name).removeClass('hide');
        });

    } else {
        vendor$.find('.vendorNameVal').html(vendor.name).removeClass('hide');
        //TODO: No Image found..
        //Have to put the vendor.name string to
        //the right of the trimmed down image..
    }
};

it.vendor.setNameDesc = function (vendor, vendor$) {
    vendor$.attr( {
        'title': vendor.name,
        'data-content': vendor.description,
        'data-vendor-name': vendor.name
    });
};

it.vendor.get$ = function (vendor) {
    var vendorHtml = this.view.getHtmlTemplate();
    var vendor$ = $(vendorHtml).clone().attr('id', 'vendorSearch_'+vendor.id);
    this.setImage(vendor, vendor$);
    this.setNameDesc(vendor, vendor$);
    return vendor$;
};


it.vendor.unSelectAllVendor = function () {
    $('.vendorSearch').addClass('unselected').removeClass('selected').find('.selected-tick').hide();
    if (it.offer && it.offer.addwizard) {
        it.offer.addwizard.getVendorStep().publishOnValidationChangeCb(false);
    }
};

it.vendor.unSelectVendor = function (vendor$) {
    vendor$.removeClass('selected').addClass('unselected').find('.selected-tick').hide();
    if (it.offer && it.offer.addwizard) {
        it.offer.addwizard.getVendorStep().publishOnValidationChangeCb(false);
    }

};

it.vendor.selectVendor = function (vendor$) {
    it.vendor.unSelectAllVendor();
    vendor$.removeClass('unselected').addClass('selected');
    if(vendor$.find('.selected-tick').length === 0) {
        vendor$.append('<a href="#" class="selected-tick">✔</a>');
    } else {
        vendor$.find('.selected-tick').show();
    }
    if (it.offer && it.offer.addwizard) {
        it.offer.addwizard.getVendorStep().publishOnValidationChangeCb(true);
    }
};

it.vendor.addHandlers = function() {
    $('.vendorSearch').popover({trigger: 'hover'}).click(function(){
        var $this = $(this);
        if ($this.hasClass('selected')) {
            it.vendor.unSelectVendor($this);
        } else {
            it.vendor.selectVendor($this);
        }
    });
};

it.vendor.getAddNewVendor$ = function (query) {
    var vendor = {
        id: 0,
        name: query
    }, 
    vendor$ = this.get$(vendor);

    //TODO: Add something to indicate
    //that this new vendor name would be added..

    return vendor$;
};

it.vendor.getAddNewVendorAlert$ = function (query) {
    var addNewVendorAlertHtml = '<div class="addNewVendorAlert row-fluid"><div class="alert alert-info"><strong>A New Vendor: </strong> <span class="vendorNameAlertVal"></span> would be added for you</div></div>',
        addNewVendorAlert$ = $(addNewVendorAlertHtml);
    addNewVendorAlert$.find('.vendorNameAlertVal').html(query);
    return addNewVendorAlert$;
};

it.vendor.plotAll = function (vendors, query) {
    var vendorsHtml = this.view.getVendorsHtml();
    var vendors$ = [],
        currentThreeVendorMax$ = $(vendorsHtml),
        vendor$ = null;
    this.view.getContainer$().html(currentThreeVendorMax$);
    for (var i=0;i < vendors.length;i++) {
        vendor$ = this.get$(vendors[i]);
        vendors$.push(vendor$);
    }
    if (vendors.length === 0) {
        vendor$ = this.getAddNewVendor$(query);
        vendors$.push(vendor$);
    }
    for (i=0;i<vendors$.length; i++) {
        var currentVendor$ = vendors$[i];
        if (currentThreeVendorMax$.find('li').length=== 3) {
            currentThreeVendorMax$ = $(vendorsHtml);
            this.view.getContainer$().append(currentThreeVendorMax$);
        }
        currentThreeVendorMax$.append(currentVendor$);
    }
    if (vendors.length === 0) {
        var addNewVendorAlert$ = this.getAddNewVendorAlert$(query);
        this.view.getContainer$().append(addNewVendorAlert$);
    }
    this.addHandlers();
};

it.vendor.getCarousel$ = function (vendor) {
    var vendorHtml = this.view.getEachVendorInCarouselHtml();
    var vendor$ = $(vendorHtml).clone().attr('id', 'vendorSearch_'+vendor.id);
    this.setImage(vendor, vendor$);
    this.setNameDesc(vendor, vendor$);
    return vendor$;
};


it.vendor.plotCarouselItems = function (vendors) {
    var vendors$ = [],
        fourVendorsIncarouselItems$ = [],
        vendor$ = null;


    for (var i=0;i < vendors.length;i++) {
        vendor$ = this.getCarousel$(vendors[i]);
        vendors$.push(vendor$);
    }

    var currentFourVendorsIncarouselItem$ = null;
    var fourVendorsIncarouselItemHtml= this.view.getvendorsInCarouselItemHtml();

    for (i=0;i<vendors$.length; i++) {

        if (i%4=== 0) {
            currentFourVendorsIncarouselItem$ = $(fourVendorsIncarouselItemHtml);
            fourVendorsIncarouselItems$.push(currentFourVendorsIncarouselItem$);

        }
        var currentVendor$ = vendors$[i];
        currentFourVendorsIncarouselItem$.find('.thumbnails').append(currentVendor$);

    }

    for (i=0;i<fourVendorsIncarouselItems$.length; i++) {
        if (i === 0) {
            $('#vendorItems').html(fourVendorsIncarouselItems$[i].addClass('active'));
        } else {
            $('#vendorItems').append(fourVendorsIncarouselItems$[i]);
        }
    }
    this.addCarouselHandlers();
};

it.vendor.addCarouselHandlers = function () {
    $('#vendorCarousel').carousel({
        interval:3000
    });

    var _onVendorInCarouselClicked = function (e) {
        var vendorName = $(this).attr('title');
        $('<form action="offers.do" method="get"><input type="hidden" name="q" value="'+vendorName+'"></input></form>').appendTo('body').submit();
    };

    $('.vendorCarouselItem').click(_onVendorInCarouselClicked);


};

it.vendor.plotCarousel = function() {
    $.getJSON( "searchVendor.do", {searchKey: '', maxResultCountKey: 12}, function(data){
        it.vendor.plotCarouselItems(data.result);
    });
};

it.vendor.plotAllAddable = function(query) {
    $.getJSON( "searchVendor.do", {searchKey: query}, function(data){
        it.vendor.plotAll (data.result, query);
    });

};

it.vendor.init = function(e) {
    this.plotAllAddable('');
};



//Unused
it.vendor.JqUiAutoCompleteInit = function() {
    var cache = {}, lastXhr;
    $('#vendor').autocomplete({
        minLength: 2,
        source: function( request, response ) {
            var term = request.term;
            if ( term in cache ) {
                response( cache[term] );
                return;
            }
            lastXhr = $.getJSON( "searchVendor.do", {searchKey: term}, function( data, status, xhr ) {
                var names = [];
                for (var i=0;i<data.result.length;i++) {
                    names.push(data.result[i].name);
                }
                cache[term] = names;
                if ( xhr === lastXhr ) {
                    response(names);
                }
            });
        }
    });
};
