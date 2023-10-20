$(document).ready(function() {	
	$(".linkremove").on("click", function(evt){
		//evt.preventDefault();
		rowId = $(this).attr("ridd");
		
		url = $(this).attr("delete");
		//alert(rowId);
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
			alert("Error while delete to cart.")
		})
	});
	
	function removeCartitemHTML(rownumber){
		$("#row" + rownumber).remove();
	}
	
});