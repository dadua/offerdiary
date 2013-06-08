var it = it || {};
it.entityplotter = it.entityplotter || {};
it.entityplotter.view = it.entityplotter.view || {};

/**
 * example config
 * config = {
 *              parentElemSelector: '#containerFluid', //Under which element to plot this entity
 *              operation: 'VIEW' or 'EDIT' or 'ADDNEw',
 *              getUrl: 'getFlower.do', - default id would return a result wrapped object
 *              saveUrl: 'saveFlower.do', 
 *              data: {
 *                      propName1: 'propVal1',
 *                      propName2: 'propVal2'
 *                  },  - this would be utilized in the case of VIEW or EDIT operation
 *                      - in case data is present then no need to call getUrl
 *              plotConfigs: [  {
 *                                  name: 'propName1',
 *                                  displayKey: 'Prop Name 1',
 *                                  type: 'string or number or ..',
 *                                              - default type would be string
 *                                              - This would map to a input text
 *                                  helpMsg: 'User Name cant be empty',
 *                                  isRequired: 'false by default'
 *                                              - Save would be disabled incase any
 *                                              - propName having isRequired = true is empty
 *                              },
 *                              {
 *                                  name: 'propName2',
 *                                              ...
 *                                              ...
 *
 *                              }
 *                          ]
 *          };
 *
 */
it.entityplotter.newInstance = function (config) {

    var _attrConfigs = null,
    
    
    _newInstance = function (configs) {
        _attrConfigs = configs;
        //_attrNameToPlotConfigMap = _getAttrNameConfigMap (_attrConfigs);
    }, 


    /*
    _getAttrNameConfigMap = function (attrConfigs) {
        var attrNameToPlotConfigMap = {};

        $.each(attrConfigs.plotConfigs, function(index, plotConfig) {
            attrNameToPlotConfigMap[plotConfig.name] =
        });

    },
   */

    _view = null,
    
    _attrNameToPlotConfigMap = {};

    /*
    _defaultEditConfig = {},

    _defaultViewConfig = {},

    _defaultAddNewConfig = {},
    
    _configMap = {};

   */

    _newInstance(config);

    return {
    };
};




it.entityplotter.view.newInstance = function (config) {
    var _get$ = function (config) {
        var attrPlotConfigs = config.plotConfigs,
            _$ = $(_operationRowHtml + _tableBodyHtml),

            _defaultPlotConfig = {
                                    displayKey: '',
                                    helpMsg: '',
                                    isRequired: false
            };

        for (var i=0; i<attrPlotConfigs.length; i++) {
            var currentAttrPlotConfig = attrPlotConfigs[i];

            currentAttrPlotConfig = $.extend(_defaultPlotConfig, currentAttrPlotConfig);

            var _eachAttr$ = $(_eachNameValRowHtml),
                _attrName = currentAttrPlotConfig.name;
            _eachAttr$.find('.attribName').html(currentAttrPlotConfig.displayKey);
            _eachAttr$.find('.valueViewElement').html(config.data[_attrName]);
            _eachAttr$.find('.valueEntryInputTextBox').val(config.data[_attrName]);

            _eachAttr$.data('attrKey', _attrName);//This helps is fetching the data from the DOM

            var helpMsg = currentAttrPlotConfig.helpMsg;
            if (currentAttrPlotConfig.isRequired) {
                helpMsg = '*' + helpMsg;
            }

            _eachAttr$.find('.help-block').html(helpMsg);
            _$.find('.nameValTableBody').append(_eachAttr$);

        }

        return _$;
    },

    _config = null,
    
    _parentElem$ = null,

    _newInstance = function (config) {
        _config = config;
        _parentElem$ = $(_config.parentElemSelector);
    },
    
    _operationRowHtml = '<div class="row-fluid operationRow titleRow"> <span class="entityTitle largeTitleFontSize">  </span>  <span class="pull-right">  <button class="operation editBtn btn btn-mini btn-info hide">Edit</button> <button class="operation addNewBtn btn btn-mini btn-success hide">Add New</button><button class="operation saveBtn  btn btn-mini btn-success hide">Save</button><button class="operation cancelBtn btn btn-mini btn-success hide">Cancel</button></span></div><br/>',


    _tableBodyHtml = '<div class="row-fluid tableContentRow"> <table class="table table-striped table-bordered nameValTable"> <tbody class="nameValTableBody"> </tbody> </table> </div>',


    _eachNameValRowHtml = '<tr class="eachAttrRow"> <td><strong class="attribName"></strong></td> <td class="valueViewElement"></td> <td class="valueEntryElement hide"> <div class="control-group" style="margin-bottom: 0px;"> <div class="controls"> <input type="text" class="valueEntryInputTextBox"></input> <span class="help-block hide" style="font-size:.7em;">* </span> </div> </div> </td> </tr>';


    _newInstance(config);

    var _getObjFromDom = function () {
        var data = {};
        _parentElem$.find('.eachAttrRow').each(function(index){
            var attrKey = $(this).data('attrKey'),
                attrVal = $(this).find('.valueEntryInputTextBox').val();

            data[attrKey] = attrVal;

        });

        return data;
    };
    
    var _copyValueViewToFormValues = function () {
        _parentElem$.find('.valueEntryElement').each(function(){
            var this$ = $(this), 
                val = this$.parent().find('.valueViewElement').html();
            this$.find('input').val(val); 
        });

    };

    var _cancelHandler = function () {
        _parentElem$.find('.valueEntryElement').hide();
        _parentElem$.find('.valueViewElement').show();
        _parentElem$.find('.operation').addClass('hide');
        _parentElem$.find('.editBtn').removeClass('hide');
    };

    var _editHandler =  function () {
        _parentElem$.find('.valueEntryElement').show();
        _parentElem$.find('.valueViewElement').hide();
        _parentElem$.find('.operation').addClass('hide');
        _parentElem$.find('.saveBtn').removeClass('hide');
        _parentElem$.find('.cancelBtn').removeClass('hide');
        _copyValueViewToFormValues();
        //TODO: Add validation handlers
    };

    var _showView = function () {
    };

    var _addNewHandler = function () {
        if (_parentElem$.find('.addNewBtn').hasClass('disabled')) {
            return;
        }
        var data = _getObjFromDom();
        $.post(_config.saveUrl, {entityJson: JSON.stringify(data)}, function (data) {
            _showView();
        });
    };

    var _saveHandler = function () {
        if (_parentElem$.find('.saveBtn').hasClass('disabled')) {
            return;
        }

        var data = _getObjFromDom();
        $.post(_config.saveUrl, {entityJson: JSON.stringify(data)}, function (data) {
            _showView();
        });
    };

    var _addHandlers = function () {
        _parentElem$.find('.editBtn').click(_editHandler);
        _parentElem$.find('.saveBtn').click(_saveHandler);
        _parentElem$.find('.cancelBtn').click(_cancelHandler);
        _parentElem$.find('.addNewBtn').click(_addNewHandler);
    };

    return {
        get$: _getUserInfo$,
        addHandlers: _addHandlers
    };
};

