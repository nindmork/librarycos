$(document).ready(function() {
	$(".linkminus").on("click", function(evt){
		evt.preventDefault();
		bookId = $(this).attr("pid");
		quantityInput = $("#quantity" + bookId);
		newQuantity = parseInt(quantityInput.val()) - 1;
		if (newQuantity > 0){
			quantityInput.val(newQuantity);
		}else{
			alert('Minimun quantity is 1');
		}
	});
	
	$(".linkplus").on("click",function(evt){
		evt.preventDefault();
		bookId = $(this).attr("pid");
		quantityInput = $("#quantity" + bookId);
		newQuantity = parseInt(quantityInput.val()) + 1;
		if (newQuantity <= 5){
			quantityInput.val(newQuantity);
			
		}else{
			alert('Maximun quantity is 5');
		}
		
	
	});
});