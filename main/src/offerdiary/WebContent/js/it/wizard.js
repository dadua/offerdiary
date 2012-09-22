/**
 *
 */
var it = it || {};
it.wizard = it.wizard || {};
it.wizard.step = it.wizard.step || {};

it.wizard.step.newInstance = function() {

    //private
    var _title = 'Default Step Title';
    var _setTitle = function(title) {
	var titleType = typeof title;
	if (titleType === 'string') {
	    _title = title;
	} else {
	    throw {
		message: 'title string, is incorrectly of type: ' + titleType
	    };
	}
    };

    var _htmlTemplateSelector = null;
    var _setHtmlTemplateSelector = function (jqueryHtmlSelector) {
	var jqueryHtmlSelectorType = typeof jqueryHtmlSelector;
	if (jqueryHtmlSelectorType == 'string') {
	    _htmlTemplateSelector = jqueryHtmlSelector;
	} else {
	    throw {
		message: 'jqueryHtmlSelector is incorrectly of type: ' + jqueryHtmlSelectorType
	    };
	}
    };

    var _stepValidator = function () {
	//The default validator validates this step
	return true;
    };

    var _setStepValidator = function (validatorFunction) {
	var validatorFunctionType = typeof validatorFunction;
	if (validatorFunctionType == 'function') {
	    _stepValidator = validatorFunction;
	} else {
	    throw {
		message: 'validatorFunction is incorrectly of type: ' + validatorFunctionType
	    };
	}
    };

    var _init = function (titleOrOptionsObj, jqueryHtmlTemplateSelector, validatorFunction) {

	if (typeof titleOrOptionsObj === 'object') {
	    _setTitle(titleOrOptionsObj.title);
	    _setHtmlTemplateSelector(titleOrOptionsObj.htmlTemplateSelector);
	    _setStepValidator(titleOrOptionsObj.stepValidator);
	} else {
	    _setTitle(titleOrOptionsObj);
	    _setHtmlTemplateSelector(jqueryHtmlTemplateSelector);
	    _setStepValidator(validatorFunction);
	}
    };

    var _fetchDataFromHtmlCb = function () {
	return {};
    };


    var _setFetchDataFromHtmlCb = function (cbFunction) {
	if (typeof cbFunction !== 'function') {
	    throw {
		message: 'expected a function which returns the form data object for this step'
	    };
	}
	_fetchDataFromHtmlCb = cbFunction;
    };

    var _plotCb = function () {

    };

    var _setPlotCb = function (cbFunction) {
	if (typeof cbFunction == 'function') {
	    throw {
		message: 'expected a function which sets values into html from the form data object'
	    };
	}
	_plotCb = cbFunction;
    };

    //public methods and variables
    return {
	init: _init,
	setTitle: _setTitle,
	getTitle: function() {
	    return _title;
	},
	setHtmlTemplateSelector: _setHtmlTemplateSelector,
	getHtmlTemplateSelector: function () {
	    return _htmlTemplateSelector;
	},
	setStepValidator: _setStepValidator,
	getStepValidator: function() {
	    return _stepValidator;
	},
	validate: function () {
	    return _stepValidator();
	},
	setPlotHtmlFromDataCb: _setPlotCb,
	setFetchDataFromHtmlCb: _setFetchDataFromHtmlCb,
	getFetchDataFromHtmlCb: function () {
	    return _fetchDataFromHtmlCb;
	},
	getPlotMethod: function () {
	    return _plotCb;
	}
    };
};

it.wizard.newInstance = function () {

    var _previousNextHtmlBsTemplate = '<ul class="pager"> \
        					<li class="previous disabled"> \
                                                	<a href="#">&larr; Older</a> \
                                        	</li> \
                                        	<li class="next disabled"> \
                                                	<a href="#">Newer &rarr;</a> \
                        			</li> \
					</ul>';

    var _modalBsTemplate = '<div class="modal hide" id="wizardModal" tabindex="-1" role="dialog" aria-labelledby="wizardTitle" aria-hidden="true">\
				<div class="modal-header wizardHeader"> \
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>\
        				<h4 id="wizardTitle">Modal header</h4>\
    				</div>\
				<div class="modal-body wizardBody">\
				</div>\
        			<div class="modal-footer wizardFooter">\
					<div class="pull-right">\
            					<button class="btn finalOption" data-dismiss="modal" aria-hidden="true">Close</button>\
            					<button class="btn btn-primary finalOption">Save changes</button>\
        				</div>\
        			</div>\
			</div>';

    var _wizardSteps = [];
    var _setWizardSteps = function (steps) {
	//TODO: Validate if steps is an array or not..
	_wizardSteps = steps;
    };

    var _formData = {};

    var _setFormData = function (formData) {
	_formData = formData;
    };

    var _onWizardFinish = function (formData) {

    };

    //default value of the index of wizard step
    var _currentStepIndex = -1;

    var _validateStepWithIndex = function (index) {
	var stepBeingIterated = null;
	for (var i =0; i <= index; i++) {
	    stepBeingIterated = _getStepWithIndex(i);
	    var validates = stepBeingIterated.validates();
	    if (!validates) {
		return false;
	    }
	}
	return true;
    };

    var _validateCurrentStep = function () {
	return _validateStepWithIndex(_currentStepIndex);
    };

    var _getStepWithIndex = function (index) {
	return _wizardSteps[index];
    };

    var _getCurrentStep = function () {
	return _getStepWithIndex(_currentStepIndex);
    };

    var _getLastStep = function () {
	return _wizardSteps[_getLastStepIndex()];
    };

    var _getLastStepIndex = function () {
	return _wizardSteps.length - 1;
    };

    var _navigateToNext = function () {
	if (_validateCurrentStep()) {
	    _currentStepIndex += 1;
	}
    };

    var _navigateToPrev = function () {


	_currentStepIndex -= 1;

    };

    var _navigateToStepWith = function(index) {
	_validateStepWithIndex(index);

    };

    var _setNextButtonEnabled = function () {

    };

    var _setPrevButtonEnabled = function () {

    };


    var _setNextButtonDisabled = function () {

    };

    var _setPrevButtonDiabled = function () {

    };


    var _setOnFinish = function (onWizardFinish) {
	if (typeof onWizardFinish !== 'function') {
	    throw {
		message: 'Expected a function onWizardFinish incorrect type of onWizardFinish' + typeof onWizardFinish
	    };
	}
	_onWizardFinish = onWizardFinish;
    };

    var _wizardContainerId = null;

    var _setWizardContainerId = function(containerId){
	_wizardContainerId;
    };

    var _init = function(containerId, wizardSteps, formData) {
	_setWizardContainerId(containerId);
	try {
	    _setWizardSteps(wizardSteps);
	} catch (e) {
	    if (console && console.log) {
		console.log(e.message);
	    }
	}
	try {
	    _setFormData(formData);
	} catch (e) {
	    if (console && console.log) {
		console.log(e.message);
	    }
	}
    };

    var _show = function () {
	$('#wizardModal').modal('show');

    };

    return {
	init: _init,
	setWizardSteps: _setWizardSteps,
	setFormData: _setFormData,
	setContainerId: _setWizardContainerId,
	getFormData: function () {
	    return _formData;
	},
	getWizardSteps: function () {
	    return _wizardSteps;
	},
	navigateToNext: _navigateToNext,
	navigateToPrev: _navigateToPrev,
	setOnFinish: _setOnFinish,
	show: _show
    };
};
