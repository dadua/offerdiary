/**
 * options.items$ = array, iterable set of item$, jquery objects summation of
 * all these jquery items$ could exceeds 12 spans.., each of the item should
 * contain a span class.. else it would added as a separate row..
 *  default assumption is that these are li's each having the class thumbnail
 *
 * options.itemRowContainerTemplate$ = each of the items that sum up to a max
 * of 12 spans would be contained in the the clone of
 *  itemRowContainerTemplate$, by default a:
 *      <div class="row-fluid"> </ul>
 *  would be used as the itemRowContainerTemplate$,
 *
 * options.directionToAdd = 'append' | 'prepend', default append
 *
 * The rowFluidAdder method maintains the max of 12 spans in a
 * row-fluid  contract, this avoids layout issues when the sum of the spans in
 * the row-fluid exceeds 12..
 *
 * Usage:
 * var item1$ = $('<li class="thumbnail span4">Item1</li>'),
 *     item2$ = $('<li class="thumbnail span4">Item2</li>'),
 *     item3$ =  $('<li class="thumbnail span5">Item3</li>'),
 *     items$ = [item1$, item2$, item3$];
 *
 * $('#rowFluidContainer').rowFluidAdder({
 *                                          'items$': items$,
 *                                          'itemRowContainerTemplate$': '<ul class="thumbnails row-fluid"> </ul>',
 *                                          });
 *
 *  The above case would add the following to the dom:
 *
 *  <div id="rowFluidContainer">
 *      <ul class="thumbnails row-fluid">
 *          <li class="thumbnail span4">Item1</li>
 *          <li class="thumbnail span4">Item2</li>
 *      </ul>
 *      <ul class="thumbnails row-fluid">
 *          <li class="thumbnail span5">Item3</li>
 *      </ul>
 *  </div>
 *
 *  TODO: handle two exception cases:
 *      - spanNoNotFound
 *          o By Default if there isn't a span class found then that item goes into 1 row-fluid
 *      - an item's spanNo incorrectly set to be greater than 12
 *
 *
 */
(function ($) {

    var _getSpansOccupied = function (item$) {
        var itemClasses = $(item$).attr('class'),
            spanRegExec = /.*span(\d+).*/.exec(itemClasses),
            offsetRegExec = /.*offset(\d+).*/.exec(itemClasses),
            spanNo = -1,
            offsetsNo= 0;
            
        if (spanRegExec && spanRegExec[1]) {
            spanNo = parseInt(spanRegExec[1], 10);
        }
        if (offsetRegExec && offsetRegExec[1]) {
            offsetsNo = parseInt(offsetRegExec[1], 10);
        }
        return spanNo + offsetsNo;
    };

    $.fn.rowFluidAdder = function (options) {
        var settings = $.extend( {
            'items$': [],
            'itemRowContainerTemplate$':
                '<div class="row-fluid"> </div>',
            'directionToAdd': 'append'
        }, options);

        var itemRows$ = [],
            currentSpanSum = 0,
            itemRowContainerTemplate$ = $(settings.itemRowContainerTemplate$),
            currentItemRow$ = itemRowContainerTemplate$.clone(),
            rootContainer$ = $(this),
            items$ = $(settings.items$),
            itemsLen = items$.size();


        if (items$.length === 0 ) {
            return this;
        }

        for (var i=0; i< itemsLen; i++) {
            var item$ = items$[i],
            currentSpanNo = _getSpansOccupied(item$), 
            spanNoNotFound = false;

            if (currentSpanNo === -1) {
                spanNoNotFound = true;
            }

            currentSpanSum += currentSpanNo;

            if (currentSpanSum === 12) {

                currentItemRow$.append(item$);
                itemRows$.push(currentItemRow$);

                currentItemRow$ = itemRowContainerTemplate$.clone();
                currentSpanSum = 0;

            } else if (currentSpanSum > 12) {

                itemRows$.push(currentItemRow$);
                currentItemRow$ = itemRowContainerTemplate$.clone();
                currentItemRow$.append(item$);
                currentSpanSum = currentSpanNo;

            } else {

                currentItemRow$.append(item$);

            }

            if (i === itemsLen -1 ) {
                itemRows$.push(currentItemRow$);
            }
        }

        //This step reduces the no. of browser repaints
        var appendedRowsTmp$ = $('<div class="_tmp_appended_rows"></div>');
        for (var j=0; j< itemRows$.length; j++) {
            appendedRowsTmp$.append(itemRows$[j]);
        }
        rootContainer$.html(appendedRowsTmp$.html());
        return this;
    };
})(jQuery);
