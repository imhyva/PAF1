$(document).ready(function () {
  if ($('#alertSuccess').text().trim() == '') {
    $('#alertSuccess').hide();
  }

  $('#alertError').hide();
});

// SAVE
$(document).on('click', '#btnSave', function (event) {
  // Clear alerts
  $('#alertSuccess').text('');
  $('#alertSuccess').hide();
  $('#alertError').text('');
  $('#alertError').hide();

  // Form validation
  var status = validateEbillForm();
  if (status != true) {
    $('#alertError').text(status);
    $('#alertError').show();
    return;
  }

  // if hidEbillIDSave is null set as POST else set as PUT
  var type = $('#hidEbillIDSave').val() == '' ? 'POST' : 'PUT';

  // ajax communication
  $.ajax({
    url: 'EbillsAPI',
    type: type,
    data: $('#formEbill').serialize(),
    dataType: 'text',
    complete: function (response, status) {
      onItemSaveComplete(response.responseText, status);
    },
  });
});

//after completing save request
function onEbillSaveComplete(response, status) {
  if (status == 'success') {
    //if the response status is success
    var resultSet = JSON.parse(response);

    if (resultSet.status.trim() === 'success') {
      //if the json status is success
      //display success alert
      $('#alertSuccess').text('Successfully saved');
      $('#alertSuccess').show();

      //load data in json to html
      $('#divEbillsGrid').html(resultSet.data);
    } else if (resultSet.status.trim() === 'error') {
      //if the json status is error
      //display error
      $('#alertError').text(resultSet.data);
      $('#alertError').show();
    }
  } else if (status == 'error') {
    //if the response status is error
    $('#alertError').text('Error while saving');
    $('#alertError').show();
  } else {
    //if an unknown error occurred
    $('#alertError').text(' error occurred');
    $('#alertError').show();
  }

  //resetting the form
  $('#hidEbillIDSave').val('');
  $('#formEbill')[0].reset();
}

//UPDATE
//identify the update button used a class
$(document).on('click', '.btnUpdate', function (event) {
  //get Ebill id from the data-ebillid attribute in update button
  $('#hidEbillIDSave').val($(this).data('ebillid'));
  //get data from <td> element
  $('#id').val($(this).closest('tr').find('td:eq(0)').text());
  $('#name').val($(this).closest('tr').find('td:eq(1)').text());
  $('#amount').val($(this).closest('tr').find('td:eq(2)').text());
  $('#Units').val($(this).closest('tr').find('td:eq(3)').text());
  $('#meterreading').val($(this).closest('tr').find('td:eq(4)').text());
  $('#accno').val($(this).closest('tr').find('td:eq(5)').text());
});

//DELETE
$(document).on('click', '.btnRemove', function (s) {
  // ajax communication
  $.ajax({
    url: 'EbillsAPI',
    type: 'DELETE',
    data: 'ebillid=' + $(this).data('ebillid'),
    dataType: 'text',
    complete: function (response, status) {
      onEbillDeleteComplete(response.responseText, status);
    },
  });
});

//after completing delete request
function onEbillDeleteComplete(response, status) {
  if (status == 'success') {
    //if the response status is success
    var resultSet = JSON.parse(response);

    if (resultSet.status.trim() === 'success') {
      //if the json status is success display success alert
      $('#alertSuccess').text('Successfully deleted');
      $('#alertSuccess').show();

      //load data in json to html
      $('#divItemsGrid').html(resultSet.data);
    } else if (resultSet.status.trim() === 'error') {
      //if the json status is error
      //display error alert
      $('#alertError').text(resultSet.data);
      $('#alertError').show();
    }
  } else if (status == 'error') {
    //if the response status is error
    $('#alertError').text('Error while deleting');
    $('#alertError').show();
  } else {
    //if an unknown error occurred
    $('#alertError').text('Unknown error occurred while deleting');
    $('#alertError').show();
  }
}

//VALIDATION
function validateEbillForm() {
  // refno
  if ($('#accno').val().trim() == '') {
    return 'Insert Ebill accno.';
  }
}
return true;
