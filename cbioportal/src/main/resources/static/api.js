
function helloworld() {
	var helloworld = document.getElementById("helloworld");

	fetch('http://localhost:8080/api/helloworld')
	  .then(function(response) {
	  	console.log(response);
	    return response.json();
	  })
	  .then(function(res) {
	    helloworld.innerHTML = res;
	});
}