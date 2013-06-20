var it = it || {};
it.flowertx = it.flowertx || {};

it.flowertx.list = it.flowertx.list || {};

it.flowertx.list.newInstance = function (containerId$, searchQueryElem$, viewConfig) {
    
    var _isOneInited = false,
        
        container$ = $(containerId$),

        searchQuery$ = $(searchQueryElem$),

        view = this.newViewInstance(containerId$, viewConfig),

        plotAll = function (flowertxs) {
            var _$ = view.get$(flowertxs);
            container$.html(_$);
            view.addHandlers();
            if (!_isOneInited) {
                addOneHandlers();
                _isOneInited = true;
            }
            container$.trigger('entityPlotted', [flowertxs]);
        },
        
        fetchAll = function() {
            var q = searchQuery$.val();

            $.post('getFlowerTransactions.do', {q:q}, function (respJSON) {
                var resp = $.parseJSON(respJSON);
                plotAll(resp.result);
            });
        },
     
        addOneHandlers = function () {
            searchQuery$.keyup(fetchAll);
        };

    return {
        plotAll: plotAll,
        addOneHandlers: addOneHandlers,
        refresh: fetchAll,
        view: view,
        getSelectedItems: view.getSelectedItems
    };

};

it.flowertx.list.defaultViewConfig = [
    {
        key: 'uniqueId',
        displayKey: 'Transaction Id',
        cellCustomHtml: '<a href="#" class="uniqueId"></a>',
        customCellPlotCb: function (data, row$) {
            $(row$).find('.uniqueId').html("Details for Tx:"+data.uniqueId).attr('href', 'flowerTransaction.do?id='+data.id);
        }
    },
    {
    	key: 'contact',
    	displayKey: 'Contact',
    	cellCustomHtml: '<a href="#" class="contact"></a>',
    	customCellPlotCb: function (data, row$) {
    		var displayContents = data.contact.name,
    			contactDetailUrl = '.do?id=' + data.contact.id;
    		if (data.contact.type === 'CUSTOMER') {
    			displayContents = displayContents +  '(Customer)';
    			contactDetailUrl = 'customer' + contactDetailUrl;
    		} else {
    			displayContents =  displayContents + '(Supplier)';
    			contactDetailUrl = 'supplier' + contactDetailUrl;
    		}
    		$(row$).find('.contact').html(displayContents).attr('href', contactDetailUrl);
	    }
    },
    {
    	key: 'dateMillis',
    	displayKey: "Date",
    	cellCustomHtml: '<span class="date"></span>',
    	customCellPlotCb: function (data, row$) {
    		$(row$).find('.date').html($.datepicker.formatDate('dd/mm/yy', new Date(data.dateMillis)));
	    }
    },
    {
        key: 'totalCost',
        displayKey: 'Total Cost'
    }
];

it.flowertx.list.newViewInstance = function (containerId$, viewConfig) {
    var _eachRowHtml = '<tr class="eachRow"><td><input type="checkbox" class="rowSelect"></input></td><td><a class="name" href="#"></a></td><td class="bankAccountDetails"></td><td class="billingName"></td><td class="phoneNo"></td><td class="alternativePhoneNo"><td class="address"></td></tr>',
        
        _eachRowEmptyHtml = '<tr class="eachRow"><td><input type="checkbox" class="rowSelect"></input></td></tr>',
        
        _tableWithHeadingHtml = '<table class="table table-striped table-condensed entityTable table-bordered"> <thead> <tr><th><input title="Select/Un-Select All" type="checkbox" class="selectall"></input></th> <th> Name </th> <th>Bank Details</th> <th>Billing Name</th> <th>Phone No.</th> <th>Phone no. 2</th> <th>Address</th> </tr> </thead> <tbody></tbody></table>',

        _tableWithHeadingEmptyHtml = '<table class="table table-striped table-condensed entityTable table-bordered"> <thead> <tr class="headingRow"><th><input title="Select/Un-Select All" type="checkbox" class="selectall"></th></tr> </thead> <tbody></tbody></table>',
        
        container$ = $(containerId$),
        
        _viewConfig = viewConfig,

        _getEachRow$ = function (flowertx) {
            var _tr$ = null;
            if ($.isArray(_viewConfig)) {
                _tr$ = $(_eachRowEmptyHtml);
                $.each(_viewConfig, function(i, config) {
                    if (typeof config.cellCustomHtml === 'string') {
                        var _cellContents$ = $('<td>'+config.cellCustomHtml +'</td>');
                        _tr$.append(_cellContents$);
                        if (typeof config.customCellPlotCb === 'function') {
                            config.customCellPlotCb(flowertx, _tr$);
                        }
                        _tr$.append(_cellContents$);

                    } else {
                        _tr$.append('<td class="'+config.key+'">'+flowertx[config.key]||'' +'</td>');
                    }
                });
            } else {
                _tr$ = $(_eachRowHtml);
                _tr$.find('.name').html(flowertx.name).attr('href', 'flowertx.do?id='+ flowertx.id);
                _tr$.find('.bankAccountDetails').html(flowertx.bankAccountDetails);
                _tr$.find('.billingName').html(flowertx.billingName);
                _tr$.find('.phoneNo').html(flowertx.phoneNo);
                _tr$.find('.alternativePhoneNo').html(flowertx.alternativePhoneNo);
                _tr$.find('.address').html(flowertx.address);
            }
            _tr$.data('entityId', flowertx.id);
            return _tr$;
        },

        _getTableWithHeading$ = function () {
            var _$ = null;
            if ($.isArray(_viewConfig)) {
                _$ = $(_tableWithHeadingEmptyHtml);
                $.each(_viewConfig, function(i, config) {
                    _$.find('.headingRow').append('<th>'+config.displayKey +'</td>');
                });
            } else {
                _$ = $(_tableWithHeadingHtml);
            }
            return _$;
        },

        _get$ = function (flowertxs) {
            var _$ = _getTableWithHeading$(),
                _tableBody$ = _$.find('tbody'),
                currentCustomer = {};

            for (var i=0; i< flowertxs.length; i++) {
                currentCustomer = flowertxs[i];
                var _tr$ = _getEachRow$(currentCustomer);
                _tableBody$.append(_tr$);
            }
            return _$;
        },

        _selectAllHandler = function () {
            if ($(this).is(':checked')) {
                container$.find('.entityTable').find('.rowSelect').prop('checked', true);
            } else {
                container$.find('.rowSelect').prop('checked', false);
            }
        },

        
        _entityClickedHandler = function (e) {
            var this$ = $(this),
                entityId = this$.closest('tr').data('entityId');

            if (this$.is(':checked')) {
                container$.trigger('entityInListSelected', [entityId, this$]);
            } else {
                container$.trigger('entityInListUnSelected', [entityId, this$]);
            }
        },

        _addHandlers = function () {
            var selectAll$ = container$.find('.entityTable').find('.selectall');
            selectAll$.tooltip().click(_selectAllHandler);
            container$.find('.entityTable').find('.rowSelect').click(_entityClickedHandler);
        },

        _hideSelectUnselectAllOption = function () {
            container$.find('.selectall').addClass('hide');
        },
        
        _hideEntitySelectCells = function () {
            container$.find('.rowSelect').closest('td').addClass('hide');
            container$.find('.selectall').closest('th').addClass('hide');
        },

        _getSelectedItems = function () {
            var selectedIds = [];
            container$.find('.rowSelect').each(function(i) {
                if ($(this).is(':checked')) {
                    selectedIds.push($(this).closest('tr').data('entityId'));
                }
            });
            return selectedIds;
        };


    return {
        get$: _get$,
        addHandlers: _addHandlers,
        getSelectedItems: _getSelectedItems,
        hideAllSelectCells: _hideEntitySelectCells,
        hideSelectAllOption: _hideSelectUnselectAllOption
    };
};

it.flowertx.list.initFlowerTxUI = function(flowerTxsContainer$, searchElem$) {
    var flowerTxList = this.newInstance(flowerTxsContainer$, searchElem$, this.defaultViewConfig);
    flowerTxList.refresh();
    return flowerTxList;
};

it.flowertx.list.initFlowerTxUIForTxs = function(flowerTxs, flowerTxsContainer$, searchElem$) {
    var flowerTxList = this.newInstance(flowerTxsContainer$, searchElem$, this.defaultViewConfig);
    flowerTxList.plotAll(flowerTxs);
    return flowerTxList;
};
