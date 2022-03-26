window.onload = highlight();

function highlight() {
    var searching = document.getElementsByClassName('box');
    var words = searching[0].getAttribute('value').trim().split(/\s/);
    
    for(var i = 0; i < words.length; i++) {
        replace('title',   words[i]);
        replace('snippet', words[i]);
    }
}

function replace(classname, word) {
    var snippet = document.getElementsByClassName(classname);
    
    for(var i = 0; i < snippet.length; i++) {
        var text = snippet[i].innerHTML;
        var reg = new RegExp(word, "g" );
        text = text.replace(reg, '<span class="highlight">'+word+'</span>');
        document.getElementsByClassName(classname)[i].innerHTML = text;
    }
    
    return;
}