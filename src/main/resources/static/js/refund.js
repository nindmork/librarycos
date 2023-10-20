$(document).ready(function() {	
	$(".linkrefund").on("click", function(evt){
		rowId = $(this).attr("ridd");	
		dele = $(this).attr("delete");
		url = contextPath+ dele;
		//alert(url);
		$.ajax({
			type: "DELETE", 
			url: url,
			beforeSend: function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfValue);
				//alert(csrfHeaderName);
				//alert(csrfValue);
			}				
		}).done(function(response){			
			//alert(response);		
			removeCartitemHTML(rowId);
		}).fail(function(){
			alert("เกิดข้อผิดพลาด response failed")
		})
	});
	
	function removeCartitemHTML(rownumber){
		$("#row" + rownumber).remove();
	}
	
});