var tab = '#approve-review';
$( document ).ready(function() {
    if ($('#tab').val() === '2') {
    	tab = '#approve-seller';
    	$('#tab2').click();
    } else {
        $('#approve-review').load($('.nav-link.active').attr("data-url"), function (result) {
        });
    }
});

$('#adminTabs a').click(function (e) {
    e.preventDefault();

    var url = $(this).attr("data-url");
    var href = this.hash;
    var pane = $(this);

    // ajax load from data-url
    $(href).load(url, function (result) {
        pane.tab('show');
    });
});
