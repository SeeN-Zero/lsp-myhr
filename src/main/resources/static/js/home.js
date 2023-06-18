window.onload = function () {
    'use strict' // Start of use strict
    $(document).ready(function () {
        $('#data-table').DataTable();
    });
    $('#modalDelete').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var objectName = button.data('object-name') // Extract info from data-object-name attribute

        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-body p').text('Do you want to delete ' + objectName + '?')
    })
}

function formatCurrency(value) {
    return value.toLocaleString('id-ID', { style: 'currency', currency: 'IDR' });
}

function displayFormattedCurrency() {
    var currencyCells  = document.getElementsByClassName('curr');
    for (var i = 0; i < currencyCells.length; i++) {
        var value = parseFloat(currencyCells[i].textContent);
        currencyCells[i].textContent = formatCurrency(value);
    }
}
