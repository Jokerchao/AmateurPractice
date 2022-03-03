var WE = {};

WE.currentSelection = {
    "startContainer": 0,
    "startOffset": 0,
    "endContainer": 0,
    "endOffset": 0};

WE.editor = document.getElementById('editor');

document.addEventListener("selectionchange", function() {
    WE.backuprange();
});

// Initializations
WE.callback = function() {
    window.location.href = "we-callback://" + encodeURI(WE.getHtml());
}

WE.setHtml = function(contents) {
    WE.editor.innerHTML = decodeURIComponent(contents.replace(/\+/g, '%20'));
}

WE.getHtml = function() {
    return WE.editor.innerHTML;
}

WE.getText = function() {
    return WE.editor.innerText;
}

WE.setBaseTextColor = function(color) {
    WE.editor.style.color  = color;
}

WE.setBaseFontSize = function(size) {
    WE.editor.style.fontSize = size;
}

WE.setPadding = function(left, top, right, bottom) {
  WE.editor.style.paddingLeft = left;
  WE.editor.style.paddingTop = top;
  WE.editor.style.paddingRight = right;
  WE.editor.style.paddingBottom = bottom;
}

WE.setBackgroundColor = function(color) {
    document.body.style.backgroundColor = color;
}

WE.setBackgroundImage = function(image) {
    WE.editor.style.backgroundImage = image;
}

WE.setWidth = function(size) {
    WE.editor.style.minWidth = size;
}

WE.setHeight = function(size) {
    WE.editor.style.height = size;
}

WE.setTextAlign = function(align) {
    WE.editor.style.textAlign = align;
}

WE.setVerticalAlign = function(align) {
    WE.editor.style.verticalAlign = align;
}

WE.setPlaceholder = function(placeholder) {
    WE.editor.setAttribute("placeholder", placeholder);
}

WE.setInputEnabled = function(inputEnabled) {
    WE.editor.contentEditable = String(inputEnabled);
}

WE.undo = function() {
    document.execCommand('undo', false, null);
}

WE.redo = function() {
    document.execCommand('redo', false, null);
}

WE.setBold = function() {
    document.execCommand('bold', true, null);
}

WE.setItalic = function() {
    document.execCommand('italic', false, null);
}

WE.setSubscript = function() {
    document.execCommand('subscript', false, null);
}

WE.setSuperscript = function() {
    document.execCommand('superscript', false, null);
}

WE.setStrikeThrough = function() {
    document.execCommand('strikeThrough', false, null);
}

WE.setUnderline = function() {
    document.execCommand('underline', false, null);
}

WE.setBullets = function() {
    document.execCommand('insertUnorderedList', false, null);
}

WE.setNumbers = function() {
    document.execCommand('insertOrderedList', false, null);
}

WE.setTextColor = function(color) {
    WE.restorerange();
    document.execCommand("styleWithCSS", null, true);
    document.execCommand('foreColor', false, color);
    document.execCommand("styleWithCSS", null, false);
}

WE.setTextBackgroundColor = function(color) {
    WE.restorerange();
    document.execCommand("styleWithCSS", null, true);
    document.execCommand('hiliteColor', false, color);
    document.execCommand("styleWithCSS", null, false);
}

WE.setTextBackColor = function(color) {
    WE.restorerange();
    document.execCommand("styleWithCSS", null, true);
    document.execCommand('backColor', false, color);
    document.execCommand("styleWithCSS", null, false);
}

WE.setFontSize = function(fontSize){
    document.execCommand("fontSize", false, fontSize);
}

WE.setHeading = function(heading) {
    document.execCommand('formatBlock', false, '<h'+heading+'>');
}

WE.setUnorderedList = function() {
    document.execCommand('InsertUnorderedList',false, null)
}

WE.setIndent = function() {
    document.execCommand('indent', false, null);
}

WE.setOutdent = function() {
    document.execCommand('outdent', false, null);
}

WE.setJustifyLeft = function() {
    document.execCommand('justifyLeft', false, null);
}

WE.setJustifyCenter = function() {
    document.execCommand('justifyCenter', false, null);
}

WE.setJustifyRight = function() {
    document.execCommand('justifyRight', false, null);
}

WE.setBlockquote = function() {
    document.execCommand('formatBlock', false, '<blockquote>');
}

WE.setEmphasis = function() {
    WE.setTextBackgroundColor('rgb(245, 246, 250)');
}

WE.insertImage = function(url, alt) {
    var html = '<img src="' + url + '" alt="' + alt + '" />';
    WE.insertHTML(html);
}

WE.insertHTML = function(html) {
    WE.restorerange();
    document.execCommand('insertHTML', false, html);
}

WE.insertText = function(text) {
    WE.restorerange();
    document.execCommand('insertText', false, text);
}

WE.insertLink = function(url, title) {
    WE.restorerange();
    var sel = document.getSelection();
    if (sel.toString().length == 0) {
        document.execCommand("insertHTML",false,"<a href='"+url+"'>"+title+"</a>");
    } else if (sel.rangeCount) {
       var el = document.createElement("a");
       el.setAttribute("href", url);
       el.setAttribute("title", title);

       var range = sel.getRangeAt(0).cloneRange();
       range.surroundContents(el);
       sel.removeAllRanges();
       sel.addRange(range);
   }
    WE.callback();
}

WE.setTodo = function(text) {
    var html = '<input type="checkbox" name="'+ text +'" value="'+ text +'"/> &nbsp;';
    document.execCommand('insertHTML', false, html);
}

WE.prepareInsert = function() {
    WE.backuprange();
}

WE.backuprange = function(){
    var selection = window.getSelection();
    if (selection.rangeCount > 0) {
      var range = selection.getRangeAt(0);
      WE.currentSelection = {
          "startContainer": range.startContainer,
          "startOffset": range.startOffset,
          "endContainer": range.endContainer,
          "endOffset": range.endOffset};
    }
}

WE.restorerange = function(){
    var selection = window.getSelection();
    selection.removeAllRanges();
    var range = document.createRange();
    range.setStart(WE.currentSelection.startContainer, WE.currentSelection.startOffset);
    range.setEnd(WE.currentSelection.endContainer, WE.currentSelection.endOffset);
    selection.addRange(range);
}

WE.enabledEditingItems = function(e) {
    var items = [];
    if (document.queryCommandState('bold')) {
        items.push('bold');
    }
    if (document.queryCommandState('italic')) {
        items.push('italic');
    }
    if (document.queryCommandState('subscript')) {
        items.push('subscript');
    }
    if (document.queryCommandState('superscript')) {
        items.push('superscript');
    }
    if (document.queryCommandState('strikeThrough')) {
        items.push('strikeThrough');
    }
    if (document.queryCommandState('underline')) {
        items.push('underline');
    }
    if (document.queryCommandState('insertOrderedList')) {
        items.push('orderedList');
    }
    if (document.queryCommandState('insertUnorderedList')) {
        items.push('unorderedList');
    }
    if (document.queryCommandState('justifyCenter')) {
        items.push('justifyCenter');
    }
    if (document.queryCommandState('justifyFull')) {
        items.push('justifyFull');
    }
    if (document.queryCommandState('justifyLeft')) {
        items.push('justifyLeft');
    }
    if (document.queryCommandState('justifyRight')) {
        items.push('justifyRight');
    }
    if (document.queryCommandState('insertHorizontalRule')) {
        items.push('horizontalRule');
    }
    var formatBlock = document.queryCommandValue('formatBlock');
    if (formatBlock.length > 0) {
        items.push(formatBlock);
    }

    window.location.href = "we-state://" + encodeURI(items.join(','));
}

WE.queryValue = function(e) {
    var items = [];
    var fontSize = document.queryCommandValue('fontSize');
    items.push(fontSize);
    var fontColor = document.queryCommandValue('foreColor');
    items.push(fontColor);
    var bold = document.queryCommandValue('bold');
    items.push(bold);
    var justifyCenter = document.queryCommandValue('justifyCenter');
    items.push(justifyCenter);
    var insertUnorderedList = document.queryCommandValue('insertUnorderedList');
    items.push(insertUnorderedList);
    var backColor = document.queryCommandValue('backColor');
    items.push(backColor);

    window.location.href = "we-update://" + encodeURI(items.join('`'));
}

WE.focus = function() {
    var range = document.createRange();
    range.selectNodeContents(WE.editor);
    range.collapse(false);
    var selection = window.getSelection();
    selection.removeAllRanges();
    selection.addRange(range);
    WE.editor.focus();
}}

WE.blurFocus = function() {
    WE.editor.+();
}

WE.timeFocus = $timeout(function() {
    WE.editor.focus();
},2000)

WE.removeFormat = function() {
    document.execCommand('removeFormat', false, null);
}

// Event Listeners
WE.editor.addEventListener("input", WE.callback);

WE.editor.addEventListener("keyup", function(e) {
    var KEY_LEFT = 37, KEY_RIGHT = 39;
    if (e.which == KEY_LEFT || e.which == KEY_RIGHT) {
        WE.enabledEditingItems(e);
        We.queryValue(e)
    }
});


WE.editor.addEventListener("click", WE.enabledEditingItems);
WE.editor.addEventListener("click", WE.queryValue);






