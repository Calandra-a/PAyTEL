 $(document).ready(function() {
     $('#myTable').DataTable( {
         ajax: {
			 "url": "https://eoa9urrjyl.execute-api.us-east-1.amazonaws.com/Development/get-all-Transactions",
			 "dataSrc": "transactions"
		 },
         columns: [
             { "data": "amount" },
             { "data": "buyer_username" },
             { "data": "note" },
			 { "data": "seller_username" },
			 { "data": "time_created" }
         ]
     } );
 } );



