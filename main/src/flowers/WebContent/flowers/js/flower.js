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
 
