
let fileInput = document.getElementById('inputGroupFile02');
let errorField = document.getElementById('sizeError');
let button = document.getElementById('saveBookButton');

function Filevalidation() {
	// Check if any file is selected.
	if (fileInput.files.length > 0) {
		for (const i = 0; i <= fileInput.files.length - 1; i++) {
			
			const fileItem = fileInput.files.item(i);
			const fsize = fileItem.size;
			const file = Math.round((fsize / 1024 / 1024));

			if(fileItem.type!=='text/plain'){
				errorField.innerHTML = '<p class="alert alert-danger" role="alert">Wrong extension. You can upload files with .txt suffix, and size less then 10 mb</p>';
			}else	
			// The size of the file.
			if (file >= 10) {
				errorField.innerHTML = '<p class="alert alert-danger" role="alert">File too big. You can upload files with .txt suffix, an size less then 10 mb</p>';
			} else {
				errorField.innerHTML = '';
				button.disabled = false;
			}
			
		}
	}
}

