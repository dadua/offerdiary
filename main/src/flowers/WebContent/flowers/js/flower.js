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
    var _eachRowHtml = '<tr><td ><a class="name"></a></td><td class="color"></td></tr>',
        
        _tableWithHeadingHtml = '<table class="table table-striped table-condensed table-bordered"> <thead> <tr> <th> Name </th> <th>Color</th> </tr> </thead> <tbody></tbody></table>',

        _get$ = function (flowers) {
            var _$ = $(_tableWithHeadingHtml),
                _tableBody$ = _$.find('tbody'),
                currentFlower = {};

            for (var i=0; i< flowers.length; i++) {
                currentFlower = flowers[i];
                var _tr$ = $(_eachRowHtml);
                _tr$.find('.name').html(currentFlower.name);
                _tr$.find('.name').attr('href', 'flower.do?id='+currentFlower.id);
                _tr$.find('.color').html(currentFlower.color);
                _tr$.data('entityId', currentFlower.id);
                _tableBody$.append(_tr$);
            }
            return _$;
        };

    return {
        get$: _get$
    };

}();

it.flower.plotAll = function (flowers) {
    var _$ = this.view.get$(flowers);
    $('#flowerContainerFluid').html(_$);
};


it.flower.addOneHandlers = function () {
    $('#searchQuery').keyup(it.flower.fetchAll);
};

it.flower.initFlowersUI = function (flowers) {
    this.plotAll(flowers);
    this.addOneHandlers();
};
