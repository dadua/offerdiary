var it = it || {};
it.flower = it.flower || {};

it.flower.plot = function (operation, data) {
    var config = this.config;
    it.entityplotter.newInstance($.extend(config, {data: data||{}, operation: operation}));
};

it.flower.plotView = function (data) {
    this.plot('VIEW', data);
};


it.flower.plotEdit = function (data) {
    this.plot('EDIT', data);
};

it.flower.plotAddNew = function () {
    this.plot('ADDNEW', {});
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

    }]
};

it.flower.list = it.flower.list || {};

it.flower.list.newInstance = function (containerId$, searchQueryElem$) {
    
    var fetchAll = function() {
        var q = searchQuery$.val();

        $.post('getFlowers.do', {q:q}, function (respJSON) {
            var resp = $.parseJSON(respJSON);
            plotAll(resp.result);
        });
    },

    plotAll = function (flowers) {
        var _$ = view.get$(flowers);
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


it.flower.list.actions = it.flower.list.actions || {};

it.flower.list.actions.newRemoverInstance = function (flowersList, removeConfirm$) {

    var _postDeleteAll = function () {
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
    },

    _removeConfirm$ = $(removeConfirm$),

    _flowersList = flowersList;

    return {
        showConfirmPopup: _removeAfterConfirm,
        allSelected: _postDeleteAll,
        addHandlers: _addHandlers
    };
}; 

it.flower.list.actions.newAddToCustomerInstance = function (flowersList, addFlowerToCustomerInvoker$) {

    var _fetchAllCustomers = function () {
        addCustomersList = it.customer.list.initMinimalCustomersUI('#customerAddContainer', '#searchCustomerQuery');

        var addFlowerToCustomerModal$ = $('#addFlowerToCustomerModal');
        addFlowerToCustomerModal$.modal('show');
    },

    addCustomersList = null,

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
    },

    _addFlowerToCustomer$ = $(addFlowerToCustomerInvoker$),

    _flowersList = flowersList;

    return {
        showCustomersToAdd: _showCustomersToAddModal,
        addHandlers: _addHandlers
    };
}; 

it.flower.list.newViewInstance = function (containerId$) {
    var _eachRowHtml = '<tr><td><input type="checkbox" class="rowSelect"></input></td><td ><a class="name"></a></td><td class="color"></td></tr>',
        _tableWithHeadingHtml = '<table class="table entityTable table-striped table-condensed table-bordered"> <thead> <tr><th><input title="Select/Un-Select All" type="checkbox" class="selectall"></input></th><th> Name </th> <th>Color</th> </tr> </thead> <tbody></tbody></table>',

        _get$ = function (flowers) {
            var _$ = $(_tableWithHeadingHtml),
                _tableBody$ = _$.find('tbody'),
                currentFlower = {};

            for (var i=0; i< flowers.length; i++) {
                currentFlower = flowers[i];
                var _tr$ = $(_eachRowHtml);
                _tr$.find('.name').html(currentFlower.name).attr('href', 'flower.do?id='+currentFlower.id);
                _tr$.find('.color').html(currentFlower.color);
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

it.flower.list.initFlowersUI = function (flowers) {

    var flowerList = this.newInstance('#flowerContainerFluid', '#searchQuery');
    flowerList.plotAll(flowers);

    var removeActionHandler = this.actions.newRemoverInstance(flowerList, $('.actionsPanel').find('.deleteSelectedAction'));
    removeActionHandler.addHandlers();

    var addToCustomerActionHandler = this.actions.newAddToCustomerInstance(flowerList, $('.actionsPanel').find('.addFlowerToCustomerAction'));
    addToCustomerActionHandler.addHandlers();
};
