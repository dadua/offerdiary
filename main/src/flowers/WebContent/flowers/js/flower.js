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
 

it.flower.fetchAll = function() {
    var q = $('#searchQuery').val();

    $.post('getFlowers.do', {q:q}, function (respJSON) {
        var resp = $.parseJSON(respJSON);
        it.flower.plotAll(resp.result);
    });
};

it.flower.view = function () {
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
            var container$ = $('#flowerContainerFluid'),
                table$ = container$.find('.entityTable');
            if ($(this).is(':checked')) {
                table$.find('.rowSelect').prop('checked', true);
                //selectAll$.tooltip({title: 'UnSelect All'});
            } else {
                table$.find('.rowSelect').prop('checked', false);
                //selectAll$.tooltip({title: 'Select All'});
            }
        },
        
        _addHandlers = function () {
            var container$ = $('#flowerContainerFluid'),
                table$ = container$.find('.entityTable'),
                selectAll$ = table$.find('.selectall'),
                actionsPanel$ = $('.actionsPanel');

            selectAll$.tooltip().click(_selectAllHandler);
            actionsPanel$.find('.deleteSelectedAction').click(it.flower.remove.showConfirmPopup);
            $('#deleteConfirmModal').find('.deleteSelectedItemsBtn').click(it.flower.remove.allSelected);

        
        };

    return {
        get$: _get$,
        addHandlers: _addHandlers
    };

}();

it.flower.plotAll = function (flowers) {
    var _$ = this.view.get$(flowers);
    $('#flowerContainerFluid').html(_$);
    this.view.addHandlers();
};

it.flower.remove = function () {

    var _getSelectedItemsToRemove = function () {

        var idsToDelete = [];
        $('#flowerContainerFluid').find('.rowSelect').each(function(i) {
            if ($(this).is(':checked')) {
                idsToDelete.push($(this).closest('tr').data('entityId'));
            }
        });
        return idsToDelete;
    },

    _postDeleteAll = function () {
        var idsToDelete = _getSelectedItemsToRemove();
        $('#deleteConfirmModal').modal('hide');
        $.post('deleteFlowers.do', {flower_ids: JSON.stringify(idsToDelete)}, function(respJSON) {
            var resp = $.parseJSON(respJSON);
            if (resp.success) {
                it.flower.fetchAll();
            }
        });
    },

    _removeAfterConfirm = function () {
        var idsToDelete = _getSelectedItemsToRemove();
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

    };

    return {
        showConfirmPopup: _removeAfterConfirm,
        allSelected: _postDeleteAll
    };
}();


it.flower.addOneHandlers = function () {
    $('#searchQuery').keyup(it.flower.fetchAll);
};

it.flower.initFlowersUI = function (flowers) {
    this.plotAll(flowers);
    this.addOneHandlers();
};
