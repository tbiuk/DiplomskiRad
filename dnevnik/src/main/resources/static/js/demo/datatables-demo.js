// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable({
	  'columnDefs': [
		    { 'orderData':[7], 'targets': [3] },
		    {
		        'targets': [7],
		        'visible': false,
		        'searchable': false
		    },
		]
	});
});
