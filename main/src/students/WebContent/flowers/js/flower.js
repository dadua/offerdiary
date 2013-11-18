var it = it || {};
it.flower = it.flower || {};

it.flower.plot = function (operation, data) {
    var config = this.config;
    return it.entityplotter.newInstance($.extend(config, {data: data||{}, operation: operation}));
};

it.flower.plotView = function (data) {
    return this.plot('VIEW', data);
};


it.flower.plotEdit = function (data) {
    return this.plot('EDIT', data);
};

it.flower.plotAddNew = function () {
    return this.plot('ADDNEW', {});
};

it.flower.config = {
    parentElemSelector: '#eachFlowerContainerFluid', 
    getUrl: 'getFlowerDetails.do',
    saveUrl: 'addFlower.do', 
    title: 'Flower',
    plotConfigs: [
        {
        name: 'name',
        displayKey: 'Name',
        type: 'string', 
        helpMsg: 'Flower Name cant be empty',
        isRequired: true
    },
    {
        name: 'color',
        displayKey: 'Colour',
        isRequired: false,
        type: 'string'

    }
    /*
    ,
    
    {
    	name: 'quantityInStock',
    	displayKey: 'Stock Available'
    }
    */
    ]
};

it.flower.fetchCustomers = function (flower) {
	$.post('getCustomersForFlower.do', {flower_id: flower.id}, function (respJSON) {
		var resp = $.parseJSON(respJSON),
			customers = resp.result;
		it.flower.plotCustomers (flower, customers);
	});
};

it.flower.plotCustomers = function (flower, customers) {
	var customersList = it.customer.list.plotMinimalCustomersUI(customers, '#customersContainerFluid');
	customersList.plotAll(customers);
	customersList.view.hideAllSelectCells();
};

it.flower.fetchSuppliers = function (flower) {
	$.post('getSuppliersForFlower.do', {flower_id: flower.id}, function (respJSON) {
		var resp = $.parseJSON(respJSON),
			suppliers = resp.result;
		it.flower.plotSuppliers (flower, suppliers);
	});
};

it.flower.plotSuppliers = function (flower, suppliers) {
	var suppliersList = it.supplier.list.plotMinimalSuppliersUI(suppliers, '#suppliersContainerFluid');
	suppliersList.plotAll(suppliers);
	suppliersList.view.hideAllSelectCells();
};

it.flower.list = it.flower.list || {};

it.flower.list.newInstance = function (containerId$, searchQueryElem$) {
    
    var container$ = $(containerId$),

    searchQuery$ = $(searchQueryElem$),
    
    fetchAll = function() {
        var q = searchQuery$.val();

        $.post('getFlowers.do', {q:q}, function (respJSON) {
            var resp = $.parseJSON(respJSON);
            plotAll(resp.result);
        });
    },

    _isOneInited = false,
    
    plotAll = function (flowers) {
        var _$ = view.get$(flowers);
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


    view = this.newViewInstance(containerId$);

    return {
        plotAll: plotAll,
        addOneHandlers: addOneHandlers,
        refresh: fetchAll,
        view: view,
        getSelectedItems: view.getSelectedItems
    };

};


it.flower.list.actions = it.flower.list.actions || {};

it.flower.list.actions.newRemoverInstance = function (flowersList, removeConfirm$) {

    var _removeConfirm$ = $(removeConfirm$),

    _flowersList = flowersList,
    
    _postDeleteAll = function () {
        var idsToDelete = _flowersList.view.getSelectedItems();
        $('#deleteConfirmModal').modal('hide');
        $.post('deleteFlowers.do', {flower_ids: JSON.stringify(idsToDelete)}, function(respJSON) {
            var resp = $.parseJSON(respJSON);
            if (resp.success) {
                _flowersList.refresh();
            }
        });
    },

    _removeAfterConfirm = function () {
        var idsToDelete = _flowersList.view.getSelectedItems();
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

it.flower.list.actions.newAddToCustomerInstance = function (flowersList, addFlowerToCustomerInvoker$) {

    var addCustomersList,
    
    _addFlowerToCustomer$ = $(addFlowerToCustomerInvoker$),

    _flowersList = flowersList,
    
    _fetchAllCustomers = function () {
        addCustomersList = it.customer.list.initMinimalCustomersUI('#customerAddContainer', '#searchCustomerQuery');

        var addFlowerToCustomerModal$ = $('#addFlowerToCustomerModal');
        addFlowerToCustomerModal$.modal('show');
    },

    _associateFlowersToCustomer = function (customers) {
        var flowerIdsToAssociateWith = _flowersList.view.getSelectedItems();
        var customerIdsToAssociateWith = addCustomersList.view.getSelectedItems();
        
        $('#addFlowerToCustomerModal').modal('hide');
        $.post('addFlowersToCustomers.do', {
            flower_ids: JSON.stringify(flowerIdsToAssociateWith),
            customer_ids: JSON.stringify(customerIdsToAssociateWith)
        }, function(respJSON) {
            var resp = $.parseJSON(respJSON);
        });
    },

    _showCustomersToAddModal = function() {
        var flowerIdsToAddToCustomer = _flowersList.view.getSelectedItems();

        if (flowerIdsToAddToCustomer.length === 0) {
            it.actionInfo.showInfoActionMsg('No Flower Items selected to be added to Customer');
        } else {
            _fetchAllCustomers();
        }

    },

    _addHandlers = function() {
        _addFlowerToCustomer$.click(_showCustomersToAddModal);
        $('#addFlowerToCustomerModal').find('.deleteSelectedItemsBtn').click(_associateFlowersToCustomer);
    };

    return {
        showCustomersToAdd: _showCustomersToAddModal,
        addHandlers: _addHandlers
    };
}; 

it.flower.list.newViewInstance = function (containerId$) {
    var _eachRowHtml = '<tr><td><input type="checkbox" class="rowSelect"></input></td><td ><a class="name"></a></td><td class="color"></td><td class="quantityInStock"></td></tr>',
    
        _tableWithHeadingHtml = '<table class="table entityTable table-striped table-condensed table-bordered"> <thead> <tr><th><input title="Select/Un-Select All" type="checkbox" class="selectall"></input></th><th> Name </th> <th>Color</th><th>Stock in quantity</th></tr> </thead> <tbody></tbody></table>',

        container$ = $(containerId$),
        
        _get$ = function (flowers) {
            var _$ = $(_tableWithHeadingHtml),
                _tableBody$ = _$.find('tbody'),
                currentFlower = {};

            for (var i=0; i< flowers.length; i++) {
                currentFlower = flowers[i];
                var _tr$ = $(_eachRowHtml);
                _tr$.find('.name').html(currentFlower.name).attr('href', 'flower.do?id='+currentFlower.id);
                _tr$.find('.color').html(currentFlower.color);
                _tr$.find('.quantityInStock').html(currentFlower.quantityInStock);
                _tr$.data('entityId', currentFlower.id);
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

        _entitySelectedHandler = function (e) {
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

            container$.find('.entityTable').find('.rowSelect').click(_entitySelectedHandler);
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
        getSelectedItems: _getSelectedItems
    };
};

it.flower.list.actions.newAddToSupplierInstance = function (flowersList, addFlowerToSupplierInvoker$) {

    var addSuppliersList,
    
        _addFlowerToSupplier$ = $(addFlowerToSupplierInvoker$),

        _flowersList = flowersList,
        
        _fetchAllSuppliers = function () {
            addSuppliersList = it.supplier.list.initMinimalSuppliersUI('#supplierAddContainer', '#searchSupplierQuery');

            var addFlowerToSupplierModal$ = $('#addFlowerToSupplierModal');
            addFlowerToSupplierModal$.modal('show');
        },

        _associateFlowersToSupplier = function (suppliers) {
            var flowerIdsToAssociateWith = _flowersList.view.getSelectedItems();
            var supplierIdsToAssociateWith = addSuppliersList.view.getSelectedItems();
            
            $('#addFlowerToSupplierModal').modal('hide');
            $.post('addFlowersToSuppliers.do', {
                flower_ids: JSON.stringify(flowerIdsToAssociateWith),
                supplier_ids: JSON.stringify(supplierIdsToAssociateWith)
            }, function(respJSON) {
                var resp = $.parseJSON(respJSON);
            });
        },

        _showSuppliersToAddModal = function() {
            var flowerIdsToAddToSupplier = _flowersList.view.getSelectedItems();

            if (flowerIdsToAddToSupplier.length === 0) {
                it.actionInfo.showInfoActionMsg('No Flower Items selected to be added to Supplier');
            } else {
                _fetchAllSuppliers();
            }

        },

        _addHandlers = function() {
            _addFlowerToSupplier$.click(_showSuppliersToAddModal);
            $('#addFlowerToSupplierModal').find('.deleteSelectedItemsBtn').click(_associateFlowersToSupplier);
        };

    return {
        showSuppliersToAdd: _showSuppliersToAddModal,
        addHandlers: _addHandlers
    };
}; 

it.flower.list.initFlowersUI = function (flowers) {

    var flowerList = this.newInstance('#flowerContainerFluid', '#searchQuery');
    flowerList.plotAll(flowers);

    var removeActionHandler = this.actions.newRemoverInstance(flowerList, $('.actionsPanel').find('.deleteSelectedAction'));
    removeActionHandler.addHandlers();

    var addToCustomerActionHandler = this.actions.newAddToCustomerInstance(flowerList, $('.actionsPanel').find('.addFlowerToCustomerAction'));
    addToCustomerActionHandler.addHandlers();
    
    var addToSupplierActionHandler = this.actions.newAddToSupplierInstance(flowerList, $('.actionsPanel').find('.addFlowerToSupplierAction'));
    addToSupplierActionHandler.addHandlers();
};
