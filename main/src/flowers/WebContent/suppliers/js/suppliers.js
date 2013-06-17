var it = it || {};
it.supplier = it.supplier || {};

it.supplier.plot = function (operation, data, refreshFromServer) {
    var config = this.config;

    if (typeof refreshFromServer !== 'boolean') {
        refreshFromServer = false;
    }
    it.entityplotter.newInstance($.extend(config, {data: data||{}, operation: operation, refreshFromServer: refreshFromServer}));
};

it.supplier.plotView = function (data, refreshFromServer) {
    this.plot('VIEW', data, refreshFromServer);
};


it.supplier.plotEdit = function (data, refreshFromServer) {
    this.plot('EDIT', data, refreshFromServer);
};

it.supplier.plotAddNew = function () {
    this.plot('ADDNEW', {});
};

it.supplier.config = {
    parentElemSelector: '#eachSupplierContainerFluid', 
    getUrl: 'getSupplierDetails.do',
    saveUrl: 'addSupplier.do', 
    title: 'Supplier',
    plotConfigs: [
        {
        name: 'name',
        displayKey: 'Name',
        type: 'string', 
        helpMsg: 'Supplier Name cant be empty',
        isRequired: true
    },

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
        type: 'string', 
        isRequired: false
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
        name: 'address',
        displayKey: 'Address',
        type: 'string', 
        isRequired: false
    }
    ]

};
 
it.supplier.getFlowers = function (supplierId, onFlowersFetchCb) {

    $.post('getFlowersForSupplier.do', {supplier_id: supplierId + ''}, function(respJSON) {
    	var resp = $.parseJSON(respJSON);
        if (typeof onFlowersFetchCb === 'function') {
            onFlowersFetchCb(resp.result);
        }
    });

};

it.supplier.list = it.supplier.list || {};

it.supplier.list.newInstance = function (containerId$, searchQueryElem$, viewConfig) {
    
    var container$ = $(containerId$),

    searchQuery$ = $(searchQueryElem$),

    _isOneInited = false,

    fetchAll = function() {
        var q = searchQuery$.val();

        $.post('getSuppliers.do', {q:q}, function (respJSON) {
            var resp = $.parseJSON(respJSON);
            plotAll(resp.result);
        });
    },

    plotAll = function (suppliers) {
        var _$ = view.get$(suppliers);
        container$.html(_$);
        view.addHandlers();
        if (!_isOneInited) {
            addOneHandlers();
            _isOneInited = true;
        }
        container$.trigger('entityPlotted', [suppliers]);
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


it.supplier.list.actions = it.supplier.list.actions || {};

it.supplier.list.actions.newRemoverInstance = function (suppliersList, removeConfirm$) {

    var _removeConfirm$ = $(removeConfirm$),

    _suppliersList = suppliersList,
    
    _postDeleteAll = function () {
        var idsToDelete = _suppliersList.view.getSelectedItems();
        $('#deleteConfirmModal').modal('hide');
        $.post('deleteSuppliers.do', {supplier_ids: JSON.stringify(idsToDelete)}, function(respJSON) {
            var resp = $.parseJSON(respJSON);
            if (resp.success) {
                _suppliersList.refresh();
            }
        });
    },

    _removeAfterConfirm = function () {
        var idsToDelete = _suppliersList.view.getSelectedItems();
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

it.supplier.list.defaultViewConfig = [
    {
        key: 'name',
        displayKey: 'Name',
        cellCustomHtml: '<a href="#" class="name"></a>',
        customCellPlotCb: function (data, row$) {
            $(row$).find('.name').html(data.name).attr('href', 'supplier.do?id='+data.id);
        }
    },
    {
        key: 'uniqueId',
        displayKey: 'Supplier Id',
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
        
it.supplier.list.newViewInstance = function (containerId$, viewConfig) {

   var _eachRowHtml = '<tr class="eachRow"><td><input type="checkbox" class="rowSelect"></input></td><td><a class="name" href="#"></a></td><td class="bankAccountDetails"></td><td class="billingName"></td><td class="phoneNo"></td><td class="alternativePhoneNo"><td class="address"></td></tr>',
        
        _eachRowEmptyHtml = '<tr class="eachRow"><td><input type="checkbox" class="rowSelect"></input></td></tr>',
        
        _tableWithHeadingHtml = '<table class="table table-striped table-condensed entityTable table-bordered"> <thead> <tr><th><input title="Select/Un-Select All" type="checkbox" class="selectall"></input></th> <th> Name </th> <th>Bank Details</th> <th>Billing Name</th> <th>Phone No.</th> <th>Phone no. 2</th> <th>Address</th> </tr> </thead> <tbody></tbody></table>',

        _tableWithHeadingEmptyHtml = '<table class="table table-striped table-condensed entityTable table-bordered"> <thead> <tr class="headingRow"><th><input title="Select/Un-Select All" type="checkbox" class="selectall"></th></tr> </thead> <tbody></tbody></table>',

        _viewConfig = viewConfig,
        
        container$ = $(containerId$);

        _getEachRow$ = function (supplier) {
            var _tr$ = null;
            if ($.isArray(_viewConfig)) {
                _tr$ = $(_eachRowEmptyHtml);
                $.each(_viewConfig, function(i, config) {
                    if (typeof config.cellCustomHtml === 'string') {
                        var _cellContents$ = $('<td>'+config.cellCustomHtml +'</td>');
                        _tr$.append(_cellContents$);
                        if (typeof config.customCellPlotCb === 'function') {
                            config.customCellPlotCb(supplier, _tr$);
                        }
                        _tr$.append(_cellContents$);

                    } else {
                        _tr$.append('<td class="'+config.key+'">'+supplier[config.key]||'' +'</td>');
                    }
                });
            } else {
                _tr$ = $(_eachRowHtml);
                _tr$.find('.name').html(supplier.name).attr('href', 'supplier.do?id='+ supplier.id);
                _tr$.find('.bankAccountDetails').html(supplier.bankAccountDetails);
                _tr$.find('.billingName').html(supplier.billingName);
                _tr$.find('.phoneNo').html(supplier.phoneNo);
                _tr$.find('.alternativePhoneNo').html(supplier.alternativePhoneNo);
                _tr$.find('.address').html(supplier.address);
            }
            _tr$.data('entityId', supplier.id);
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
    
        _get$ = function (suppliers) {
            var _$ = $(_getTableWithHeading$()),
                _tableBody$ = _$.find('tbody'),
                currentSupplier = {};

            for (var i=0; i< suppliers.length; i++) {
                currentSupplier = suppliers[i];
                var _tr$ = _getEachRow$(currentSupplier);
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

it.supplier.list.initSuppliersUI = function (suppliers) {

    var supplierList = this.newInstance('#supplierContainerFluid', '#searchQuery');
    supplierList.plotAll(suppliers);

    var removeActionHandler = this.actions.newRemoverInstance(supplierList, $('.actionsPanel').find('.deleteSelectedAction'));
    removeActionHandler.addHandlers();
};


it.supplier.list.initMinimalSuppliersUI = function(supplierContainer$, searchElem$) {
    var supplierList = this.newInstance(supplierContainer$, searchElem$, this.defaultViewConfig);
    supplierList.refresh();
    return supplierList;
};


it.supplier.list.plotMinimalSuppliersUI = function(suppliers, supplierContainer$, searchElem$) {
    var supplierList = this.newInstance(supplierContainer$, searchElem$, this.defaultViewConfig);
    supplierList.plotAll(suppliers);
    return supplierList;
};
