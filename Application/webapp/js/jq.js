
function makeTable(container, data) {
    var table = $("<table/>").addClass('CSSTableGenerator');
    $.each(data, function(rowIndex, r) {
        var row = $("<tr/>");
        $.each(r, function(colIndex, c) { 
            row.append($("<t"+(rowIndex == 0 ?  "h" : "d")+"/>").text(c));
        });
        table.append(row);
    });
    return container.append(table);
}

/* var username = "axel";
        
    $.get("https://2lzayywjtk.execute-api.us-east-1.amazonaws.com/Development/user-info?username="+username, function(data, status){
    
   });
   

  
var transid = "ca30da40-d5bb-11e8-a339-f1553396d9ab";
        
    $.get("https://2lzayywjtk.execute-api.us-east-1.amazonaws.com/Development/get-transaction?transid="+transid{

}  
   });
$.get("https://2lzayywjtk.execute-api.us-east-1.amazonaws.com/Development/get-all-Transactions", function(data, status){
        
   });

var transid = "ca30da40-d5bb-11e8-a339-f1553396d9ab";

   $.get("https://2lzayywjtk.execute-api.us-east-1.amazonaws.com/Development/flag-transaction?transid="+transid, function(data, status){

  }); */
  
  
$(document).ready(function() {
    var data = $.get("https://2lzayywjtk.execute-api.us-east-1.amazonaws.com/Development/get-transaction?transid=ca30da40-d5bb-11e8-a339-f1553396d9ab";
    var cityTable = makeTable(#container, data);
});