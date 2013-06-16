var it = it || {};
it.flowertx = it.flowertx || {};

it.flowertx.plot = function (operation, data) {
    var config = this.config;
    it.entityplotter.newInstance($.extend(config, {data: data||{}, operation: operation}));
};

it.flowertx.plotView = function (data) {
    this.plot('VIEW', data);
};

it.flowertx.plotEdit = function (data) {
    this.plot('EDIT', data);
};

it.flowertx.plotAddNew = function () {
    this.plot('ADDNEW', {});
};

it.flowertx.config = {
    parentElemSelector: '#eachCustomerContainerFluid', 
    getUrl: 'getCustomerDetails.do',
    saveUrl: 'addCustomer.do', 
    title: 'Customer',
    plotConfigs: [
        {
        name: 'name',
        displayKey: 'Name',
        type: 'string', 
        helpMsg: 'Customer Name cant be empty',
        isRequired: true
    },
    /*
    {
        name: 'nickName',
        displayKey: 'Nick Name',
        isRequired: false,
        type: 'string'

    },
    */
    {
        name: 'bankAccountDetails',
        displayKey: 'Bank Account Details',
        type: 'string', 
        isRequired: false
    },
    {
        name: 'billingName',
        displayKey: 'Billing Name',
        type: 'string', 
        isRequired: false
    },
    {
        name: 'phoneNo',
        displayKey: 'Phone Number',
        type: 'string', 
        isRequired: false
    },
    {
        name: 'alternativePhoneNo',
        displayKey: 'Phone Number 2',
        type: 'string', 
        isRequired: false
    },
    {
        name: 'place',
        displayKey: 'Place',
        helpMsg: 'This is a shorter version of address',
        type: 'string',
        isRequired: false
    },
    {
        name: 'address',
        displayKey: 'Address',
        type: 'string', 
        isRequired: false
    }
    ]
};

it.flowertx.list = it.flowertx.list || {};

it.flowertx.list.newInstance = function (containerId$, searchQueryElem$, viewConfig) {
    
    var _isOneInited = false,
    
    container$ = $(containerId$),

    searchQuery$ = $(searchQueryElem$),
    
    fetchAll = function() {
        var q = searchQuery$.val();

        $.post('getCustomers.do', {q:q}, function (respJSON) {
            var resp = $.parseJSON(respJSON);
            plotAll(resp.result);
        });
    },

    plotAll = function (flowertxs) {
        var _$ = view.get$(flowertxs);
        container$.html(_$);
        view.addHandlers();
        if (!_isOneInited) {
            addOneHandlers();
            _isOneInited = true;
        }
    }, 


    addOneHandlers = function () {
        searchQuery$.keyup(fetchAll);
    },

    view = this.newViewInstance(containerId$, viewConfig);

    return {
        plotAll: plotAll,
        addOneHandlers: addOneHandlers,
        refresh: fetchAll,
        view: view,
        getSelectedItems: view.getSelectedItems
    };

};

it.flowertx.list.actions = it.flowertx.list.actions || {};

it.flowertx.list.actions.newRemoverInstance = function (flowertxsList, removeConfirm$) {

    var _removeConfirm$ = $(removeConfirm$),

    _flowertxsList = flowertxsList,
    
    _postDeleteAll = function () {
        var idsToDelete = _flowertxsList.view.getSelectedItems();
        $('#deleteConfirmModal').modal('hide');
        $.post('deleteCustomers.do', {flowertx_ids: JSON.stringify(idsToDelete)}, function(respJSON) {
            var resp = $.parseJSON(respJSON);
            if (resp.success) {
                _flowertxsList.refresh();
            }
        });
    },

    _removeAfterConfirm = function () {
        var idsToDelete = _flowertxsList.view.getSelectedItems();
        _showDeleteConfirmPopup(idsToDelete);
    },

    _showDeleteConfirmPopup = function(idsToDelete) {

        var confirmModal$ = $('#deleteConfirmModal');
        if (idsToDelete.length === 0) {
            it.actionInfo.showInfoActionMsg('No Items to delete');
        } else {
            confirmModal$.find('.modal-body').html(idsToDelete.length + ' items would be deleted');
            confirmModal$.modal('show');
        }

    },

    _addHandlers = function() {
        _removeConfirm$.click(_removeAfterConfirm);
        $('#deleteConfirmModal').find('.deleteSelectedItemsBtn').click(_postDeleteAll);
    };


    return {
        showConfirmPopup: _removeAfterConfirm,
        allSelected: _postDeleteAll,
        addHandlers: _addHandlers
    };
}; 

it.flowertx.list.defaultViewConfig = [
    {
        key: 'name',
        displayKey: 'Name',
        cellCustomHtml: '<a href="#" class="name"></a>',
        customCellPlotCb: function (data, row$) {
            $(row$).find('.name').html(data.name).attr('href', 'flowertx.do?id='+data.id);
        }
    },

    {
        key: 'uniqueId',
        displayKey: 'Customer Id',
        isViewOnly: true
    },
    {
        key: 'billingName',
        displayKey: 'Billing Name'
    },
    {
        key: 'phoneNo',
        displayKey:'Phone No.'
    },
    {
        key: 'place',
        displayKey: 'Place'
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

        _addHandlers = function () {
            selectAll$ = container$.find('.entityTable').find('.selectall');
            selectAll$.tooltip().click(_selectAllHandler);
        },
        
        _hideAllSelectCells = function () {
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
        hideAllSelectCells: _hideAllSelectCells
    };
};

it.flowertx.list.initMinimalCustomersUI = function(flowertxContainer$, searchElem$) {
    var flowertxList = this.newInstance(flowertxContainer$, searchElem$, this.defaultViewConfig);
    flowertxList.refresh();
    return flowertxList;
};


it.flowertx.list.plotMinimalCustomersUI = function(flowertxs, flowertxContainer$, searchElem$) {
    var flowertxList = this.newInstance(flowertxContainer$, searchElem$, this.defaultViewConfig);
    flowertxList.plotAll(flowertxs);
    return flowertxList;
};

it.flowertx.list.initCustomersUI = function (flowertxs) {

    var flowertxList = this.newInstance('#flowertxContainerFluid', '#searchQuery');
    flowertxList.plotAll(flowertxs);

    var removeActionHandler = this.actions.newRemoverInstance(flowertxList, $('.actionsPanel').find('.deleteSelectedAction'));
    removeActionHandler.addHandlers();
};
