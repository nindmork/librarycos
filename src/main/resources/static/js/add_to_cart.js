$(document).ready(function() {
	
	$(".buttonAdd2Cart").on("click", function(evt){
		evt.preventDefault();
		bookId = $(this).attr("bookid");
		quantity = $("#quantity" + bookId).val();
		url = contextPath+ "cart/add/" + bookId +"/"+ quantity  +"/"+ customerId;
		$.ajax({
			type: "POST", 
			url: url,
			beforeSend: function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfValue);
				//alert(csrfHeaderName);
				//alert(csrfValue);
			}
				
		}).done(function(response){
			alert(response);
		}).fail(function(){
			alert("Error while add to cart.")
		})
	});
	
});