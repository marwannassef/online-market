$(document).ready(function() {
    $("#country").change(function() {
    	getStates();
    });
    $("#state").change(function() {
    	getCities();
    });
});

$(window).on('load', function() {
	getStatesFirst();
	getCitiesFirst();
});

function getStatesFirst() {
    var country = $("#country").val();
    var state = $("#stateId").val();
    $.get( "/states?id=" + country, function( data ) {
        $("#state").empty();
        var option = "<option value = " + 0 + "> Select State </option>";
        $("#state").append(option);
        data.forEach(function(item, i) {
            var option = "<option value = " + item.id + ">" + item.name +  "</option>";
            $("#state").append(option);
            $("#state").val(state);
        });
    });
};

function getCitiesFirst() {
    var state = $("#stateId").val();
    var city = $("#cityId").val();
    $.get( "/cities?id=" + state, function( data ) {
        $("#city").empty();
        var option = "<option value = " + 0 + "> Select City </option>";
        $("#city").append(option);
        data.forEach(function(item, i) {
            var option = "<option value = " + item.id + ">" + item.name +  "</option>";
            $("#city").append(option);
            $("#city").val(city);
        });
    });
};

function getStates() {
    var country = $("#country").val();
    $.get( "/states?id=" + country, function( data ) {
        $("#state").empty();
        var option = "<option value = " + 0 + "> Select State </option>";
        $("#state").append(option);
        data.forEach(function(item, i) {
            var option = "<option value = " + item.id + ">" + item.name +  "</option>";
            $("#state").append(option);
        });
    });
};

function getCities() {
    var state = $("#state").val();
    $.get( "/cities?id=" + state, function( data ) {
        $("#city").empty();
        var option = "<option value = " + 0 + "> Select City </option>";
        $("#city").append(option);
        data.forEach(function(item, i) {
            var option = "<option value = " + item.id + ">" + item.name +  "</option>";
            $("#city").append(option);
        });
    });
};