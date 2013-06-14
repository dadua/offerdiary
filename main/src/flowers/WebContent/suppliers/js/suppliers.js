var it = it || {};
it.supplier = it.supplier || {};

it.supplier.plot = function (operation, data) {
    var config = this.config;
    it.entityplotter.newInstance($.extend(config, {data: data||{}, operation: operation}));
};

it.supplier.plotView = function (data) {
    this.plot('VIEW', data);
};


it.supplier.plotEdit = function (data) {
    this.plot('EDIT', data);
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
        sRequired: false
    },
    {
        name: 'alternativePhoneNo',
        displayKey: 'Phone Number 2',
        type: 'string', 
        sRequired: false
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
        sRequired: false
    }
    ]

};
 

it.supplier.list = it.supplier.list || {};

it.supplier.list.newInstance = function (containerId$, searchQueryElem$) {
    
    var fetchAll = function() {
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
    }, 

    _isOneInited = false,

    addOneHandlers = function () {
        searchQuery$.keyup(fetchAll);
    },

    container$ = $(containerId$),

    searchQuery$ = $(searchQueryElem$),

    view = this.newViewInstance(containerId$);

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

    var _postDeleteAll = function () {
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
    },

    _removeConfirm$ = $(removeConfirm$),

    _suppliersList = suppliersList;

    return {
        showConfirmPopup: _removeAfterConfirm,
        allSelected: _postDeleteAll,
        addHandlers: _addHandlers
    };
}; 

it.supplier.list.newViewInstance = function (containerId$) {
    var _eachRowHtml = '<tr><td><input type="checkbox" class="rowSelect"></input></td><td ><a class="name" href="#"></a></td><td class="bankAccountDetails"></td><td class="billingName"></td><td class="phoneNo"</td><td class="alternativePhoneNo"><td class="address"></td></tr>',
        
        _tableWithHeadingHtml = '<table class="table table-striped table-condensed entityTable table-bordered"> <thead> <tr><th><input title="Select/Un-Select All" type="checkbox" class="selectall"></input></th> <th> Name </th> <th>Bank Details</th> <th>Billing Name</th> <th>Phone No.</th> <th>Phone no. 2</th> <th>Address</th> </tr> </thead> <tbody></tbody></table>',

        _get$ = function (suppliers) {
            var _$ = $(_tableWithHeadingHtml),
                _tableBody$ = _$.find('tbody'),
                currentSupplier = {};

            for (var i=0; i< suppliers.length; i++) {
                currentSupplier = suppliers[i];
                var _tr$ = $(_eachRowHtml);
                _tr$.find('.name').html(currentSupplier.name).attr('href', 'supplier.do?id='+ currentSupplier.id);
                _tr$.find('.bankAccountDetails').html(currentSupplier.bankAccountDetails);
                _tr$.find('.billingName').html(currentSupplier.billingName);
                _tr$.find('.phoneNo').html(currentSupplier.phoneNo);
                _tr$.find('.alternativePhoneNo').html(currentSupplier.alternativePhoneNo);
                _tr$.find('.address').html(currentSupplier.address);
                _tr$.data('entityId', currentSupplier.id);
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

        _getSelectedItems = function () {
            var selectedIds = [];
            container$.find('.rowSelect').each(function(i) {
                if ($(this).is(':checked')) {
                    selectedIds.push($(this).closest('tr').data('entityId'));
                }
            });
            return selectedIds;
        },

        container$ = $(containerId$);

    return {
        get$: _get$,
        addHandlers: _addHandlers,
        getSelectedItems: _getSelectedItems
    };
};

it.supplier.list.initSuppliersUI = function (suppliers) {

    var supplierList = this.newInstance('#supplierContainerFluid', '#searchQuery');
    supplierList.plotAll(suppliers);

    var removeActionHandler = this.actions.newRemoverInstance(supplierList, $('.actionsPanel').find('.deleteSelectedAction'));
    removeActionHandler.addHandlers();
};
