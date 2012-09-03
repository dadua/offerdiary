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
	    var stepValidator = stepBeingIterated.getStepValidator();
	    var validates = stepValidator();
	    if (!validates) {
		return false;
	    }
	}
	return true;
    };

    var _validateCurrentStep = function () {
	_validateStepWithIndex(_currentStepIndex);
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
	_validateCurrentStep();
	_currentStepIndex += 1;
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

    var _init = function(wizardSteps, formData) {
	_setWizardSteps(wizardSteps);
	_setFormData(formData);

    };

    return {
	init: _init,
	setWizardSteps: _setWizardSteps,
	setFormData: _setFormData,
	getFormData: function () {
	    return _formData;
	},
	getWizardSteps: function () {
	    return _wizardSteps;
	},
	navigateToNext: _navigateToNext,
	navigateToPrev: _navigateToPrev,
	setOnFinish: _setOnFinish
    };
};
