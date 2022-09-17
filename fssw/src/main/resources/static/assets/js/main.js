$(document).ready(function () {

    $('.table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();

        if (text === '수정') {

            $.get(href, function (comments, status) {
                $('.myForm #Commentcontent').val(comments.text);
                $('.myForm #comment-id').val(comments.id).hide();

            });
        }
        $('.myForm #exampleModal').modal();

    });

    $('.table .reBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();

        if (text === '대댓글 입력') {

            $.get(href, function (comments, status) {
                $('.myFormSmallComment #ano-reComment-group').val(comments.group).hide();
            });
            $('.myFormSmallComment #smallCommentModal').modal();

        }

    });
    


    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });


});