$(document).ready(function() {	
		
	$(".checkOut").on("click", function(){
		//evt.preventDefault();
		customerid = $(this).attr("cusid");
		url = contextPath+ "place_order/" + customerid ;
		
		//alert(url);
		$.ajax({
			type: "POST", 
			url: url,
			beforeSend: function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfValue);
				//alert(csrfHeaderName);
				//alert(csrfValue);
			}
				
		}).done(function(response){			
			//alert(response);
		}).fail(function(){
			alert("Error while delete to cart.")
		})
	});
	
});