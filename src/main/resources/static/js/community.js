/**
 * 提交回复
 */
function post() {
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    comment2Target(questionId, 1, content);
}

/**
 * 提交二级回复
 * @param e
 */
function comment(e) {
    let id = e.getAttribute("data-id");
    let content = $("#input-" + id).val();
    comment2Target(id, 2, content);
}

function comment2Target(targetId, type, content) {
    if (!content) {
        alert("不能为空内容~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "description": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    let isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://gitee.com/oauth/authorize?client_id=333bd3155df1290cfb826e985151c38a2f3409c174ea775cbeecc8b17e75ec4f&redirect_uri=http://localhost:8887/callback&response_type=code&scope=user_info");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

/**
 * 展开二级评论
 */
function collapseComment(e) {
    let id = e.getAttribute("data-id");
    let subCommentContainer = $("#comment-" + id);

    if (subCommentContainer.children().children().length == 1) {
        $.getJSON("/comment/" + id, function (data) {
            $.each(data.data.reverse(), function (index, comment) {
                let mediaLeftElement = $("<div/>")
                    .append($("<img/>", {
                        "class": "mr-3 img-rounded media-object",
                        "alt": "头像不见了哟",
                        "src": comment.user.avatarUrl
                    }));
                let mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<h5/>", {
                    "class": "mt-0"
                }).append($("<a/>", {
                    "href": "#",
                    "html": comment.user.name
                }))).append($("<div/>", {
                    "html": comment.description
                })).append($("<div/>", {
                    "class": "menu",
                    "style": "margin-bottom: 30px"
                }).append($("<span/>", {
                    "class": "float-right",
                    "style": "color: #999",
                    "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                }))).append($("<hr/>"));

                let mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                subCommentContainer.children().prepend(mediaElement);
            });
        });
    }
}

//展开标签
function showSelectTag() {
    $("#select-tag").show();
}
//往标签栏添加标签
function selectTag(e) {
    let value = e.getAttribute("data-tag");
    let previous = $("#tag").val();
    if(!previous.includes(value)){
        if(previous){
            $("#tag").val(previous + ',' + value);
        }else {
            $("#tag").val(value);
        }
    }else {

    }
}