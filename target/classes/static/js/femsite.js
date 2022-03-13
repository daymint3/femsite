// Googleでの検索に必要な情報を追加する
function set_hidden() {
    const hl = document.getElementsByName('hl');
    if(hl.length == 0) {
        add_hidden('ie', 'utf-8');
        add_hidden('oe', 'utf-8');
        add_hidden('hl', 'ja');
    }
}

// formに<input type="hidden name="name" value="value">を追加
function add_hidden(name, value) {
    var input = document.createElement('input');
   
    input.type  = 'hidden';
    input.name  = name;
    input.value = value;

    document.forms[0].appendChild(input);
}