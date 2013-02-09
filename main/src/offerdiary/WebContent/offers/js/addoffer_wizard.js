/**
 *
 */

var it = it || {};
it.offer = it.offer || {};

it.offer.addwizard = function () {
    var addWizardSteps = [],
    initFormData = {},
    addWizard = null,
    vendorStep = it.wizard.step.newInstance(),
    offerDetailsStep = it.wizard.step.newInstance(),
    remindMeStep = it.wizard.step.newInstance();

    var _initVendorStep = function () {

        //TODO: This could be chained as well..
        vendorStep.setTitle('Vendor');
        vendorStep.setHtmlTemplateSelector('#vendorSelectionTemplate');
        vendorStep.setPlotHtmlFromDataCb(function(data){
            //TODO: verify if this vendorVO
            if (data && data.vendor) {
                it.vendor.selectVendor($('#vendorSearch_' + data.vendor.id));
            } else {
                it.vendor.unSelectAllVendor();
            }
        });
        var vendorStepValidator = function() {
            var isValidated = false;
            if (vendorStep.$('li.selected').length == 1) {
                isValidated = true;
            }
            vendorStep.publishOnValidationChangeCb(isValidated);
            return isValidated;
        };
        vendorStep.setStepValidator(vendorStepValidator);
        vendorStep.setFetchDataFromHtmlCb(function(){
            var selectedVendorLiDomId = vendorStep.$('li.selected').attr('id');
            var selectedVendorId = /vendorSearch_(\d+)/.exec(selectedVendorLiDomId)[1];
            return {
                vendor: {
                    id: selectedVendorId
                }
            };
        });
    };

    var _initWizard = function () {
        addWizardSteps.push(vendorStep);
        addWizardSteps.push(offerDetailsStep);
        addWizardSteps.push(remindMeStep);
        addWizard = it.wizard.newInstance('addOfferWizardRoot', addWizardSteps, initFormData);
        addWizard.setTitle('Add offer to wallet');

        addWizard.setOnFinish(function (offerData) {
            it.offer.saveOfferFromWizard(offerData);
        });
    };

    var _initOfferDetailsStep = function () {

        offerDetailsStep.setTitle('Benefits');
        offerDetailsStep.setHtmlTemplateSelector('#benefitDetailsTemplate');
        offerDetailsStep.setPlotHtmlFromDataCb(function(data) {
            //TODO: This check might break edit case when data.code is undefined
            // but data.description is available..
            // Then the description value would be set to ''
            if (data && data.code && data.description) {
                //TODO: fill description etc..
                offerDetailsStep.$('#offerDescription').val(data.description);
                offerDetailsStep.$('#offerCode').val(data.code);
            } else {
                offerDetailsStep.$('#offerDescription').val('');
                offerDetailsStep.$('#offerCode').val('');
            }
        });
        offerDetailsStep.setStepValidator(function() {
            var isValidated = true;
            //TODO: Do some validation using the below jQuery object
            //offerDetailsStep.$('some');
            offerDetailsStep.publishOnValidationChangeCb(isValidated);
            return isValidated;
        });
        offerDetailsStep.setFetchDataFromHtmlCb(function() {
            var code = offerDetailsStep.$('#offerCode').val(),
                description = offerDetailsStep.$('#offerDescription').val();

            return {
                code: code,
                description: description
            };

        });
    };

    var _initRemindMeStep = function () {

        remindMeStep.setTitle('Reminders');
        remindMeStep.setHtmlTemplateSelector('#reminderDetailsTemplate');
        remindMeStep.setPlotHtmlFromDataCb(function(data) {
            var isValidated = true;
            if (data && data.expiryDateInMillis) {
                //TODO: fill expiry date etc to form elements in DOM..
                remindMeStep.$('#expiryDatePicker').datepicker({
                    inline: true,
                    altField: '#expiryDateInput',
                    altFormat: "d M, yy"
                });
            }
            remindMeStep.publishOnValidationChangeCb(isValidated);
        });
        var remindMeStepValidator = function(){
            var isValidated = false;
            if (remindMeStep.$('#change').val() === '') {
                isValidated = false;
            } else {
                isValidated = true;
            }
            remindMeStep.publishOnValidationChangeCb(isValidated);
            return isValidated;
        };
        remindMeStep.setStepValidator(remindMeStepValidator);
        remindMeStep.setFetchDataFromHtmlCb(function () {
            return {
                expiryDateInMillis : $('#expiryDatePicker').datepicker('getDate').getTime()
            };
        });
    };

    var _initVendorDomHandlers = function () {
        it.vendor.init();
        vendorStep.$('#vendorQuery').keyup(function(){
            var query = $(this).val();
            it.vendor.plotAllAddable(query);
            vendorStep.publishOnValidationChangeCb(false);
        });
    };

    var _initRemindMeDomHandlers = function () {
        remindMeStep.$('#expiryDatePicker').datepicker({
            inline: true,
            altField: '#expiryDateInput',
            altFormat: "d M, yy"
        });
    };

    var _initDomHandlers = function () {
        _initVendorDomHandlers();
        _initRemindMeDomHandlers();
    };

    var _initAll = function () {
        _initWizard();
        _initVendorStep();
        _initRemindMeStep();
        _initOfferDetailsStep();
        addWizard.reInitDom();
        _initDomHandlers();
    };


    return {
        init: _initAll,
        getWizard: function () {
            return addWizard;
        },
        getVendorStep: function () {
            return vendorStep;
        },
        getRemindMeStep: function () {
            return remindMeStep;
        },
        getOfferDetailsStep: function () {
            return offerDetailsStep;
        }
    };
}();
