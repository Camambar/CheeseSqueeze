<!-- Hide from older browsers

function getVersion() {
	var xmlHttp = null;

	xmlHttp = new XMLHttpRequest();
	xmlHttp.open( "GET", "http://camambar.github.io/CheeseSqueeze/version?hash=" + Math.random(), false );
	xmlHttp.send( null );
	vString = xmlHttp.responseText.split("\n")[1];
	vString = vString.split(".").join("_");
	return vString;
}

function getAndroidAPK() {
	return "https://play.google.com/store/apps/details?id=cheese.squeeze.game.android";
}

function getWeb() {
	return "http://camambar.github.io/CheeseSqueeze/game/web";
}

function getDesktopJAR() {
	return "http://camambar.github.io/CheeseSqueeze/releases/CheeseSqueeze-v" + getVersion() + ".jar";
}

function getIOS() {
	return "";
}
//stop hiding-->
