$(document).ready(function() {
	var options = {
		autoClose: true,
		progressBar: true,
		enableSounds: true,
		sounds: {
			info: "https://res.cloudinary.com/dxfq3iotg/video/upload/v1557233294/info.mp3",
			success: "https://res.cloudinary.com/dxfq3iotg/video/upload/v1557233524/success.mp3",
			warning: "https://res.cloudinary.com/dxfq3iotg/video/upload/v1557233563/warning.mp3",
			error: "https://res.cloudinary.com/dxfq3iotg/video/upload/v1557233574/error.mp3",
		},
	};
	var toast = new Toasty(options);
	toast.configure(options);
	var status = $('#status').val();
	if (status == 'success') {
		toast.success("Operation succeeded...");
	}
	$('#addToCart').click(function() {
		toast.success("This toast notification with sound");
	});
	$('#infotoast').click(function() {
		toast.info("This toast notification with sound");
	});
	$('#warningtoast').click(function() {
		toast.warning("This toast notification with sound");
	});
	$('#errortoast').click(function() {
		toast.error("This toast notification with sound");
	});
});